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

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.restful.identity.IdentityRestfulPath;
import com.hiperium.identity.bo.module.UserBO;
import com.hiperium.identity.model.security.User;
import com.hiperium.identity.restful.generic.GenericResource;


/**
 * This class represents the user service administration regardless with the
 * data base.
 * 
 * @author Andres Solorzano
 * 
 */
@Path(IdentityRestfulPath.USERS)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResource extends GenericResource<User> {

	/** The property log. */
    @Inject
    private HiperiumLogger log;
	
    /** The property userBO. */
    @EJB
    private UserBO userBO;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws WebApplicationException
	 * @throws InformationException
	 */
	@GET
	@Path(IdentityRestfulPath.FIND_USER_BY_ID)
	public User findByUserId(@QueryParam("userId") Long userId)
			throws WebApplicationException, InformationException {
		this.log.debug("findByUsername - " + userId);
		User user = this.userBO.findById(userId, false, super.getTokenId());
		if(user == null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} 
		return user;
	}
	
}
