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

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hiperium.common.services.dto.HomeAuthResponseDTO;
import com.hiperium.common.services.dto.HomeCredentialDTO;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.exception.PropertyValidationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.security.bo.authentication.AuthenticationBO;
import com.hiperium.security.common.dto.UserAuthResponseDTO;
import com.hiperium.security.common.dto.UserCredentialDTO;
import com.hiperium.security.restful.RestSecurityPath;
import com.hiperium.security.restful.generic.GenericResource;

/**
 * This class represents the user authenticator service administration
 * validation.
 *
 * @author Andres Solorzano
 */
@Path(RestSecurityPath.AUTHENTICATION)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthenticationResource extends GenericResource<UserAuthResponseDTO> {
	
	/** The property log. */
    @Inject
    private HiperiumLogger log;
	
    /** The authenticationBO property. */
	@EJB
	private AuthenticationBO authenticationBO;
	
    /**
     * Used by JavaScript in the presentation layer to authenticate the user.
     *
     * @param credentialsDTO
     * @return
     * @throws InformationException
     * @throws PropertyValidationException
     */
    @POST
    @Path(RestSecurityPath.USER_AUTH)
    public UserAuthResponseDTO userAuthentication(@NotNull UserCredentialDTO credentialsDTO) 
    		throws InformationException, PropertyValidationException {
        this.log.debug("userAuth - BEGIN");
        // Validate object parameter
        Set<ConstraintViolation<UserCredentialDTO>> violations = super.getValidator().validate(credentialsDTO);
		if (!violations.isEmpty()) {
			throw new PropertyValidationException(new HashSet<ConstraintViolation<UserCredentialDTO>>(violations));
		}
        // Creates a session register with a new HTTP session
        String userAgent = super.getServletRequest().getHeader("User-Agent");
        String remoteIpAddress = super.getServletRequest().getRemoteAddr();
        UserAuthResponseDTO dto = this.authenticationBO.userAuthentication(credentialsDTO.getEmail(), credentialsDTO.getPassword(), 
        		userAgent, remoteIpAddress);
        this.log.debug("userAuth - END");
        return dto;
    }
    
    /**
     * Used by JavaScript in the presentation layer to authenticate the user.
     *
     * @param credentialsDTO
     * @return
     * @throws InformationException
     * @throws PropertyValidationException
     */
    @POST
    @Path(RestSecurityPath.HOME_AUTH)
    public HomeAuthResponseDTO homeAuthentication(@NotNull HomeCredentialDTO credentialsDTO) 
    		throws InformationException, PropertyValidationException {
        this.log.debug("homeAuth - BEGIN");
        // Validate object parameter
        Set<ConstraintViolation<HomeCredentialDTO>> violations = super.getValidator().validate(credentialsDTO);
		if (!violations.isEmpty()) {
			throw new PropertyValidationException(new HashSet<ConstraintViolation<HomeCredentialDTO>>(violations));
		}
        // Creates a session register with a new HTTP session
        String userAgent = super.getServletRequest().getHeader("User-Agent");
        String remoteIpAddress = super.getServletRequest().getRemoteAddr();
        HomeAuthResponseDTO dto = this.authenticationBO.homeAuthentication(credentialsDTO.getId(), credentialsDTO.getSerial(), 
        		userAgent, remoteIpAddress);
        this.log.debug("homeAuth - END");
        return dto;
    }
    
}
