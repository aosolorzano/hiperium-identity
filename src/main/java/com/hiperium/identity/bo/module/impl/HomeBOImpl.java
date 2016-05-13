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

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.dto.SelectionDTO;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.generic.GenericBO;
import com.hiperium.identity.bo.module.HomeBO;
import com.hiperium.identity.model.security.Home;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class HomeBOImpl extends GenericBO implements HomeBO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Home update(@NotNull Home home, @NotNull String tokenId) 
			throws InformationException {
		this.log.debug("homeSelection - START");
		Home actualHome = super.getDaoFactory().getHomeDAO().findById(home.getId(), false, true);
		actualHome.setComments(home.getComments());
		actualHome.setName(home.getName());
		actualHome = super.getDaoFactory().getHomeDAO().update(actualHome);
		this.log.debug("homeSelection - END");
		return actualHome;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SelectionDTO> findByUserId(@NotNull @Min(value = 1L) Long userId, @NotNull String tokenId) {
		return super.getDaoFactory().getHomeDAO().findByUserId(userId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean findCloudEnable(@NotNull @Min(value = 1L) Long homeId) throws InformationException {
		return super.getDaoFactory().getHomeDAO().findCloudEnable(homeId);
	}
}
