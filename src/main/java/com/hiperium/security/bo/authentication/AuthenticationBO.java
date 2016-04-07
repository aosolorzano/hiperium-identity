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
package com.hiperium.security.bo.authentication;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.dto.HomeAuthResponseDTO;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.security.common.dto.UserAuthResponseDTO;

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
	UserAuthResponseDTO userAuthentication(@NotNull String userEmail,
			@NotNull String userPassword, @NotNull String userAgent,
			@NotNull String remoteIpAddress) throws InformationException;

	/**
	 * 
	 * @param homeId
	 * @param profileId
	 * @param tokenId
	 * @return
	 * @throws InformationException
	 */
	void homeSelection(@NotNull @Min(value = 1L) Long homeId,
			@NotNull @Min(value = 1L) Long profileId, @NotNull String tokenId)
			throws InformationException;

	/**
	 * 
	 * @param homeId
	 * @param serial
	 * @param userAgent
	 * @param remoteIpAddress
	 * @return
	 * @throws InformationException
	 */
	HomeAuthResponseDTO homeAuthentication(@NotNull @Min(value = 1L) Long homeId,
			@NotNull String serial, @NotNull String userAgent,
			@NotNull String remoteIpAddress)
			throws InformationException;
}
