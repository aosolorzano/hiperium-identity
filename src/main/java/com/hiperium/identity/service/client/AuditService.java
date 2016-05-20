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
package com.hiperium.identity.service.client;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.client.http.HttpClient;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.model.UserStatistic;
import com.hiperium.identity.service.converter.UserStatisticConverter;

/**
 * This service class send calls to the REST Service to operate with different
 * actions originated from the system.
 * 
 * @author Andres Solorzano
 * 
 */
public final class AuditService extends HttpClient {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(AuditService.class);
    
	/** The property service. */
	private static AuditService service = null;
	
	/** The property userStatisticConverter. */
	private UserStatisticConverter userStatisticConverter;
	
	/**
	 * Class constructor
	 */
	private AuditService() {
		super();
		this.userStatisticConverter = new UserStatisticConverter();
	}

	/**
	 * Return the singleton instance.
	 * 
	 * @return
	 */
	public static AuditService getInstance() {
		if(service == null) {
			service = new AuditService();
		}
		return service;
	}
	
	/**
	 * 
	 * @param serviceURI
	 * @param id
	 * @param token
	 * @return
	 */
	public UserStatistic findByUserStatisticId(@NotNull String serviceURI, @NotNull @Min(value = 1L) Long id, @NotNull String token) {
		LOGGER.debug("findByUserStatisticId - START");
		UserStatistic register = null;
		try {
			String response = super.getFromService(serviceURI.concat("?id=").concat(id.toString()), "application/json", token);
			if(StringUtils.isNotBlank(response)) {
				register = this.userStatisticConverter.fromJsonToUserStatistic(response);
			}
		} catch (InformationException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return register;
	}
	
	/**
	 * 
	 * @param serviceURI
	 * @param id
	 * @param token
	 */
	public void updateLastPasswordChange(@NotNull String serviceURI, @NotNull @Min(value = 1L) Long id, @NotNull String token) {
		LOGGER.debug("updateLastPasswordChange - START");
		try {
			super.putToService(serviceURI, id.toString(), "text/plain", "text/plain", token);
		} catch (InformationException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
