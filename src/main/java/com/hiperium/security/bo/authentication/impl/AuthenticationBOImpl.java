/**
 * Product  : Hiperium Project
 * Architect: Andres Solorzano.
 * Created  : 08-05-2009 - 23:30:00
 * 
 * The contents of this file are copyrighted by Andres Solorzano 
 * and it is protected by the license: "GPL V3." You can find a copy of this 
 * license at: http://www.hiperium.com/about/licence.html
 * 
 * Copyright 2014 Andres Solorzano. All rights reserved.
 * 
 */
package com.hiperium.security.bo.authentication.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.EnumAccessChannel;
import com.hiperium.common.services.EnumAuthenticationResult;
import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.audit.UserStatistic;
import com.hiperium.common.services.dto.HomeAuthResponseDTO;
import com.hiperium.common.services.exception.EnumInformationException;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.vo.SessionAuditVO;
import com.hiperium.security.audit.bo.AuditManagerBO;
import com.hiperium.security.bo.authentication.AuthenticationBO;
import com.hiperium.security.bo.generic.GenericBO;
import com.hiperium.security.common.dto.UserAuthResponseDTO;
import com.hiperium.security.common.utils.HashMd5;
import com.hiperium.security.dao.module.SessionRegisterDAO;
import com.hiperium.security.model.security.User;
import com.hiperium.security.model.security.UserHome;
import com.hiperium.security.model.security.UserHomePK;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class AuthenticationBOImpl extends GenericBO implements AuthenticationBO {

	/** The property log. */
    @Inject
    protected HiperiumLogger log;
    
    /** The property auditManagerBO. */
	@EJB
	private AuditManagerBO auditManagerBO;
	
    /** The property sessionRegisterDAO. */
	@EJB
	private SessionRegisterDAO sessionRegisterDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAuthResponseDTO userAuthentication(@NotNull String userEmail, @NotNull String userPassword, @NotNull String userAgent, 
			@NotNull String remoteIpAddress) throws InformationException {
		this.log.debug("userAuthentication - BEGIN");
		
		User user = super.getDaoFactory().getUserDAO().findByEmail(userEmail);
		if(user == null) {
			this.log.error("Error: No User found with email: " + userEmail);
			throw InformationException.generate(EnumInformationException.USER_NOT_FOUND);
		}
		
		// Validates user password
		HashMd5 md5Converter = new HashMd5();
		String md5Password = md5Converter.hash(userPassword);
		if(!user.getPassword().equals(md5Password)) {
			throw InformationException.generate(EnumInformationException.USER_NOT_FOUND);
		}
		
		// Creates the initial session register
		SessionRegister sessionRegister = new SessionRegister();
		sessionRegister.setUserId(user.getId());
		sessionRegister.setActive(true);
		sessionRegister.setIpConnection(remoteIpAddress);
		sessionRegister.setUserAgent(userAgent);
		sessionRegister.setAccessChannel(EnumAccessChannel.MOBILE);
		sessionRegister.setAuthenticationResult(EnumAuthenticationResult.LOGIN_SUCCESS);
		this.sessionRegisterDAO.create(sessionRegister);
		
		// Add session register to the session map
		super.getSessionManager().addSessionRegister(sessionRegister);
		
        // Construct the response
		UserAuthResponseDTO dto = new UserAuthResponseDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getFirstname());
		dto.setLanguage(user.getLanguageId());
		dto.setAuthorizationToken(sessionRegister.getTokenId());
		
		// Verify if user needs to change the password
		UserStatistic userStatistic;
		try {
			userStatistic = this.auditManagerBO.findUserStatisticById(sessionRegister.getUserId(), sessionRegister.getTokenId());
			Calendar lastChangedPassword = Calendar.getInstance();
			lastChangedPassword.setTime(userStatistic.getLastPasswordChange());
			// TODO: CASE BASED ON BUSINESS RULE, FOR TEST I AM USING 3 MONTHS
			lastChangedPassword.add(Calendar.MONTH, 3);
			if (lastChangedPassword.before(Calendar.getInstance())) {
	        	dto.setChangePassword(Boolean.TRUE);
	        } else {
	        	dto.setChangePassword(Boolean.FALSE);
	        }
		} catch (Exception e) {
			this.log.error(e.getMessage());
		}
				
		this.log.debug("userAuthentication - END");
		return dto;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HomeAuthResponseDTO homeAuthentication(@NotNull @Min(value = 1L) Long homeId, @NotNull String serial,
			@NotNull String userAgent, @NotNull String remoteIpAddress) throws InformationException {
		this.log.debug("homeAuthentication - BEGIN");
		
		// Find the Raspberry registered with the serial received
		Long gatewayId = super.getDaoFactory().getHomeGatewayDAO().findGatewayIdByHomeSerial(homeId, serial);
		if(gatewayId == 0L || gatewayId == null) {
			this.log.error("Error: No Home Gateway found with serial: " + serial);
			throw InformationException.generate(EnumInformationException.USER_NOT_FOUND);
		}
		
		// Creates the user session register to be persisted in the data base
		SessionRegister sessionRegister = new SessionRegister();
		sessionRegister.setHomeId(homeId);
		sessionRegister.setUserId(1L); // PLATFORM USER ID
		sessionRegister.setActive(true);
		sessionRegister.setAuthenticationResult(EnumAuthenticationResult.LOGIN_SUCCESS);
		sessionRegister.setIpConnection(remoteIpAddress);
		sessionRegister.setUserAgent(userAgent);
		sessionRegister.setAccessChannel(EnumAccessChannel.HOME);
		this.sessionRegisterDAO.create(sessionRegister);
		
		// Add session register to the session map
		super.getSessionManager().addSessionRegister(sessionRegister);
		
		// Find the JBoss Application User credentials to be send to the Raspberry for Queue connections.
		List<String> register = super.getDaoFactory().getApplicationUserDAO().findByRole("hiperium");
		HomeAuthResponseDTO dto = new HomeAuthResponseDTO();
		dto.setParam1(register.get(0));
		dto.setParam2(register.get(1));
		dto.setParam3(sessionRegister.getTokenId());
				
		this.log.debug("homeAuthentication - END");
		return dto;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void homeSelection(@NotNull @Min(value = 1L) Long homeId, @NotNull @Min(value = 1L) Long profileId,
			@NotNull String tokenId) throws InformationException {
		this.log.debug("homeSelection - BEGIN");
		SessionAuditVO sessionAuditVO = super.getSessionManager().findSessionAuditVO(tokenId);
		UserHomePK userHomePK = new UserHomePK(sessionAuditVO.getUserId(), homeId);
		UserHome userHome = super.getDaoFactory().getUserHomeDAO().findById(userHomePK, false, true);
		// VALIDATES USER STATE
		if (userHome == null || !userHome.getActive()) {
			throw InformationException.generate(EnumInformationException.USER_ACCOUNT_LOCKED);
		}
		// VALIDATES HOME STATE
		if (!userHome.getHome().getCloudEnable()) {
			throw InformationException.generate(EnumInformationException.HOME_CLOUD_NOT_ENABLED);
		}
		// UPDATE SESSION REGISTER
		SessionRegister sessionRegister = super.getSessionManager().findSessionRegister(tokenId);
		sessionRegister.setHomeId(homeId);
		sessionRegister.setProfileId(profileId);
		try {
			this.auditManagerBO.updateHomeSelection(sessionRegister, sessionRegister.getTokenId());
		} catch (Exception e) {
			throw new InformationException(e.getMessage());
		}

		// Updates session register in the session map
		super.getSessionManager().updateSessionRegister(sessionRegister);
		this.log.debug("homeSelection - END");
	}
}
