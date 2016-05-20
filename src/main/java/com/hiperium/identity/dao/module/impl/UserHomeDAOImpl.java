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
package com.hiperium.identity.dao.module.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.identity.dao.EnumNamedQuerySecurity;
import com.hiperium.identity.dao.GenericDAO;
import com.hiperium.identity.dao.module.UserHomeDAO;
import com.hiperium.identity.model.security.UserHome;
import com.hiperium.identity.model.security.UserHomePK;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UserHomeDAOImpl extends GenericDAO<UserHome, UserHomePK> implements UserHomeDAO {

	/** The property entityManger. */
	@Inject
    private EntityManager entityManager;
	
	/**
	 * Component post contruct.
	 */
	@PostConstruct
	public void init() {
		super.setEntityManager(this.entityManager);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserHome> findByUserId(@NotNull @Min(value = 1L) Long userId) throws InformationException {
		return super.createNamedQuery(EnumNamedQuerySecurity.USER_HOME_FIND_BY_USERI_ID.getName(), UserHome.class, true)
				.setParameter("userId", userId)
				.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserHome findById(UserHomePK id, boolean lock, boolean bypassCache) {
		return super.findById(id, lock, bypassCache);
	}
}
