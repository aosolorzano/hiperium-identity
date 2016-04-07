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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;

import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.vo.SessionAuditVO;
import com.hiperium.security.common.SessionManagerBean;
import com.hiperium.security.restful.RestSecurityPath;
import com.hiperium.security.restful.generic.GenericResource;

/**
 * This class represents the user home service.
 * 
 * @author Andres Solorzano
 * 
 */
@Path(RestSecurityPath.SESSION_MANAGER)
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class SessionManagerResource extends GenericResource<SessionManagerResource>{

	/** The property log. */
    @Inject
    private HiperiumLogger log;
    
    /** The property sessionManager. */
    @EJB
    private SessionManagerBean sessionManager;
    
    /** The property servletRequest. */
	@Context
	private HttpServletRequest servletRequest;
	
	/**
	 * 
	 * @param userToken
	 * @return
	 * @throws WebApplicationException
	 */
	@GET
	@Path(RestSecurityPath.GET_SESSION_AUDIT_VO)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public SessionAuditVO getSessionAuditVO() throws WebApplicationException {
		this.log.debug("getSessionAuditVO - BEGIN");
		if(!this.sessionManager.isUserLoggedIn(super.getTokenId())) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		} 
		SessionAuditVO auditVO = this.sessionManager.findSessionAuditVO(super.getTokenId());
		if(auditVO == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		this.log.debug("getSessionAuditVO - END");
		return auditVO;
	}
	
	/**
	 * 
	 * @param userToken
	 * @return
	 * @throws WebApplicationException
	 */
	@GET
	@Path(RestSecurityPath.IS_USER_LOGGED_IN)
	public Response isUserLoggedIn() throws WebApplicationException {
		this.log.debug("isUserLoggedIn - BEGIN");
		if(!this.sessionManager.isUserLoggedIn(super.getTokenId())) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		this.log.debug("isUserLoggedIn - END");
		return Response.ok("OK").build();
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path(RestSecurityPath.LOGOUT)
	public Response logout() {
		this.log.debug("logout - BEGIN");
		String token = this.servletRequest.getHeader("Authorization");
		if(StringUtils.isNotBlank(token)) {
			this.sessionManager.delete(token);
		}
		this.log.debug("logout - END");
		return Response.ok().build();
	}
	
}
