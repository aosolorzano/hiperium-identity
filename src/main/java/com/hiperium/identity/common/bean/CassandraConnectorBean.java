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
package com.hiperium.identity.common.bean;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.hiperium.common.services.logger.HiperiumLogger;

/**
 * This service class represents the driver connector for the Apache Cassandra
 * instance.
 *
 * @author Andres Solorzano
 *
 */
@Startup
@Singleton
@LocalBean
@Lock(LockType.READ)
@DependsOn("ConfigurationBean")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CassandraConnectorBean {
	
	/** The property INSERT_SESSION_REGISTER. */
	private static final String INSERT_SESSION_REGISTER = "INSERT INTO haudit.aud_session_register (id,user_id,token_id,login_date,active,ip_connection,user_agent,auth_result,access_channel) "
			+ "VALUES (?,?,?,?,?,?,?,?,?);";
	
	/** The property UPDATE_HOME_SELECTION_QUERY. */
	private static final String UPDATE_HOME_SELECTION_QUERY = "UPDATE haudit.aud_session_register SET home_id=?, profile_id=? WHERE id=?;";
	
	/** The property UPDATE_LOGOUT_DATE_QUERY. */
	private static final String UPDATE_LOGOUT_DATE_QUERY = "UPDATE haudit.aud_session_register SET logout_date=? WHERE id=?;";
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;

	/** Cassandra Cluster. */
	@Inject
	private Cluster cluster;
	/** Cassandra Session. */
	private Session session;
	
	/** The property insertSessionRegisterPS. */
	private PreparedStatement insertSessionRegisterPS;
	/** The property updateHomeSelectionPS. */
	private PreparedStatement updateHomeSelectionPS;
	/** The property updateLogoutDatePS. */
	private PreparedStatement updateLogoutDatePS;
	
	/**
	 * Component constructor.
	 */
	@PostConstruct
	public void init() {
		this.log.debug("init() - BEGIN");
		final Metadata metadata = this.cluster.getMetadata();
		this.log.debug("Connected to cluster: " + metadata.getClusterName());
		for (final Host host : metadata.getAllHosts()) {
			this.log.debug("Datacenter: " + host.getDatacenter() + " " + host.getAddress() + " " + host.getRack());
		}
		this.session = this.cluster.connect();
		
		// Prepared statement for session register audit
		this.insertSessionRegisterPS = this.session.prepare(INSERT_SESSION_REGISTER);
		// Prepared statement for home selection audit
		this.updateHomeSelectionPS = this.session.prepare(UPDATE_HOME_SELECTION_QUERY);
		// Prepared statement for logout audit
		this.updateLogoutDatePS = this.session.prepare(UPDATE_LOGOUT_DATE_QUERY);
				
		this.log.debug("init() - END");
	}

	/**
	 * Provide Cassandra Session to be injected in another component.
	 *
	 * @return My session.
	 */
	@Produces
	public Session getSession() {
		return this.session;
	}

	/**
	 * @return the insertSessionRegisterPS
	 */
	public PreparedStatement getInsertSessionRegisterPS() {
		return insertSessionRegisterPS;
	}

	/**
	 * @return the updateHomeSelectionPS
	 */
	public PreparedStatement getUpdateHomeSelectionPS() {
		return updateHomeSelectionPS;
	}

	/**
	 * @return the updateLogoutDatePS
	 */
	public PreparedStatement getUpdateLogoutDatePS() {
		return updateLogoutDatePS;
	}
	
}
