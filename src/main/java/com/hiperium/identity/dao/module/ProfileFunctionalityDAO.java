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
import com.hiperium.identity.common.vo.ProfileFunctionalityVO;
import com.hiperium.identity.model.security.ProfileFunctionality;
import com.hiperium.identity.model.security.ProfileFunctionalityPK;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface ProfileFunctionalityDAO {

	/**
	 * 
	 * @param profileId
	 * @return
	 * @throws InformationException
	 */
	List<ProfileFunctionality> findByProfileId(
			@NotNull @Min(value = 1L) Long profileId)
			throws InformationException;

	/**
	 * 
	 * @param URL
	 * @param profileId
	 * @return
	 */
	ProfileFunctionalityVO findByProfileAndURL(@NotNull String URL,
			@NotNull @Min(value = 1L) Long profileId);

	/**
	 * 
	 * @param restURI
	 * @param profileId
	 * @return
	 */
	ProfileFunctionalityVO findByProfileAndRestURI(@NotNull String restURI,
			@NotNull @Min(value = 1L) Long profileId);

	/**
	 * 
	 * @param id
	 * @param lock
	 * @param bypassCache
	 * @return
	 */
	ProfileFunctionality findById(ProfileFunctionalityPK id, boolean lock,
			boolean bypassCache);

	/**
	 * 
	 * @param register
	 * @return
	 */
	ProfileFunctionality update(ProfileFunctionality register);

	/**
	 * 
	 * @param id
	 */
	void delete(ProfileFunctionalityPK id);
}
