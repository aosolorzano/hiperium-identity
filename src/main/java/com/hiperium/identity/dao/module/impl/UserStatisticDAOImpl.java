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

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.model.UserStatistic;
import com.hiperium.identity.common.bean.CassandraConnectorBean;
import com.hiperium.identity.dao.module.UserStatisticDAO;

/**
 * This service is the implementation of the interface DeviceLocal and manages
 * all actions needed for device management.
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserStatisticDAOImpl implements UserStatisticDAO {

	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/** The property cassandraConnector. */
	@EJB
	private CassandraConnectorBean cassandraConnector;
	
	/** The property findUserStatisticBS. */
	private BoundStatement findUserStatisticBS;
	
	/**
	 * Component post construct.
	 */
	@PostConstruct
	public void init() {
		// Prepared statement for persist session register
		this.findUserStatisticBS = new BoundStatement(this.cassandraConnector.getFindUserStatisticPS());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateLastPasswordChange(Long userId) {
		this.log.debug("updateLastPasswordChange() - BEGIN");
		Statement exampleQuery = QueryBuilder.update("haudit", "aud_user_statistic")
				.with(QueryBuilder.set("last_password_change", new Date()))
				.where(QueryBuilder.eq("user_id", userId));
		this.cassandraConnector.getSession().execute(exampleQuery);
		this.log.debug("updateLastPasswordChange() - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserStatistic findById(Long userId) {
		UserStatistic userStatistic = null;
		ResultSet result = this.cassandraConnector.getSession().execute(this.findUserStatisticBS.bind(userId));
		if(result != null) {
			for (Row row : result) {
				userStatistic = new UserStatistic();
				userStatistic.setUserId(userId);
				userStatistic.setLastPasswordChange(row.getDate("last_password_change"));
				userStatistic.setFailedAttempts(row.getInt("failed_attempts"));
				userStatistic.setLastFailedAttempts(row.getInt("last_failed_attempts"));
				break;
			}
		}
		return userStatistic;
	}
}
