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

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.security.bo.generic.GenericBO;
import com.hiperium.security.bo.module.ApplicationUserBO;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class ApplicationUserBOImpl extends GenericBO implements ApplicationUserBO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> findByRole(String role) throws NoResultException {
		this.log.debug("findByRole - START");
		return super.getDaoFactory().getApplicationUserDAO().findByRole(role);
	}
}
