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
package com.hiperium.security.audit.bo.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.audit.UserStatistic;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.restful.RegistryAuditPath;
import com.hiperium.common.services.restful.audit.AuditService;
import com.hiperium.common.services.restful.dto.ServiceDetailsDTO;
import com.hiperium.security.audit.bo.AuditManagerBO;
import com.hiperium.security.bo.generic.GenericBO;

/**
 * This service is the implementation of the interface DeviceLocal and manages
 * all actions needed for device management.
 * 
 * @author Andres Solorzano
 * 
 */
@Startup
@Singleton
@Lock(LockType.READ)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AuditManagerBOImpl extends GenericBO implements AuditManagerBO {

	/** The property log. */
	@Inject
	private HiperiumLogger log;

	/** The property auditService. */
	private AuditService auditService;
	
	/** The property curatorClient. */
	@Inject
	private CuratorFramework curatorClient;
	/** The property serviceDiscovery. */
	private ServiceDiscovery<ServiceDetailsDTO> serviceDiscovery;
	/** The property serializer. */
	private JsonInstanceSerializer<ServiceDetailsDTO> serializer;

	/**
	 * Initializes the component.
	 */
	@PostConstruct
	public void init() {
		this.auditService = AuditService.getInstance();
		this.serializer = new JsonInstanceSerializer<ServiceDetailsDTO>(ServiceDetailsDTO.class); // Payload Serializer
		this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetailsDTO.class)
				.client(this.curatorClient)
				.basePath(RegistryAuditPath.BASE_PATH)
				.serializer(this.serializer)
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateHomeSelection(@NotNull SessionRegister sessionRegister, String token) throws Exception {
		this.log.debug("updateHomeSelection - START");
		this.auditService.updateHomeSelection(this.getServiceURI(RegistryAuditPath.UPDATE_SESSION_REGISTER_HOME_SELECTION), sessionRegister, token);
		this.log.debug("updateHomeSelection - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void updateLogoutDate(@NotNull SessionRegister sessionRegister, String token) throws Exception {
		this.log.debug("updateLogoutDate - START");
		this.auditService.updateLogoutDate(this.getServiceURI(RegistryAuditPath.UPDATE_SESSION_REGISTER_LOGOUT_DATE), sessionRegister.getId(), token);
		this.log.debug("updateLogoutDate - END");
	}

	/**
	 * {@inheritDoc}
	 */
	public UserStatistic findUserStatisticById(@NotNull @Min(value = 1L) Long userId, @NotNull String token) throws Exception {
		this.log.debug("findById - START");
		return this.auditService.findByUserStatisticId(this.getServiceURI(RegistryAuditPath.FIND_USER_STATISTIC_BY_USER_ID), userId, token);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateLastPasswordChange(@NotNull @Min(value = 1L) Long userId, @NotNull String token) throws Exception {
		this.log.debug("updateLastPasswordChange - START");
		this.auditService.updateLastPasswordChange(this.getServiceURI(RegistryAuditPath.UPDATE_LAST_USER_PASS_BY_USER_ID), userId, token); 
		this.log.debug("updateLastPasswordChange - END");
	}
	
	/**
	 * 
	 * @param serviceRegistryPath
	 * @return
	 * @throws Exception
	 */
	private String getServiceURI(String serviceRegistryPath) throws Exception {
		final Collection<ServiceInstance<ServiceDetailsDTO>> services = this.serviceDiscovery.queryForInstances(serviceRegistryPath);
		if(services == null || services.isEmpty()) {
        	throw new Exception("No results found for querying services called: " + serviceRegistryPath);
        } else {
        	for(final ServiceInstance<ServiceDetailsDTO> service : services) {
        		return service.buildUriSpec();
            }
        }
		return null;
	}
}
