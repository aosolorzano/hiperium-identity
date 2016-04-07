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
package com.hiperium.security.restful;

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
import com.hiperium.common.services.restful.RegistrySecutityPath;
import com.hiperium.common.services.restful.registry.ServiceRegister;
import com.hiperium.security.common.ConfigurationBean;

/**
 * This class represents the JAX-RS Activation Implementation for JAX-RS
 * applications.
 * 
 * @author Andres Solorzano
 *
 */
@ApplicationPath(RestSecurityPath.SECURITY_PATH)
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
		this.registerService(RestSecurityPath.AUTHENTICATION.concat(RestSecurityPath.USER_AUTH),             RegistrySecutityPath.USER_AUTHENTICATION,          "1.0", "");
		this.registerService(RestSecurityPath.AUTHENTICATION.concat(RestSecurityPath.HOME_AUTH),             RegistrySecutityPath.HOME_AUTHENTICATION,          "1.0", "");
		this.registerService(RestSecurityPath.HOME_SELECTION,                                                RegistrySecutityPath.HOME_SELECTION,               "1.0", "");
		this.registerService(RestSecurityPath.APPLICATION_USER,                                              RegistrySecutityPath.FIND_APP_USER_BY_ROLE_NAME,   "1.0", "");
		this.registerService(RestSecurityPath.HOMES.concat(RestSecurityPath.FIND_HOME_USER_BY_ID),           RegistrySecutityPath.FIND_HOME_USER_BY_ID,         "1.0", "");
		this.registerService(RestSecurityPath.PROFILE_FUNCTIONALITY.concat(RestSecurityPath.UPDATE),         RegistrySecutityPath.UPDATE_PROFILE_FUNCTIONALITY, "1.0", "");
		this.registerService(RestSecurityPath.PROFILES.concat(RestSecurityPath.CREATE),                      RegistrySecutityPath.CREATE_PROFILE,               "1.0", "");
		this.registerService(RestSecurityPath.PROFILES.concat(RestSecurityPath.UPDATE),                      RegistrySecutityPath.UPDATE_PROFILE,               "1.0", "");
		this.registerService(RestSecurityPath.PROFILES.concat(RestSecurityPath.DELETE),                      RegistrySecutityPath.DELETE_PROFILE,               "1.0", "");
		this.registerService(RestSecurityPath.PROFILES.concat(RestSecurityPath.FIND_PROFILE_BY_HOME_ID),     RegistrySecutityPath.FIND_PROFILE_BY_HOME_ID,      "1.0", "");
		this.registerService(RestSecurityPath.SESSION_MANAGER.concat(RestSecurityPath.GET_SESSION_AUDIT_VO), RegistrySecutityPath.GET_SESSION_AUDIT_VO,         "1.0", "");
		this.registerService(RestSecurityPath.SESSION_MANAGER.concat(RestSecurityPath.IS_USER_LOGGED_IN),    RegistrySecutityPath.IS_USER_LOGGED_IN,            "1.0", "");
		this.registerService(RestSecurityPath.SESSION_MANAGER.concat(RestSecurityPath.LOGOUT),               RegistrySecutityPath.FIND_PROFILE_BY_HOME_ID,      "1.0", "");
		this.registerService(RestSecurityPath.UPDATE_PASSWORD,                                               RegistrySecutityPath.UPDATE_USER_PASSWORD,         "1.0", "");
		this.registerService(RestSecurityPath.USERS.concat(RestSecurityPath.FIND_USER_BY_ID),                RegistrySecutityPath.FIND_USER_BY_ID,              "1.0", "");
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
			server = new ServiceRegister(this.client, this.servicePort, serviceURI, serviceName, RegistrySecutityPath.BASE_PATH, serviceDetails, serviceVersion);
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
				RestSecurityPath.SECURITY_CONTEXT_ROOT, 
				RestSecurityPath.SECURITY_PATH, 
				servicePath);
	}
}
