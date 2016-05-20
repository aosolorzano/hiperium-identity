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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hiperium.commons.client.dto.SelectionDTO;
import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.restful.path.IdentityRestfulPath;
import com.hiperium.identity.bo.module.HomeBO;
import com.hiperium.identity.model.security.Home;
import com.hiperium.identity.restful.generic.GenericResource;

/**
 * This class represents the user home service.
 * 
 * @author Andres Solorzano
 * 
 */
@Path(IdentityRestfulPath.HOMES)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class HomeResource extends GenericResource<Home> {

	/** The property log. */
    @Inject
    private HiperiumLogger log;
    
    /** The property homeBO. */
    @EJB
	private HomeBO homeBO;
	
	/**
	 *
	 * @return
	 * @throws InformationException
	 */
	@GET
	@Path(IdentityRestfulPath.FIND_HOME_USER_BY_ID)
	public List<SelectionDTO> findByUserId(@QueryParam("userId") Long userId) throws InformationException {
		this.log.debug("findByUserId - START");
		return this.homeBO.findByUserId(userId, super.getTokenId());
	}
	
}
