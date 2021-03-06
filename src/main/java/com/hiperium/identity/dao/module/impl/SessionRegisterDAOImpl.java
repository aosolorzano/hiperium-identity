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

import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.datastax.driver.core.BoundStatement;
import com.hiperium.commons.services.bean.BeanUtils;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.model.SessionRegister;
import com.hiperium.identity.common.bean.CassandraConnectorBean;
import com.hiperium.identity.dao.module.SessionRegisterDAO;

/**
 * This class manages all user's token used for external applications.
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SessionRegisterDAOImpl implements SessionRegisterDAO {

	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/** The property cassandraConnector. */
	@EJB
	private CassandraConnectorBean cassandraConnector;
	
	/** The property createSessionRegisterBS. */
	private BoundStatement createSessionRegisterBS;
	/** The property updateHomeSelectionBS. */
	private BoundStatement updateHomeSelectionBS;
	/** The property updateLogoutDateBS. */
	private BoundStatement updateLogoutDateBS;
	
	/**
	 * Component post construct.
	 */
	@PostConstruct
	public void init() {
		// Prepared statement for persist session register
		this.createSessionRegisterBS = new BoundStatement(this.cassandraConnector.getInsertSessionRegisterPS());
		// Prepared statement for home selection audit
		this.updateHomeSelectionBS = new BoundStatement(this.cassandraConnector.getUpdateHomeSelectionPS());
		// Prepared statement for logout audit
		this.updateLogoutDateBS = new BoundStatement(this.cassandraConnector.getUpdateLogoutDatePS());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SessionRegister create(@NotNull SessionRegister sessionRegister) {
		this.log.debug("create() - BEGIN");
		sessionRegister.setId(BeanUtils.uuidForDate(new Date()));
		sessionRegister.setLoginDate(new Date());
		//TODO: Implement JWT too.
		sessionRegister.setTokenId("Bearer ".concat(UUID.randomUUID().toString()));
		this.cassandraConnector.getSession().execute(this.createSessionRegisterBS.bind(
				sessionRegister.getId(),
				sessionRegister.getUserId(),
				sessionRegister.getTokenId(),
				sessionRegister.getLoginDate(),
				sessionRegister.getActive(),
				sessionRegister.getIpConnection(),
				sessionRegister.getUserAgent(),
				sessionRegister.getAuthenticationResult().name(),
				sessionRegister.getAccessChannel().name()
				));
		this.log.debug("create() - END");
		return sessionRegister;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateHomeSelection(@NotNull SessionRegister sessionRegister) {
		this.log.debug("updateHomeSelection() - BEGIN");
		this.cassandraConnector.getSession().execute(this.updateHomeSelectionBS.bind(sessionRegister.getHomeId(), 
				sessionRegister.getProfileId(), sessionRegister.getId()));
		this.log.debug("updateHomeSelection() - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateLogoutDate(@NotNull UUID id) {
		this.log.debug("updateLogoutDate() - BEGIN");
		this.cassandraConnector.getSession().execute(this.updateLogoutDateBS.bind(new Date(), id));
		this.log.debug("updateLogoutDate() - END");
	}

}
