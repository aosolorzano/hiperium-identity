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

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.identity.model.security.ProfileFunctionality;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface ProfileFunctionalityBO {

	/**
	 * 
	 * @param register
	 * @param tokenId
	 * @return
	 * @throws InformationException
	 */
	ProfileFunctionality update(@NotNull ProfileFunctionality register,
			@NotNull String tokenId) throws InformationException;

	/**
	 * 
	 * @param register
	 * @param tokenId
	 * @throws InformationException
	 */
	void delete(@NotNull ProfileFunctionality register,
			@NotNull String tokenId) throws InformationException;
}
