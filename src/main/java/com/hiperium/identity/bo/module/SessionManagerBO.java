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
package com.hiperium.identity.bo.module;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.vo.UserSessionVO;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Remote
public interface SessionManagerBO {

	/**
	 * 
	 * @param sessionRegister
	 */
	void addUserSessionRegister(@NotNull SessionRegister sessionRegister);

	/**
	 * 
	 * @param sessionRegister
	 */
	void addHomeSessionRegister(@NotNull SessionRegister sessionRegister);

	/**
	 * 
	 * @param sessionRegister
	 */
	void updateUserSessionRegister(@NotNull SessionRegister sessionRegister);

	/**
	 * 
	 * @param tokenId
	 * @return
	 */
	SessionRegister delete(@NotNull String tokenId);

	/**
	 * 
	 * @param tokenId
	 * @return
	 */
	boolean isUserLoggedIn(@NotNull String tokenId);
	
	/**
	 * 
	 * @param tokenId
	 * @return
	 */
	boolean isHomeLoggedIn(String tokenId);

	/**
	 * 
	 * @param tokenId
	 * @return
	 */
	SessionRegister findUserSessionRegister(@NotNull String tokenId);

	/**
	 * 
	 * @param tokenId
	 * @return
	 */
	UserSessionVO findUserSessionVO(@NotNull String tokenId);

}
