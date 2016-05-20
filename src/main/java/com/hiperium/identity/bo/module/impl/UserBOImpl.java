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

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.generic.GenericBO;
import com.hiperium.identity.bo.module.UserBO;
import com.hiperium.identity.dao.list.ListOperationDAO;
import com.hiperium.identity.model.security.Home;
import com.hiperium.identity.model.security.Profile;
import com.hiperium.identity.model.security.User;
import com.hiperium.identity.model.security.UserHome;
import com.hiperium.identity.model.security.UserHomePK;

/**
 * This class represents the user service administration regardless with the
 * data base.
 * 
 * @author Andres Solorzano.
 * 
 */
@Stateless
public class UserBOImpl extends GenericBO implements UserBO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/** The property listOperationBO. */
	@EJB
	private ListOperationDAO<UserHome, UserHomePK> listOperationDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User update (@NotNull User user, @NotNull String tokenId) 
			throws InformationException {
		this.log.debug("homeSelection - START");
		user = super.getDaoFactory().getUserDAO().update(user);
		this.log.debug("homeSelection - END");
		return user;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserHomes(@NotNull @Min(value = 1L) Long userId, 
			@NotNull List<Home> selectedHomes, 
			@NotNull String tokenId) 
					throws InformationException {
		this.log.debug("updateUserHomes - START");
		User user = super.getDaoFactory().getUserDAO().findById(userId, false, false);
		// TODO: VALIDATE THAT DO NOT REMOVE THE USER ADMINISTRATOR HOME
		if (selectedHomes != null && !selectedHomes.isEmpty()) {
			List<UserHome> selectedUserHomes = new ArrayList<UserHome>();
			for(Home home : selectedHomes) {
				UserHomePK userHomePK = new UserHomePK(user.getId(), home.getId());
				UserHome userHome = super.getDaoFactory().getUserHomeDAO().findById(userHomePK, false, true);
				if(userHome == null) {
					userHome = new UserHome();
					userHome.setPk(userHomePK);
					userHome.setHome(home);
				}
				selectedUserHomes.add(userHome);
			}
			List<UserHome> actualUserHomes = super.getDaoFactory().getUserHomeDAO().findByUserId(user.getId());
			this.listOperationDAO.process(actualUserHomes, selectedUserHomes, tokenId);
		}
		this.log.debug("updateUserHomes - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User updateUserProfiles(@NotNull @Min(value = 1L) Long userId, 
			@NotNull List<Profile> selectedProfiles, 
			@NotNull String tokenId) 
					throws InformationException {
		this.log.debug("updateUserProfiles - START");
		this.log.debug("updateUserProfiles - END");
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public User findById(@NotNull @Min(value = 1L) Long id, boolean lock, @NotNull String tokenId) throws InformationException {
		return super.getDaoFactory().getUserDAO().findById(id, lock, false);
	}
}
