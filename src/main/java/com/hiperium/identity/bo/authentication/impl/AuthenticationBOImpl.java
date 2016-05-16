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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.EnumAccessChannel;
import com.hiperium.common.services.EnumAuthenticationResult;
import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.exception.EnumInformationException;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.vo.UserSessionVO;
import com.hiperium.identity.audit.bo.AuditManagerBO;
import com.hiperium.identity.bo.authentication.AuthenticationBO;
import com.hiperium.identity.common.SessionManagerBean;
import com.hiperium.identity.common.dto.HomeSelectionDTO;
import com.hiperium.identity.common.utils.HashMd5;
import com.hiperium.identity.dao.factory.DataAccessFactory;
import com.hiperium.identity.dao.module.SessionRegisterDAO;
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
    protected HiperiumLogger log;
    
    /** The property daoFactory. */
    @Inject
    private DataAccessFactory daoFactory;
    
    /** The property auditManagerBO. */
	@EJB
	private AuditManagerBO auditManagerBO;
	
    /** The property sessionRegisterDAO. */
	@EJB
	private SessionRegisterDAO sessionRegisterDAO;
	
	/** The property sessionManagerBean. */
	@EJB
	private SessionManagerBean sessionManagerBean;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SessionRegister userAuthentication(@NotNull String userEmail, @NotNull String userPassword, @NotNull String userAgent, 
			@NotNull String remoteIpAddress) throws InformationException {
		this.log.debug("userAuthentication - BEGIN");
		
		User user = this.daoFactory.getUserDAO().findByEmail(userEmail);
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
		
		// Add user session register to the singleton map
		this.sessionManagerBean.addUserSessionRegister(sessionRegister);
				
		this.log.debug("userAuthentication - END");
		return sessionRegister;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SessionRegister homeAuthentication(@NotNull @Min(value = 1L) Long homeId, @NotNull String serial,
			@NotNull String userAgent, @NotNull String remoteIpAddress) throws InformationException {
		this.log.debug("homeAuthentication - BEGIN");
		
		// Find the Raspberry registered with the serial received
		Long gatewayId = this.daoFactory.getHomeGatewayDAO().findGatewayIdByHomeSerial(homeId, serial);
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
		
		// Add user session register to the singleton map
		this.sessionManagerBean.addHomeSessionRegister(sessionRegister);
				
		this.log.debug("homeAuthentication - END");
		return sessionRegister;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void homeSelection(@NotNull HomeSelectionDTO homeSelectionDTO, @NotNull String tokenId) throws InformationException {
		this.log.debug("homeSelection - BEGIN");
		
		// Sets the values to session register
		SessionRegister sessionRegister = this.sessionManagerBean.findUserSessionRegister(tokenId);
		sessionRegister.setHomeId(homeSelectionDTO.getHomeId());
		sessionRegister.setProfileId(homeSelectionDTO.getProfileId());
		
		UserHomePK userHomePK = new UserHomePK(sessionRegister.getUserId(), sessionRegister.getHomeId());
		UserHome userHome = this.daoFactory.getUserHomeDAO().findById(userHomePK, false, true);
		
		// VALIDATES USER STATE
		if (userHome == null || !userHome.getActive()) {
			throw InformationException.generate(EnumInformationException.USER_ACCOUNT_LOCKED);
		}
		
		// VALIDATES HOME STATE
		if (!userHome.getHome().getCloudEnable()) {
			throw InformationException.generate(EnumInformationException.HOME_CLOUD_NOT_ENABLED);
		}
		
		// UPDATE SESSION REGISTER
		try {
			this.auditManagerBO.updateHomeSelection(sessionRegister, sessionRegister.getTokenId());
			this.sessionManagerBean.updateUserSessionRegister(sessionRegister);
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
		return this.sessionManagerBean.findUserSessionRegister(tokenId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUserLoggedIn(@NotNull String tokenId) {
		return this.sessionManagerBean.isUserLoggedIn(tokenId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserSessionVO findUserSessionVO(@NotNull String tokenId) {
		return this.sessionManagerBean.findUserSessionVO(tokenId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endUserSession(@NotNull String tokenId) {
		SessionRegister sessionRegister = this.sessionManagerBean.delete(tokenId);
		if(sessionRegister != null) {
			try {
				this.auditManagerBO.updateLogoutDate(sessionRegister, sessionRegister.getTokenId());
			} catch (Exception e) {
				this.log.error(e.getMessage());
			}
		}
	}
}
