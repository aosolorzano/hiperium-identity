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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.dto.SelectionDTO;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.utils.EnumNamedQuerySecurity;
import com.hiperium.identity.dao.GenericDAO;
import com.hiperium.identity.dao.module.HomeDAO;
import com.hiperium.identity.model.security.Home;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class HomeDAOImpl extends GenericDAO<Home, Long> implements HomeDAO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;

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
	public Boolean findCloudEnable(@NotNull @Min(value = 1L) Long homeId) throws InformationException {
		this.log.debug("findCloudEnable - START");
		Boolean result = Boolean.FALSE;
		try {
			result = super
					.createNamedQuery(EnumNamedQuerySecurity.HOME_IS_CLOUD_ENABLE.getName(), Boolean.class, true)
					.setParameter("homeId", homeId)
					.getSingleResult();
		} catch (NoResultException e) {
			this.log.error("Error: No result to find home: " + homeId);
		}
		this.log.debug("findCloudEnable - END");
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SelectionDTO> findByUserId(@NotNull @Min(value = 1L) Long userId) {
		this.log.debug("findHomeSelection - BEGIN");
		List<SelectionDTO> homes = super
				.createNamedQuery(EnumNamedQuerySecurity.HOME_FIND_SELECTION_BY_USER_ID.getName(), SelectionDTO.class, false)
				.setParameter("userId", userId)
				.getResultList();
		this.log.debug("findHomeSelection - END");
		return homes;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Home findById(Long id, boolean lock, boolean bypassCache) {
		return super.findById(id, lock, bypassCache);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Home update(Home register) {
		return super.update(register);
	}
	
}
