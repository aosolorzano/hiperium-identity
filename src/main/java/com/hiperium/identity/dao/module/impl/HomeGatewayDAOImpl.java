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

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.identity.dao.EnumNamedQuerySecurity;
import com.hiperium.identity.dao.GenericDAO;
import com.hiperium.identity.dao.module.HomeGatewayDAO;
import com.hiperium.identity.model.security.HomeGateway;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class HomeGatewayDAOImpl extends GenericDAO<HomeGateway, Long> implements HomeGatewayDAO {

	/** The property log. */
	@Inject
	private HiperiumLogger log;

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
	public Long findGatewayIdByHomeSerial(@NotNull @Min(value = 1L) Long homeId, @NotNull String serial) {
		Long gatewayId = null;
		try {
			// Find the Raspberry registered with the serial received
			gatewayId = super.createNamedQuery(EnumNamedQuerySecurity.HOME_GATEWAY_FIND_BY_HOME_SERIAL.getName(), Long.class, true)
				.setParameter("homeId", homeId)
				.setParameter("serial", serial)
				.getSingleResult();
		} catch (NoResultException e) {
			this.log.error("Error: No Home Gateway found with serial: " + serial);
		}
		return gatewayId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String findTokenByHomeId(@NotNull @Min(value = 1L) Long homeId) {
		String token = null;
		try {
			token = super
					.createNamedQuery(EnumNamedQuerySecurity.HOME_GATEWAY_FIND_TOKEN_BY_HOME_ID.getName(), String.class, true)
					.setParameter("homeId", homeId)
					.getSingleResult();
		} catch (NoResultException e) {
			this.log.error("Error: No Token found for home ID: " + homeId);
		}
		return token;
	}

}
