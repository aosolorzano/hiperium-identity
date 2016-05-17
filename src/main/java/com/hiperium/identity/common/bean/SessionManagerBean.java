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
package com.hiperium.identity.common.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.hiperium.common.services.EnumAccessChannel;
import com.hiperium.common.services.EnumAuthenticationResult;
import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.vo.UserSessionVO;

/**
 * This bean is used only for user session validation and resource access
 * between business objects and security filters in the presentation layer.
 *
 * @author Andres Solorzano
 * @version 1.0
 */
public class SessionManagerBean implements Serializable {

	/** The serialVersionUID property. */
	private static final long serialVersionUID = 6226858590509908917L;
	
	/** The userTokenMap property. */
	private HashMap<String, SessionRegister> userTokenMap;
	/** The userTokenMap property. */
	private HashMap<String, SessionRegister> homeTokenMap;
	/** The homeUsersTokenMap property. */
	private HashMap<Long, Set<String>> homeUsersTokenMap;
	
	/**
	 * Initializes the component.
	 */
	public void initialize() {
		this.userTokenMap = new HashMap<String, SessionRegister>();
		this.homeTokenMap = new HashMap<String, SessionRegister>();
		this.homeUsersTokenMap = new HashMap<Long, Set<String>>();
		// Add Platform User to session register map
		SessionRegister platformRegister = new SessionRegister();
		platformRegister.setUserId(1L); 
		platformRegister.setTokenId(UUID.randomUUID().toString()); // Initial application user token
		platformRegister.setAccessChannel(EnumAccessChannel.PLATFORM);
		platformRegister.setActive(true);
		platformRegister.setIpConnection("localhost");
		platformRegister.setLoginDate(new Date());
		platformRegister.setAuthenticationResult(EnumAuthenticationResult.LOGIN_SUCCESS);
		this.userTokenMap.put(platformRegister.getTokenId(), platformRegister);
	}
	
	/**
	 * 
	 * @param sessionRegister
	 */
	public void addUserSessionRegister(@NotNull SessionRegister sessionRegister) {
		this.userTokenMap.put(sessionRegister.getTokenId(), sessionRegister);
	}
	
	/**
	 * 
	 * @param sessionRegister
	 */
	public void addHomeSessionRegister(@NotNull SessionRegister sessionRegister) {
		this.homeTokenMap.put(sessionRegister.getTokenId(), sessionRegister);
	}
	
	/**
	 * 
	 * @param sessionRegister
	 */
	public void updateUserSessionRegister(@NotNull SessionRegister sessionRegister) {
		if(this.userTokenMap.containsKey(sessionRegister.getTokenId())) {
			this.userTokenMap.put(sessionRegister.getTokenId(), sessionRegister);
		}
		// Add users of the same home to the session map. This because the device 
		// platform needs notifies for an home event to connected users of the 
		// same home.
		if(this.homeUsersTokenMap.containsKey(sessionRegister.getHomeId())) {
			this.homeUsersTokenMap.get(sessionRegister.getHomeId()).add(sessionRegister.getTokenId());
		} else {
			Set<String> sessionIds = new HashSet<String>();
			sessionIds.add(sessionRegister.getTokenId());
			this.homeUsersTokenMap.put(sessionRegister.getHomeId(), sessionIds);
		}
	}
	
	/**
	 * 
	 * @param tokenId
	 * @return
	 */
	public SessionRegister delete(@NotNull String tokenId) {
		SessionRegister sessionRegister = this.userTokenMap.get(tokenId);
		if(sessionRegister != null) {
			this.userTokenMap.remove(tokenId);
			// Remove the session from home session map too.
			if(this.homeUsersTokenMap.containsKey(sessionRegister.getHomeId())) {
				this.homeUsersTokenMap.get(sessionRegister.getHomeId()).remove(tokenId);
				if(this.homeUsersTokenMap.get(sessionRegister.getHomeId()).isEmpty()) {
					this.homeUsersTokenMap.remove(sessionRegister.getHomeId());
				}
			}
		}
		return sessionRegister;
	}
	
	/**
	 *
	 * @param tokenId
	 * @return
	 */
	public boolean isUserLoggedIn(@NotNull String tokenId) {
		return this.userTokenMap.containsKey(tokenId);
	}
	
	/**
	 * 
	 * @param tokenId
	 * @return
	 */
	public SessionRegister findUserSessionRegister(@NotNull String tokenId) {
		SessionRegister register = null;
		if (this.userTokenMap.containsKey(tokenId)) {
			register = this.userTokenMap.get(tokenId);
		}
		return register;
	}

	/**
	 *
	 * @param tokenId
	 * @return
	 */
	public UserSessionVO findUserSessionVO(@NotNull String tokenId) {
		SessionRegister sessionRegister = this.userTokenMap.get(tokenId);
		if(sessionRegister == null) {
			return null;
		} else {
			UserSessionVO sessionAuditVO = new UserSessionVO(
					sessionRegister.getUserId(),
					sessionRegister.getHomeId(),
					sessionRegister.getProfileId(),
					sessionRegister.getIpConnection(),
					sessionRegister.getAccessChannel(),
					tokenId);
			return sessionAuditVO;
		}
	}
}
