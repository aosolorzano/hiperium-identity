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
package com.hiperium.security.bo.module.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.security.bo.generic.GenericBO;
import com.hiperium.security.bo.module.HomeGatewayBO;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class HomeGatewayBOImpl extends GenericBO implements HomeGatewayBO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String findTokenInSession(@NotNull @Min(value = 1L) Long homeId,
			@NotNull String tokenId) 
					throws InformationException {
		this.log.debug("findTokenInSession - START");
		return super.getDaoFactory().getHomeGatewayDAO().findTokenByHomeId(homeId);
	}
}
