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
import javax.persistence.Query;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.dto.SelectionDTO;
import com.hiperium.identity.dao.EnumNamedQuerySecurity;
import com.hiperium.identity.dao.GenericDAO;
import com.hiperium.identity.dao.module.ProfileDAO;
import com.hiperium.identity.model.security.Profile;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProfileDAOImpl extends GenericDAO<Profile, Long> implements ProfileDAO {

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
	public List<SelectionDTO> findByHomeId(@NotNull @Min(value = 1L) Long userId, @NotNull @Min(value = 1L) Long homeId) {
		List<SelectionDTO> profiles = super
				.createNamedQuery(EnumNamedQuerySecurity.PROFILE_FIND_SELECTION_BY_HOME_ID.getName(), SelectionDTO.class, false)
				.setParameter("userId", userId)
				.setParameter("homeId", homeId)
				.getResultList();
		return profiles;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(Profile register) {
		Query query = super.getEntityManager().createNamedQuery(EnumNamedQuerySecurity.PROFILE_FIND_SELECTION_BY_HOME_ID.getName());
		Long id = query.getSingleResult() == null ? 1L : ((Long) query.getSingleResult()) + 1L;
		register.setId(id);
		super.create(register);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Profile update(Profile register) {
		return super.update(register);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Long id) {
		Profile register = super.getReference(id);
		super.delete(register);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Profile findById(Long id, boolean lock, boolean bypassCache) {
		return super.findById(id, lock, bypassCache);
	}
	
}
