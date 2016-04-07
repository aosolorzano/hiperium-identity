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
package com.hiperium.security.bo.module;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.security.model.security.Home;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface HomeProtectionBO {

	/**
	 * 
	 * @param protectedMode
	 * @param homeId
	 * @param tokenId
	 * @throws InformationException
	 */
	Home updateProtectedMode(boolean protectedMode,
			@NotNull @Min(value = 1L) Long homeId, @NotNull String tokenId)
			throws InformationException;
}
