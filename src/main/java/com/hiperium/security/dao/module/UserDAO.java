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
package com.hiperium.security.dao.module;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.security.model.security.User;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface UserDAO {

	/**
	 * 
	 * @param profileId
	 * @return
	 * @throws InformationException
	 */
	List<User> findByProfileId(@NotNull @Min(value = 1L) Long profileId)
			throws InformationException;

	/**
	 * 
	 * @param homeId
	 * @param filterDTO
	 * @return
	 * @throws InformationException
	 */
	List<User> findUsers(@NotNull @Min(value = 1L) Long homeId,
			@NotNull User filterDTO) throws InformationException;

	/**
	 * 
	 * @param userEmail
	 * @return
	 */
	User findByEmail(@NotNull String userEmail);

	/**
	 * 
	 * @param id
	 * @param lock
	 * @param bypassCache
	 * @return
	 */
	User findById(Long id, boolean lock,
			boolean bypassCache);

	/**
	 * 
	 * @param register
	 * @return
	 */
	User update(User register);
}
