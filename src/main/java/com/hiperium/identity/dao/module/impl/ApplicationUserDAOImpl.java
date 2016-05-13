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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.identity.dao.module.ApplicationUserDAO;

/**
 * 
 * @author Andres Solorzano
 * @version 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApplicationUserDAOImpl implements ApplicationUserDAO {
	
	/** The property QUERY_FIND_BY_ROLE_NAME. */
	private static final String QUERY_FIND_BY_ROLE_NAME = "SELECT u.username, u.password FROM hsecurity.sec_application_user u WHERE u.role = ?";
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/** The property entityManger. */
	@Inject
    private EntityManager entityManager;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> findByRole(@NotNull String role) throws NoResultException {
		List<String> result = new ArrayList<String>();
		try {
			List<Object[]> rows = this.entityManager.createNativeQuery(QUERY_FIND_BY_ROLE_NAME)
					.setParameter(1, role)
					.getResultList();
			Iterator<Object[]> it = rows.iterator();
			while (it.hasNext()) {
				Object[] row = (Object[]) it.next();
				result.add((String) row[0]);
				result.add((String) row[1]);
			}
		} catch (Exception e) {
			this.log.error(e.getMessage(), e);
		}
		if(result.isEmpty()) {
			throw new NoResultException("No register found with role: " + role);
		}
		return result;
	}
	
}
