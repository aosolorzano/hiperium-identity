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
package com.hiperium.security.common;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.EnumAccessChannel;
import com.hiperium.common.services.EnumAuthenticationResult;
import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.exception.EnumInformationException;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.vo.SessionAuditVO;
import com.hiperium.security.audit.bo.AuditManagerBO;
import com.hiperium.security.common.vo.ProfileFunctionalityVO;

/**
 * This bean is used only for user session validation and resource access
 * between business objects and security filters in the presentation layer.
 *
 * @author Andres Solorzano
 * @version 1.0
 */
@Startup
@Singleton
@LocalBean
@Lock(LockType.READ)
@DependsOn("ConfigurationBean")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SessionManagerBean {

	/** The property log. */
	@Inject
	protected HiperiumLogger log;
	
	/** The auditManagerBO property. */
	@EJB
	private AuditManagerBO auditManagerBO;

	/** The sessionMap property. */
	private HashMap<String, SessionRegister> tokenMap;
	/** The homeSessionMap property. */
	private HashMap<Long, Set<String>> homeTokenMap;
	
	/**
	 * Class constructor.
	 */
	public SessionManagerBean() {
		this.tokenMap = new HashMap<>();
		this.homeTokenMap = new HashMap<>();
	}

	/**
	 * Component initialization.
	 */
	@PostConstruct
	public void init() {
		this.log.debug("init - BEGIN");
		SessionRegister platformRegister = new SessionRegister();
		platformRegister.setUserId(1L); // Platform user
		platformRegister.setTokenId(UUID.randomUUID().toString()); // Initial application token
		platformRegister.setAccessChannel(EnumAccessChannel.PLATFORM);
		platformRegister.setActive(true);
		platformRegister.setIpConnection("localhost");
		platformRegister.setLoginDate(new Date());
		platformRegister.setAuthenticationResult(EnumAuthenticationResult.LOGIN_SUCCESS);
		this.tokenMap.put(platformRegister.getTokenId(), platformRegister);
		this.log.debug("init - END");
	}
	
	/**
	 *
	 * @param sessionRegister
	 * @param sessionId
	 * @return
	 */
	public SessionRegister addSessionRegister(@NotNull SessionRegister sessionRegister) {
		this.log.debug("addSessionRegister - BEGIN");
		this.tokenMap.put(sessionRegister.getTokenId(), sessionRegister);
		this.log.debug("addSessionRegister - END");
		return sessionRegister;
	}

	/**
	 *
	 * @param sessionId
	 * @throws com.hiperium.commons.exception.InformationException
	 */
	public void updateSessionRegister(@NotNull SessionRegister sessionRegister) throws InformationException {
		this.log.debug("updateSessionRegister - BEGIN");
		if(this.tokenMap.containsKey(sessionRegister.getTokenId())) {
			this.tokenMap.put(sessionRegister.getTokenId(), sessionRegister);
		}
		// Add users of the same home to the session map. This is because the device 
		// platform needs to notify for an home event to connected users of the 
		// same home.
		if(this.homeTokenMap.containsKey(sessionRegister.getHomeId())) {
			this.homeTokenMap.get(sessionRegister.getHomeId()).add(sessionRegister.getTokenId());
		} else {
			Set<String> sessionIds = new HashSet<String>();
			sessionIds.add(sessionRegister.getTokenId());
			this.homeTokenMap.put(sessionRegister.getHomeId(), sessionIds);
		}
		this.log.debug("updateSessionRegister - END");
	}

	/**
	 *
	 * @param tokenId
	 */
	public void delete(@NotNull String tokenId) {
		this.log.debug("delete - BEGIN");
		SessionRegister sessionRegister = this.tokenMap.get(tokenId);
		if(sessionRegister != null) {
			try {
				this.auditManagerBO.updateLogoutDate(sessionRegister, sessionRegister.getTokenId());
			} catch (Exception e) {
				this.log.error(e.getMessage());
			}
			this.tokenMap.remove(tokenId);
			// Remove the session from home session map too.
			if(this.homeTokenMap.containsKey(sessionRegister.getHomeId())) {
				this.homeTokenMap.get(sessionRegister.getHomeId()).remove(tokenId);
				if(this.homeTokenMap.get(sessionRegister.getHomeId()).isEmpty()) {
					this.homeTokenMap.remove(sessionRegister.getHomeId());
				}
			}
		}
		this.log.debug("delete - END");
	}

	/**
	 *
	 * @param tokenId
	 * @return
	 */
	public boolean isUserLoggedIn(@NotNull String tokenId) {
		return this.tokenMap.containsKey(tokenId);
	}
	
	/**
	 *
	 * @param tokenId
	 * @return
	 * @throws com.hiperium.commons.exception.InformationException
	 */
	public SessionRegister findSessionRegister(@NotNull String tokenId) throws InformationException {
		SessionRegister register = null;
		if (this.tokenMap.containsKey(tokenId)) {
			register = this.tokenMap.get(tokenId);
		}
		return register;
	}

	/**
	 *
	 * @param tokenId
	 * @return
	 */
	public SessionAuditVO findSessionAuditVO(@NotNull String tokenId) {
		SessionRegister sessionRegister = this.tokenMap.get(tokenId);
		if(sessionRegister == null) {
			return null;
		} else {
			SessionAuditVO sessionAuditVO = new SessionAuditVO(
					sessionRegister.getUserId(),
					sessionRegister.getHomeId(),
					sessionRegister.getProfileId(),
					sessionRegister.getIpConnection(),
					sessionRegister.getAccessChannel(),
					tokenId);
			return sessionAuditVO;
		}
	}

	/**
	 *
	 * @param tokenId
	 * @return
	 */
	public Long findUserProfileId(@NotNull String tokenId) {
		return this.tokenMap.get(tokenId).getProfileId();
	}

	/**
	 *
	 * @param tokenId
	 * @return
	 */
	public Long findUserId(@NotNull String tokenId) {
		return this.tokenMap.get(tokenId).getUserId();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void validateFunctionalityAccessTime(@NotNull ProfileFunctionalityVO profileFunctionalityVO,
			@NotNull Locale locale, @NotNull String sessionId) throws InformationException {
		this.log.debug("validateFunctionalityAccessTime - BEGIN");
		Calendar actualDate = Calendar.getInstance();
		int actualHour = actualDate.get(Calendar.HOUR_OF_DAY);
		int actualMinute = actualDate.get(Calendar.MINUTE);
		if (actualHour < profileFunctionalityVO.getHourOfHourFrom() || actualHour > profileFunctionalityVO.getHourOfHourUntil()) {
			throw InformationException.generate(EnumInformationException.INVALID_FUNCTION_ACCESS_TIME);
		} else if (actualHour == profileFunctionalityVO.getHourOfHourFrom() && actualMinute < profileFunctionalityVO.getMinuteOfHourFrom()) {
			throw InformationException.generate(EnumInformationException.INVALID_FUNCTION_ACCESS_TIME);
		} else if (actualHour == profileFunctionalityVO.getHourOfHourUntil() && actualMinute > profileFunctionalityVO.getMinuteOfHourUntil()) {
			throw InformationException.generate(EnumInformationException.INVALID_FUNCTION_ACCESS_TIME);
		}
		this.log.debug("validateFunctionalityAccessTime - END");
	}
	
	/**
	 *
	 */
	@Schedule(hour = "*/3")
	private void deleteIsolatedObjects() {
		this.log.debug("removeIsolatedObjects() - START");
		System.gc();
		this.log.debug("removeIsolatedObjects() - END");
	}

	/**
	 * 
	 */
	@PreDestroy
	public void destroy() {
		this.log.debug("DESTROYING SESSION MANAGER - START");
		this.tokenMap.clear();
		this.homeTokenMap.clear();
		this.log.debug("DESTROYING SESSION MANAGER - END");
	}
}
