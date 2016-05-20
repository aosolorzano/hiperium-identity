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
package com.hiperium.identity.dao.module;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.identity.model.security.UserHome;
import com.hiperium.identity.model.security.UserHomePK;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface UserHomeDAO {

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws InformationException
	 */
	List<UserHome> findByUserId(@NotNull @Min(value = 1L) Long userId)
			throws InformationException;

	/**
	 * 
	 * @param id
	 * @param lock
	 * @param bypassCache
	 * @return
	 */
	UserHome findById(UserHomePK id, boolean lock, boolean bypassCache);

}
