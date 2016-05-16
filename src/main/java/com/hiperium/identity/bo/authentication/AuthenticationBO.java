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
package com.hiperium.identity.bo.authentication;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.vo.UserSessionVO;
import com.hiperium.identity.common.dto.HomeSelectionDTO;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface AuthenticationBO {

	/**
	 * 
	 * @param userEmail
	 * @param userPassword
	 * @param userAgent
	 * @param remoteIpAddress
	 * @return
	 * @throws InformationException
	 */
	SessionRegister userAuthentication(@NotNull String userEmail, @NotNull String userPassword,
			@NotNull String userAgent, @NotNull String remoteIpAddress) throws InformationException;

	/**
	 * 
	 * @param homeId
	 * @param serial
	 * @param userAgent
	 * @param remoteIpAddress
	 * @return
	 * @throws InformationException
	 */
	SessionRegister homeAuthentication(@NotNull @Min(value = 1L) Long homeId, @NotNull String serial,
			@NotNull String userAgent, @NotNull String remoteIpAddress) throws InformationException;

	/**
	 * 
	 * @param homeSelectionDTO
	 * @param tokenId
	 * @throws InformationException
	 */
	void homeSelection(@NotNull HomeSelectionDTO homeSelectionDTO, @NotNull String tokenId) throws InformationException;

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
	UserSessionVO findUserSessionVO(@NotNull String tokenId);

	/**
	 * 
	 * @param tokenId
	 */
	void endUserSession(@NotNull String tokenId);

	/**
	 * 
	 * @param tokenId
	 * @return
	 * @throws InformationException
	 */
	SessionRegister findUserSessionRegister(@NotNull String tokenId) throws InformationException;

}
