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
package com.hiperium.identity.common;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.datastax.driver.core.Cluster;
import com.hiperium.common.services.gson.GsonConverterUtil;
import com.hiperium.common.services.logger.HiperiumLogger;

/**
 * 
 * @author Andres Solorzano
 * @version 1.0
 */
@Startup
@Singleton
@LocalBean
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigurationBean {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(ConfigurationBean.class);
	
	/** The property ZK_HOST. */
	private static final String ZK_HOST = "apache.zookeeper.host";
	
	/** The property SESSION_MAP_NAME. */
	public static final String SESSION_MAP_NAME = "httpSessionsMap";
	/** The property SESSION_VALUE_NAME. */
	public static final String SESSION_VALUE_NAME = "sessionRegisterObject";
	
	/** The property SERVER_PORT with value hiperium.server.port. */
	public static final String SERVER_PORT = "hiperium.server.port";
	/** The property SERVER_HOST with value hiperium.server.host. */
	public static final String SERVER_HOST = "hiperium.server.host";
	
	/** The property CASSANDRA_PORT with value apache.cassandra.port. */
	public static final String CASSANDRA_PORT = "apache.cassandra.port";
	/** The property CASSANDRA_HOST with value apache.cassandra.host. */
	public static final String CASSANDRA_HOST = "apache.cassandra.host";
	
	/** The property PROPERTIES. */
	private static final Properties PROPERTIES = new Properties();
	
	/** The property entityManger. */
	@PersistenceContext(unitName = "hiperiumSecurityPU")
	private EntityManager entityManager;

	/** Cassandra Cluster. */
	private Cluster cluster;
	
	/** The property client. */
	private CuratorFramework client;
	
	/**
	 * Class initialization.
	 */
	static {
		try {
			PROPERTIES.load(ConfigurationBean.class.getClassLoader().getResourceAsStream("common.properties"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Searches for the property with the specified key in this resource object.
	 * The method returns null if the property is not found.
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(String key) {
		return PROPERTIES.getProperty(key);
	}

	/**
	 * Component initialization.
	 */
	@PostConstruct
	public void init() {
		LOGGER.debug("init() - START");
		// CONNECT TO CASSANDRA CLUSTER
		this.cluster = Cluster.builder().addContactPoint(getPropertyValue(CASSANDRA_HOST))
				.withPort(Integer.parseInt(getPropertyValue(CASSANDRA_PORT)))
				.build();
		// START CURATOR CLIENT
		this.client = CuratorFrameworkFactory.newClient(
				getPropertyValue(ZK_HOST), 
				new ExponentialBackoffRetry(1000, 3));
		this.client.start();
		LOGGER.debug("init() - END");
	}
	
	/**
	 * 
	 * @param injectionPoint
	 * @return the entityManager
	 */
	@Produces
	public EntityManager getEntityManager(InjectionPoint injectionPoint) {
		return this.entityManager;
	}
	
	/**
	 * 
	 * @param injectionPoint
	 * @return the client
	 */
	@Produces
	public CuratorFramework getClient(InjectionPoint injectionPoint) {
		return this.client;
	}

	/**
	 * 
	 * @param injectionPoint
	 * @return the cluster
	 */
	@Produces
	public Cluster getCluster(InjectionPoint injectionPoint) {
		return this.cluster;
	}
	
	/**
	 * Produces the HiperiumLogger component for CDI injection.
	 * 
	 * @param injectionPoint
	 * @return
	 */
	@Produces
	public HiperiumLogger produceHiperiumLogger(InjectionPoint injectionPoint) {
		return HiperiumLogger.getLogger(injectionPoint.getMember().getDeclaringClass());
	}
	
	/**
	 * 
	 * @param injectionPoint
	 * @return
	 */
	@Produces
	public GsonConverterUtil getGsonConverterUtil(InjectionPoint injectionPoint) {
		return new GsonConverterUtil();
	}
	
	/**
	 * Call the garbage collector every 2 hours.
	 */
	@Schedule(hour = "*/2")
	private void deleteIsolatedObjects() {
		System.gc();
	}
	
	/**
	 * Close all opened resources.
	 */
	@PreDestroy
	public void destroy() {
		LOGGER.debug("destroy() - START");
		// DO NOT CLOSE CURATOR CLIENT HERE, IT MUST BE OPENED TO THE END.
		this.cluster.close();
		LOGGER.debug("destroy() - END");
	}
}
