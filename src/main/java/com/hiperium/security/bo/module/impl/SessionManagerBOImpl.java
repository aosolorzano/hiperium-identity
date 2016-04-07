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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.constraints.NotNull;

import com.hiperium.security.bo.module.SessionManagerBO;
import com.hiperium.security.common.SessionManagerBean;
import com.hiperium.security.common.vo.ProfileFunctionalityVO;
import com.hiperium.security.dao.module.ProfileFunctionalityDAO;

/**
 * This is a bypass bean that is used between Web components and EJB components
 * but only for some methods that the Web components can see.
 *
 * @author Andres Solorzano
 * @version 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SessionManagerBOImpl implements SessionManagerBO {

	/** The property sessionManager. */
	@EJB
	private SessionManagerBean sessionManager;
	
	/** The property profileFunctionalityDAO. */
	@EJB
	private ProfileFunctionalityDAO profileFunctionalityDAO;

	/**
	 *
	 * @param URL
	 * @param tokenId
	 * @return
	 */
	@Override
	public ProfileFunctionalityVO findByProfileAndURL(@NotNull String URL,
			@NotNull String tokenId) {
		return this.profileFunctionalityDAO.findByProfileAndURL(URL,
				this.sessionManager.findUserProfileId(tokenId));
	}

	/**
	 *
	 * @param restURI
	 * @param tokenId
	 * @return
	 */
	@Override
	public ProfileFunctionalityVO findByProfileAndRestURI(
			@NotNull String restURI, @NotNull String tokenId) {
		return this.profileFunctionalityDAO.findByProfileAndRestURI(restURI,
				this.sessionManager.findUserProfileId(tokenId));
	}
}
