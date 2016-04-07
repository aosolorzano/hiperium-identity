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
package com.hiperium.security.restful.module;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.security.bo.module.ProfileFunctionalityBO;
import com.hiperium.security.model.security.ProfileFunctionality;
import com.hiperium.security.restful.RestSecurityPath;
import com.hiperium.security.restful.generic.GenericResource;


/**
 * This class implements all defined methods in the ProfileFunctionalityService
 * interface.
 * 
 * @author Andres Solorzano
 * 
 */
@Path(RestSecurityPath.PROFILE_FUNCTIONALITY)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProfileFunctionalityResource extends GenericResource<ProfileFunctionality> {

	/** The property log. */
    @Inject
    private HiperiumLogger log;

    /** The property profileFunctionalityBO. */
    @EJB
    private ProfileFunctionalityBO profileFunctionalityBO;
    
	/**
	 * 
	 * @param register
	 * @return
	 * @throws InformationException
	 */
	@PUT
	@Path(RestSecurityPath.UPDATE)
	public ProfileFunctionality update(ProfileFunctionality register) 
			throws InformationException {
		this.log.debug("homeSelection - " + register);
		return this.profileFunctionalityBO.update(register, super.getTokenId());
	}
	
}
