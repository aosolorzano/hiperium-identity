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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface UpdatePasswordBO {

	/**
	 * 
	 * @param userId
	 * @param newPassword
	 * @param prevPassword
	 * @param tokenId
	 * @throws InformationException
	 */
	void updateUserPassword(@NotNull @Min(value = 1L) Long userId, @NotNull String newPassword,
			@NotNull String prevPassword, @NotNull String tokenId) throws InformationException;
}
