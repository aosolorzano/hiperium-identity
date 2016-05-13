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
package com.hiperium.identity.restful.module;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hiperium.common.services.dto.ApplicationUserDTO;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.module.ApplicationUserBO;
import com.hiperium.identity.model.security.Home;
import com.hiperium.identity.restful.RestSecurityPath;
import com.hiperium.identity.restful.generic.GenericResource;

/**
 * This class represents the user home service.
 * 
 * @author Andres Solorzano
 * 
 */
@Path(RestSecurityPath.APPLICATION_USER)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ApplicationUserResource extends GenericResource<Home> {

	/** The property log. */
    @Inject
    private HiperiumLogger log;
    
    /** The property appUserBO. */
	@EJB
	private ApplicationUserBO appUserBO;
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@GET
	public ApplicationUserDTO findByRole(@QueryParam("role") String role) {
		this.log.debug("findByRole - START");
		List<String> result = this.appUserBO.findByRole(role);
		if(result == null) {
			return null;
		}
		ApplicationUserDTO dto = new ApplicationUserDTO();
		dto.setParam1(result.get(0));
		dto.setParam2(result.get(1));
		this.log.debug("findByRole - END");
		return dto;
	}
	
}
