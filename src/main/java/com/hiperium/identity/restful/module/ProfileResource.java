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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.dto.SelectionDTO;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.authentication.AuthenticationBO;
import com.hiperium.identity.bo.module.ProfileBO;
import com.hiperium.identity.common.dto.ProfileParamsDTO;
import com.hiperium.identity.model.security.Profile;
import com.hiperium.identity.restful.RestIdentityPath;
import com.hiperium.identity.restful.generic.GenericResource;


/**
 * This class implements all defined methods in the ProfileService
 * interface.
 * 
 * @author Andres Solorzano
 * 
 */
@Path(RestIdentityPath.PROFILES)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProfileResource extends GenericResource<Profile> {

	/** The property log. */
    @Inject
    private HiperiumLogger log;
	
	/** The property profileBO. */
	@EJB
	private ProfileBO profileBO;
	
	/** The property authenticationBO. */
	@EJB
	private AuthenticationBO authenticationBO;
	
	/**
	 * 
	 * @param paramsDTO
	 * @throws WebApplicationException
	 * @throws InformationException
	 */
	@POST
	@Path(RestIdentityPath.CREATE)
	public void create(ProfileParamsDTO paramsDTO)
			throws WebApplicationException, InformationException {
		this.log.debug("create - START");
	}

	/**
	 * 
	 * @param paramsDTO
	 * @return
	 * @throws WebApplicationException
	 * @throws InformationException
	 */
	@PUT
	@Path(RestIdentityPath.UPDATE)
	public Profile update(ProfileParamsDTO paramsDTO)
			throws WebApplicationException, InformationException {
		this.log.debug("homeSelection - START");
		return null;
	}

	/**
	 * 
	 * @param sessionId
	 * @param profileId
	 * @throws WebApplicationException
	 * @throws InformationException
	 */
	@DELETE
	@Path(RestIdentityPath.DELETE)
	public void delete(@QueryParam("profileId") Long profileId) 
			throws WebApplicationException, InformationException {
		this.log.debug("delete - " + profileId);
	}
	
	/**
	 *
	 * @param profileId
	 * @return
	 * @throws InformationException
	 */
	@GET
	@Path(RestIdentityPath.FIND_PROFILE_BY_HOME_ID)
	public List<SelectionDTO> findByHomeId(@QueryParam("homeId") Long homeId)
			throws InformationException {
		this.log.debug("findByHomeId - START");
		SessionRegister sessionRegister = this.authenticationBO.findUserSessionRegister(super.getTokenId());
		return this.profileBO.findByHomeId(homeId, sessionRegister.getUserId(), sessionRegister.getTokenId());
	}

}
