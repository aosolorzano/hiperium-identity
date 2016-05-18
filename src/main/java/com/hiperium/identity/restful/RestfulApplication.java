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
package com.hiperium.identity.restful;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;

import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.restful.identity.IdentityRegistryPath;
import com.hiperium.common.services.restful.identity.IdentityRestfulPath;
import com.hiperium.common.services.restful.registry.ServiceRegister;
import com.hiperium.identity.common.bean.ConfigurationBean;

/**
 * This class represents the JAX-RS Activation Implementation for JAX-RS
 * applications.
 * 
 * @author Andres Solorzano
 *
 */
@ApplicationPath(IdentityRestfulPath.IDENTITY_PATH)
public class RestfulApplication extends Application {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(RestfulApplication.class);

	/** The property client. */
	@Inject
	private CuratorFramework client;
	
	/** The property servers. */
	private List<ServiceRegister> registers;
	
	/** The property serviceHost. */
	private String serviceHost; 
	/** The property servicePort. */
	private Integer servicePort;
	
	/**
	 * Register all web services in apache zookeeper service registry.
	 */
	@PostConstruct
	public void init() {
		LOGGER.info("init() - START");
		this.registers = new ArrayList<ServiceRegister>();
		this.serviceHost = ConfigurationBean.getPropertyValue(ConfigurationBean.SERVER_HOST);
		this.servicePort = Integer.valueOf(ConfigurationBean.getPropertyValue(ConfigurationBean.SERVER_PORT));
		this.registerService(IdentityRestfulPath.AUTHENTICATION.concat(IdentityRestfulPath.USER_AUTH),             IdentityRegistryPath.USER_AUTHENTICATION,          "1.0", "");
		this.registerService(IdentityRestfulPath.AUTHENTICATION.concat(IdentityRestfulPath.HOME_AUTH),             IdentityRegistryPath.HOME_AUTHENTICATION,          "1.0", "");
		this.registerService(IdentityRestfulPath.AUTHENTICATION.concat(IdentityRestfulPath.IS_USER_LOGGED_IN),     IdentityRegistryPath.IS_USER_LOGGED_IN,            "1.0", "");
		this.registerService(IdentityRestfulPath.AUTHENTICATION.concat(IdentityRestfulPath.GET_USER_SESSION_VO),   IdentityRegistryPath.GET_USER_SESSION_VO,          "1.0", "");
		this.registerService(IdentityRestfulPath.AUTHENTICATION.concat(IdentityRestfulPath.LOGOUT),                IdentityRegistryPath.LOGOUT,          			  "1.0", "");
		this.registerService(IdentityRestfulPath.HOME_SELECTION,                                                   IdentityRegistryPath.HOME_SELECTION,               "1.0", "");
		this.registerService(IdentityRestfulPath.APPLICATION_USER,                                                 IdentityRegistryPath.FIND_APP_USER_BY_ROLE_NAME,   "1.0", "");
		this.registerService(IdentityRestfulPath.HOMES.concat(IdentityRestfulPath.FIND_HOME_USER_BY_ID),           IdentityRegistryPath.FIND_HOME_USER_BY_ID,         "1.0", "");
		this.registerService(IdentityRestfulPath.PROFILE_FUNCTIONALITY.concat(IdentityRestfulPath.UPDATE),         IdentityRegistryPath.UPDATE_PROFILE_FUNCTIONALITY, "1.0", "");
		this.registerService(IdentityRestfulPath.PROFILES.concat(IdentityRestfulPath.CREATE),                      IdentityRegistryPath.CREATE_PROFILE,               "1.0", "");
		this.registerService(IdentityRestfulPath.PROFILES.concat(IdentityRestfulPath.UPDATE),                      IdentityRegistryPath.UPDATE_PROFILE,               "1.0", "");
		this.registerService(IdentityRestfulPath.PROFILES.concat(IdentityRestfulPath.DELETE),                      IdentityRegistryPath.DELETE_PROFILE,               "1.0", "");
		this.registerService(IdentityRestfulPath.PROFILES.concat(IdentityRestfulPath.FIND_PROFILE_BY_HOME_ID),     IdentityRegistryPath.FIND_PROFILE_BY_HOME_ID,      "1.0", "");
		this.registerService(IdentityRestfulPath.UPDATE_PASSWORD,                                                  IdentityRegistryPath.UPDATE_USER_PASSWORD,         "1.0", "");
		this.registerService(IdentityRestfulPath.USERS.concat(IdentityRestfulPath.FIND_USER_BY_ID),                IdentityRegistryPath.FIND_USER_BY_ID,              "1.0", "");
		LOGGER.info("init() - END");
	}

	/**
	 * 
	 * @param servicePath
	 * @param serviceName
	 * @param serviceVersion
	 * @param serviceDetails
	 */
	public void registerService(String servicePath, String serviceName, String serviceVersion, String serviceDetails) {
		String serviceURI = this.getUri(servicePath);
		ServiceRegister server;
		try {
			server = new ServiceRegister(this.client, this.servicePort, serviceURI, serviceName, IdentityRegistryPath.BASE_PATH, serviceDetails, serviceVersion);
			server.start();
			this.registers.add(server);
			LOGGER.info("Service added to the Registry: " + serviceURI);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Close all opened resource in this component.
	 */
	@PreDestroy
	public void destroy() {
		for(ServiceRegister register : this.registers) {
			CloseableUtils.closeQuietly(register);
		}
		this.registers.clear();
		this.client.close();
	}
	
	/**
	 * 
	 * @param servicePath
	 * @return
	 */
	private String getUri(final String servicePath) {
		return String.format("{scheme}://%s:{port}%s%s%s", 
				this.serviceHost,
				IdentityRestfulPath.IDENTITY_CONTEXT_ROOT, 
				IdentityRestfulPath.IDENTITY_PATH, 
				servicePath);
	}
}
