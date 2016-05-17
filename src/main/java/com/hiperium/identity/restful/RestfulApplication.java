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

import java.net.InetAddress;
import java.net.UnknownHostException;
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
import com.hiperium.common.services.restful.IdentityRegistryPath;
import com.hiperium.common.services.restful.registry.ServiceRegister;
import com.hiperium.identity.common.bean.ConfigurationBean;

/**
 * This class represents the JAX-RS Activation Implementation for JAX-RS
 * applications.
 * 
 * @author Andres Solorzano
 *
 */
@ApplicationPath(RestIdentityPath.IDENTITY_PATH)
public class RestfulApplication extends Application {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(RestfulApplication.class);

	/** The property client. */
	@Inject
	private CuratorFramework client;
	
	/** The property servers. */
	private List<ServiceRegister> registers;
	
	/** The property servicePort. */
	private Integer servicePort;
	
	/**
	 * Register all web services in apache zookeeper service registry.
	 */
	@PostConstruct
	public void init() {
		LOGGER.info("init() - START");
		this.registers = new ArrayList<ServiceRegister>();
		this.servicePort = Integer.valueOf(ConfigurationBean.getPropertyValue(ConfigurationBean.SERVER_PORT));
		this.registerService(RestIdentityPath.AUTHENTICATION.concat(RestIdentityPath.USER_AUTH),             IdentityRegistryPath.USER_AUTHENTICATION,          "1.0", "");
		this.registerService(RestIdentityPath.AUTHENTICATION.concat(RestIdentityPath.HOME_AUTH),             IdentityRegistryPath.HOME_AUTHENTICATION,          "1.0", "");
		this.registerService(RestIdentityPath.HOME_SELECTION,                                                IdentityRegistryPath.HOME_SELECTION,               "1.0", "");
		this.registerService(RestIdentityPath.APPLICATION_USER,                                              IdentityRegistryPath.FIND_APP_USER_BY_ROLE_NAME,   "1.0", "");
		this.registerService(RestIdentityPath.HOMES.concat(RestIdentityPath.FIND_HOME_USER_BY_ID),           IdentityRegistryPath.FIND_HOME_USER_BY_ID,         "1.0", "");
		this.registerService(RestIdentityPath.PROFILE_FUNCTIONALITY.concat(RestIdentityPath.UPDATE),         IdentityRegistryPath.UPDATE_PROFILE_FUNCTIONALITY, "1.0", "");
		this.registerService(RestIdentityPath.PROFILES.concat(RestIdentityPath.CREATE),                      IdentityRegistryPath.CREATE_PROFILE,               "1.0", "");
		this.registerService(RestIdentityPath.PROFILES.concat(RestIdentityPath.UPDATE),                      IdentityRegistryPath.UPDATE_PROFILE,               "1.0", "");
		this.registerService(RestIdentityPath.PROFILES.concat(RestIdentityPath.DELETE),                      IdentityRegistryPath.DELETE_PROFILE,               "1.0", "");
		this.registerService(RestIdentityPath.PROFILES.concat(RestIdentityPath.FIND_PROFILE_BY_HOME_ID),     IdentityRegistryPath.FIND_PROFILE_BY_HOME_ID,      "1.0", "");
		this.registerService(RestIdentityPath.UPDATE_PASSWORD,                                               IdentityRegistryPath.UPDATE_USER_PASSWORD,         "1.0", "");
		this.registerService(RestIdentityPath.USERS.concat(RestIdentityPath.FIND_USER_BY_ID),                IdentityRegistryPath.FIND_USER_BY_ID,              "1.0", "");
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
		String localIP = null;
		try {
			InetAddress localAddress = InetAddress.getLocalHost();
			localIP = localAddress.getHostAddress();
		} catch (UnknownHostException e) {
			localIP = ConfigurationBean.getPropertyValue(ConfigurationBean.SERVER_HOST);
			LOGGER.error("Unknown host exception, using defaul Hiperium server address: " + localIP);
		}
		return String.format("{scheme}://%s:{port}%s%s%s", 
				localIP,
				RestIdentityPath.IDENTITY_CONTEXT_ROOT, 
				RestIdentityPath.IDENTITY_PATH, 
				servicePath);
	}
}
