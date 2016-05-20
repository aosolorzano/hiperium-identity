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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.identity.bo.utils.EnumNamedQuerySecurity;
import com.hiperium.identity.dao.GenericDAO;
import com.hiperium.identity.dao.module.UserDAO;
import com.hiperium.identity.model.security.User;

/**
 * This class represents the user service administration regardless with the
 * data base.
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UserDAOImpl extends GenericDAO<User, Long> implements UserDAO {
	
	/** The property entityManger. */
	@Inject
    private EntityManager entityManager;
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		super.setEntityManager(this.entityManager);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findByProfileId(@NotNull @Min(value = 1L) Long profileId) throws InformationException {
		return super.createNamedQuery("User.findByProfileId", User.class, true)
				.setParameter("profileId", profileId)
				.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<User> findUsers(@NotNull @Min(value = 1L) Long homeId, @NotNull User filterDTO) throws InformationException {
		// TODO: USE CRITERIA
		StringBuilder queryBuilder = new StringBuilder("SELECT u FROM Home h join h.users u WHERE h.id = :homeId");

		// BUILD THE QUERY
		if (StringUtils.isNotBlank(filterDTO.getEmail())) {
			queryBuilder.append(" AND u.email like :userEmail");
		}
		if (StringUtils.isNotBlank(filterDTO.getFirstname())) {
			queryBuilder.append(" AND u.name like :firstname");
		}
		if (StringUtils.isNotBlank(filterDTO.getLastname())) {
			queryBuilder.append(" AND u.lastname like :lastname");
		}
		queryBuilder.append(" ORDER BY u.email");

		// CREATE THE QUERY
		Query query = super.createQuery(queryBuilder.toString(), true).setParameter("homeId", homeId);
		if (StringUtils.isNotBlank(filterDTO.getEmail())) {
			query.setParameter("userEmail", filterDTO.getEmail().concat("%"));
		}
		if (StringUtils.isNotBlank(filterDTO.getFirstname())) {
			query.setParameter("name", filterDTO.getFirstname().concat("%"));
		}
		if (StringUtils.isNotBlank(filterDTO.getLastname())) {
			query.setParameter("lastname", filterDTO.getLastname().concat("%"));
		}
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByEmail(String userEmail) {
		// FINDS A USER
		User user;
		try {
			user = (User) super.createNamedQuery(EnumNamedQuerySecurity.USER_FIND_BY_EMAIL.getName(), true)
					.setParameter("userEmail", userEmail)
					.getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		return user;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findById(Long id, boolean lock, boolean bypassCache) {
		return super.findById(id, lock, bypassCache);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User update(User register) {
		return super.update(register);
	}
}
