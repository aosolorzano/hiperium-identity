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
package com.hiperium.identity.bo.module.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.generic.GenericBO;
import com.hiperium.identity.bo.module.HomeProtectionBO;
import com.hiperium.identity.model.security.Home;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class HomeProtectionBOImpl extends GenericBO implements HomeProtectionBO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Home updateProtectedMode(boolean protectedMode, 
			@NotNull @Min(value = 1L) Long homeId,
			@NotNull String tokenId) 
					throws InformationException {
		this.log.debug("updateProtectedMode - START");
		Home home = super.getDaoFactory().getHomeDAO().findById(homeId, false, true);
		home.setProtectedMode(protectedMode);
		home = super.getDaoFactory().getHomeDAO().update(home);
		this.log.debug("updateProtectedMode - END");
		return home;
	}
}
