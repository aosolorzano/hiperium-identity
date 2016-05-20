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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.services.exception.PropertyValidationException;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.model.SessionRegister;
import com.hiperium.commons.services.restful.path.IdentityRestfulPath;
import com.hiperium.identity.bo.authentication.AuthenticationBO;
import com.hiperium.identity.bo.module.UpdatePasswordBO;
import com.hiperium.identity.common.dto.UpdatePasswordDTO;
import com.hiperium.identity.restful.generic.GenericResource;

/**
 * This class expose the method to homeSelection the user password.
 * 
 * @author Andres Solorzano
 * 
 */
@Path(IdentityRestfulPath.UPDATE_PASSWORD)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UpdatePasswordResource extends GenericResource<UpdatePasswordDTO> {

	/** The property log. */
    @Inject
    protected HiperiumLogger log;
    
	/** The updatePasswordBO property. */
	@EJB
	private UpdatePasswordBO updatePasswordBO;
	
	/** The authenticationBO property. */
	@EJB
	private AuthenticationBO authenticationBO;
	
	/** The property servletRequest. */
	@Context
	private HttpServletRequest servletRequest;
	
	/**
	 * 
	 * @param passwordDTO
	 * @return
	 * @throws InformationException
	 * @throws PropertyValidationException
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response updatePassword(UpdatePasswordDTO passwordDTO) 
			throws InformationException, PropertyValidationException {
		this.log.debug("updatePassword - BEGIN");
		
		// Validate object parameter
		super.validateObjectProperties(passwordDTO);
		
		// Validate previous and new password
		if(!passwordDTO.getConfirmPassword().equals(passwordDTO.getNewPassword())) {
			String error = "Code: 1, Message: Passwords do not match";
			InformationException exception = new InformationException(error);
			throw exception;
		}

		// Get the session register from session.
		SessionRegister sessionRegister = this.authenticationBO.findUserSessionRegister(super.getTokenId());
		this.updatePasswordBO.updateUserPassword(sessionRegister.getUserId(), passwordDTO.getNewPassword(), 
				passwordDTO.getPrevPassword(), super.getTokenId());

		// Ends the user session
		this.authenticationBO.endUserSession(super.getTokenId());
		
		this.log.debug("updatePassword - END");
		return super.ok();
	}
	
}
