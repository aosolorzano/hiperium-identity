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
package com.hiperium.identity.audit.bo;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.services.model.UserStatistic;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface AuditManagerBO {

	/**
	 * 
	 * @param userId
	 * @param token
	 * @return
	 * @throws Exception
	 */
	UserStatistic findUserStatisticById(@NotNull @Min(value = 1L) Long userId, @NotNull String token) throws Exception;

	/**
	 * 
	 * @param userId
	 * @param token
	 * @throws Exception
	 */
	void updateLastPasswordChange(@NotNull @Min(value = 1L) Long userId, @NotNull String token) throws Exception;

}
