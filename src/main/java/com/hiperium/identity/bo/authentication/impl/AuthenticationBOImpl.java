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
package com.hiperium.identity.bo.authentication.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.dto.HomeResponseDTO;
import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.services.EnumAccessChannel;
import com.hiperium.commons.services.EnumAuthenticationResult;
import com.hiperium.commons.services.exception.EnumInformationException;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.model.SessionRegister;
import com.hiperium.commons.services.model.UserStatistic;
import com.hiperium.commons.services.vo.UserSessionVO;
import com.hiperium.identity.bo.authentication.AuthenticationBO;
import com.hiperium.identity.bo.module.SessionManagerBO;
import com.hiperium.identity.common.dto.HomeSelectionDTO;
import com.hiperium.identity.common.dto.UserResponseDTO;
import com.hiperium.identity.common.utils.HashMd5;
import com.hiperium.identity.dao.factory.DataAccessFactory;
import com.hiperium.identity.dao.module.ApplicationUserDAO;
import com.hiperium.identity.dao.module.SessionRegisterDAO;
import com.hiperium.identity.dao.module.UserStatisticDAO;
import com.hiperium.identity.model.security.User;
import com.hiperium.identity.model.security.UserHome;
import com.hiperium.identity.model.security.UserHomePK;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class AuthenticationBOImpl implements AuthenticationBO {

	/** The property log. */
    @Inject
    private HiperiumLogger log;
    
    /** The property daoFactory. */
    @Inject
    private DataAccessFactory daoFactory;
    
    /** The property sessionRegisterDAO. */
	@EJB
	private SessionRegisterDAO sessionRegisterDAO;
	
	/** The property userStatisticDAO. */
	@EJB
	private UserStatisticDAO userStatisticDAO;
	
	/** The applicationUserDAO property. */
	@EJB
	private ApplicationUserDAO applicationUserDAO;
	
	/** The property sessionManagerBO. */
	@EJB
	private SessionManagerBO sessionManagerBO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDTO userAuthentication(@NotNull String userEmail, @NotNull String userPassword, @NotNull String userAgent, 
			@NotNull String remoteIpAddress) throws InformationException {
		this.log.debug("userAuthentication - BEGIN");
		
		User user = this.daoFactory.getUserDAO().findByEmail(userEmail);
		if(user == null) {
			this.log.error("Error: No User found with email: " + userEmail);
			throw new InformationException(EnumInformationException.USER_NOT_FOUND.getCode());
		}
		
		// Validates user password
		HashMd5 md5Converter = new HashMd5();
		String md5Password = md5Converter.hash(userPassword);
		if(!user.getPassword().equals(md5Password)) {
			throw new InformationException(EnumInformationException.USER_NOT_FOUND.getCode());
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
		
		// Add user session register to the singleton map
		this.sessionManagerBO.addUserSessionRegister(sessionRegister);
		
		// Construct the response
     	UserResponseDTO dto = new UserResponseDTO();
     	dto.setId(user.getId());
     	dto.setUsername(user.getFirstname());
     	dto.setLanguage(user.getLanguageId());
     	dto.setAuthorizationToken(sessionRegister.getTokenId());
     	
     	// Verify if user needs to change the password
 		UserStatistic userStatistic;
 		try {
 			userStatistic = this.userStatisticDAO.findById(sessionRegister.getUserId());
 			Calendar lastChangedPassword = Calendar.getInstance();
 			lastChangedPassword.setTime(userStatistic.getLastPasswordChange());
 			// TODO: CASE BASED ON BUSINESS RULE, FOR TESTING WE ARE USING 3 MONTHS
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
	public HomeResponseDTO homeAuthentication(@NotNull @Min(value = 1L) Long homeId, @NotNull String serial,
			@NotNull String userAgent, @NotNull String remoteIpAddress) throws InformationException {
		this.log.debug("homeAuthentication - BEGIN");
		
		// Find the Raspberry registered with the serial received
		Long gatewayId = this.daoFactory.getHomeGatewayDAO().findGatewayIdByHomeSerial(homeId, serial);
		if(gatewayId == 0L || gatewayId == null) {
			this.log.error("Error: No Home Gateway found with serial: " + serial);
			throw new InformationException(EnumInformationException.USER_NOT_FOUND.getCode());
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
		
		// Add user session register to the singleton map
		this.sessionManagerBO.addHomeSessionRegister(sessionRegister);
		
		// Find the JBoss Application User credentials to be send to the Raspberry for Queue connections.
 		List<String> register = this.applicationUserDAO.findByRole("hiperium");
 		HomeResponseDTO dto = new HomeResponseDTO();
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
	public void homeSelection(@NotNull HomeSelectionDTO homeSelectionDTO, @NotNull String tokenId) throws InformationException {
		this.log.debug("homeSelection - BEGIN");
		
		// Sets the values to session register
		SessionRegister sessionRegister = this.sessionManagerBO.findUserSessionRegister(tokenId);
		sessionRegister.setHomeId(homeSelectionDTO.getHomeId());
		sessionRegister.setProfileId(homeSelectionDTO.getProfileId());
		
		UserHomePK userHomePK = new UserHomePK(sessionRegister.getUserId(), sessionRegister.getHomeId());
		UserHome userHome = this.daoFactory.getUserHomeDAO().findById(userHomePK, false, true);
		
		// VALIDATES USER STATE
		if (userHome == null || !userHome.getActive()) {
			throw new InformationException(EnumInformationException.USER_ACCOUNT_LOCKED.getCode());
		}
		
		// VALIDATES HOME STATE
		if (!userHome.getHome().getCloudEnable()) {
			throw new InformationException(EnumInformationException.HOME_CLOUD_NOT_ENABLED.getCode());
		}
		
		// UPDATE SESSION REGISTER
		try {
			this.sessionRegisterDAO.updateHomeSelection(sessionRegister);
			this.sessionManagerBO.updateUserSessionRegister(sessionRegister);
		} catch (Exception e) {
			throw new InformationException(e.getMessage());
		}
		
		this.log.debug("homeSelection - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SessionRegister findUserSessionRegister(@NotNull String tokenId) throws InformationException {
		return this.sessionManagerBO.findUserSessionRegister(tokenId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUserLoggedIn(@NotNull String tokenId) {
		return this.sessionManagerBO.isUserLoggedIn(tokenId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isHomeLoggedIn(@NotNull String tokenId) {
		return this.sessionManagerBO.isHomeLoggedIn(tokenId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserSessionVO findUserSessionVO(@NotNull String tokenId) {
		return this.sessionManagerBO.findUserSessionVO(tokenId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endUserSession(@NotNull String tokenId) {
		SessionRegister sessionRegister = this.sessionManagerBO.delete(tokenId);
		if(sessionRegister != null) {
			try {
				this.sessionRegisterDAO.updateLogoutDate(sessionRegister.getId());
			} catch (Exception e) {
				this.log.error(e.getMessage());
			}
		}
	}
}
