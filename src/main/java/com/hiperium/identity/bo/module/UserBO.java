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

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.identity.model.security.Home;
import com.hiperium.identity.model.security.Profile;
import com.hiperium.identity.model.security.User;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface UserBO {

	/**
	 * 
	 * @param user
	 *            the home identifier
	 * @param tokenId
	 *            the session identifier
	 * @return
	 * @throws InformationException
	 */
	User update(@NotNull User user, @NotNull String tokenId)
			throws InformationException;

	/**
	 * 
	 * @param userId
	 * @param homes
	 * @param tokenId
	 * @return
	 * @throws InformationException
	 */
	void updateUserHomes(@NotNull @Min(value = 1L) Long userId,
			@NotNull List<Home> homes, @NotNull String tokenId)
			throws InformationException;

	/**
	 * 
	 * @param userId
	 * @param profiles
	 * @param tokenId
	 * @return
	 * @throws InformationException
	 */
	User updateUserProfiles(@NotNull @Min(value = 1L) Long userId,
			@NotNull List<Profile> profiles, @NotNull String tokenId)
			throws InformationException;

	/**
	 * 
	 * @param id
	 * @param lock
	 * @param tokenId
	 * @return
	 * @throws InformationException
	 */
	User findById(@NotNull @Min(value = 1L) Long id, boolean lock,
			@NotNull String tokenId) throws InformationException;
	
}
