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
package com.hiperium.security.restful.auth;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.exception.PropertyValidationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.security.bo.authentication.AuthenticationBO;
import com.hiperium.security.common.dto.HomeSelectionDTO;
import com.hiperium.security.restful.RestSecurityPath;
import com.hiperium.security.restful.generic.GenericResource;

/**
 * This class represents the user authenticator service administration
 * validation.
 * 
 * @author Andres Solorzano
 * 
 */
@Path(RestSecurityPath.HOME_SELECTION)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class HomeSelectionResource extends GenericResource<HomeSelectionDTO> {

	/** The property log. */
    @Inject
    protected HiperiumLogger log;
    
	/** The authenticationBO property. */
	@EJB
	private AuthenticationBO authenticationBO;

	/**
	 * 
	 * @param homeSelectionDTO
	 * @return
	 * @throws InformationException
	 * @throws PropertyValidationException
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response select(@NotNull HomeSelectionDTO homeSelectionDTO) 
			throws InformationException, PropertyValidationException {
		this.log.debug("select - BEGIN");
		
		// Validate object parameter
		super.validateObjectProperties(homeSelectionDTO);
		
		// Register the home and profile selection in the session.
		this.authenticationBO.homeSelection(homeSelectionDTO.getHomeId(), 
						homeSelectionDTO.getProfileId(), super.getTokenId());
		
		// Assigns the default profile and generates the menu 
		this.log.debug("select - END");
		return super.ok();
	}

}
