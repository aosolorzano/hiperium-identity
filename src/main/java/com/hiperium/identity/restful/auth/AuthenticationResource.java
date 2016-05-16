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
package com.hiperium.identity.restful.auth;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;

import com.hiperium.common.services.audit.SessionRegister;
import com.hiperium.common.services.audit.UserStatistic;
import com.hiperium.common.services.dto.HomeResponseDTO;
import com.hiperium.common.services.dto.HomeCredentialDTO;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.exception.PropertyValidationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.common.services.vo.UserSessionVO;
import com.hiperium.identity.audit.bo.AuditManagerBO;
import com.hiperium.identity.bo.authentication.AuthenticationBO;
import com.hiperium.identity.bo.module.ApplicationUserBO;
import com.hiperium.identity.bo.module.UserBO;
import com.hiperium.identity.common.ConfigurationBean;
import com.hiperium.identity.common.dto.HomeSelectionDTO;
import com.hiperium.identity.common.dto.UserAuthResponseDTO;
import com.hiperium.identity.common.dto.UserCredentialDTO;
import com.hiperium.identity.model.security.User;
import com.hiperium.identity.restful.RestIdentityPath;
import com.hiperium.identity.restful.generic.GenericResource;

/**
 * This class represents the user authenticator service administration
 * validation.
 *
 * @author Andres Solorzano
 */
@Path(RestIdentityPath.AUTHENTICATION)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthenticationResource extends GenericResource<UserAuthResponseDTO> {
	
	/** The property log. */
    @Inject
    private HiperiumLogger log;
	
    /** The authenticationBO property. */
	@EJB
	private AuthenticationBO authenticationBO;
	
	/** The userBO property. */
	@EJB
	private UserBO userBO;
	
	/** The applicationUserBO property. */
	@EJB
	private ApplicationUserBO applicationUserBO;
	
	/** The property auditManagerBO. */
	@EJB
	private AuditManagerBO auditManagerBO;
	
	/** The property servletRequest. */
	@Context
	private HttpServletRequest servletRequest;
	
    /**
     * Used by JavaScript in the presentation layer to authenticate the user.
     *
     * @param credentialsDTO
     * @return
     * @throws InformationException
     * @throws PropertyValidationException
     */
    @POST
    @Path(RestIdentityPath.USER_AUTH)
    public UserAuthResponseDTO userAuthentication(@NotNull UserCredentialDTO credentialsDTO) 
    		throws InformationException, PropertyValidationException {
        this.log.debug("userAuthentication - BEGIN");
       
        // Validate object parameter
        Set<ConstraintViolation<UserCredentialDTO>> violations = super.getValidator().validate(credentialsDTO);
		if (!violations.isEmpty()) {
			throw new PropertyValidationException(new HashSet<ConstraintViolation<UserCredentialDTO>>(violations));
		}
        // Creates a session register with a new HTTP session
        String userAgent = super.getServletRequest().getHeader("User-Agent");
        String remoteIpAddress = super.getServletRequest().getRemoteAddr();
        SessionRegister sessionRegister = this.authenticationBO.userAuthentication(credentialsDTO.getEmail(), credentialsDTO.getPassword(), userAgent, remoteIpAddress);
        
        // Add session register to the session map
        HttpSession session = this.servletRequest.getSession();
        session.setAttribute(sessionRegister.getTokenId(), sessionRegister);
     	this.getHttpSessionMap().put(session.getId(), session);
     		
        // Construct the response
        User user = this.userBO.findById(sessionRegister.getUserId(), false, sessionRegister.getTokenId());
     	UserAuthResponseDTO dto = new UserAuthResponseDTO();
     	dto.setId(user.getId());
     	dto.setUsername(user.getFirstname());
     	dto.setLanguage(user.getLanguageId());
     	dto.setAuthorizationToken(sessionRegister.getTokenId());
     	
     	// Verify if user needs to change the password
 		UserStatistic userStatistic;
 		try {
 			userStatistic = this.auditManagerBO.findUserStatisticById(sessionRegister.getUserId(), sessionRegister.getTokenId());
 			Calendar lastChangedPassword = Calendar.getInstance();
 			lastChangedPassword.setTime(userStatistic.getLastPasswordChange());
 			// TODO: CASE BASED ON BUSINESS RULE, FOR TESTING WE ARE USING 3 MONTHS
 			lastChangedPassword.add(Calendar.MONTH, 3);
 			if (lastChangedPassword.before(Calendar.getInstance())) {
 	        	dto.setChangePassword(Boolean.TRUE);
 	        } else {
 	        	dto.setChangePassword(Boolean.FALSE);
 	        }
 		} catch (Exception e) {
 			this.log.error(e.getMessage());
 		}
     		
        this.log.debug("userAuthentication - END");
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
    @Path(RestIdentityPath.HOME_AUTH)
    public HomeResponseDTO homeAuthentication(@NotNull HomeCredentialDTO credentialsDTO) 
    		throws InformationException, PropertyValidationException {
        this.log.debug("homeAuthentication - BEGIN");
       
        // Validate object parameter
        Set<ConstraintViolation<HomeCredentialDTO>> violations = super.getValidator().validate(credentialsDTO);
		if (!violations.isEmpty()) {
			throw new PropertyValidationException(new HashSet<ConstraintViolation<HomeCredentialDTO>>(violations));
		}
		
        // Creates a session register with a new HTTP session
        String userAgent = super.getServletRequest().getHeader("User-Agent");
        String remoteIpAddress = super.getServletRequest().getRemoteAddr();
        SessionRegister sessionRegister = this.authenticationBO.homeAuthentication(credentialsDTO.getId(), credentialsDTO.getSerial(), userAgent, remoteIpAddress);
        
        // Add session register to the session map
        HttpSession session = this.servletRequest.getSession();
        session.setAttribute(sessionRegister.getTokenId(), sessionRegister);
     	this.getHttpSessionMap().put(session.getId(), session);
 		
 		// Find the JBoss Application User credentials to be send to the Raspberry for Queue connections.
 		List<String> register = this.applicationUserBO.findByRole("hiperium");
 		HomeResponseDTO dto = new HomeResponseDTO();
 		dto.setParam1(register.get(0));
 		dto.setParam2(register.get(1));
 		dto.setParam3(sessionRegister.getTokenId());
     		
        this.log.debug("homeAuthentication - END");
        return dto;
    }
	
    /**
	 * 
	 * @param homeSelectionDTO
	 * @return
	 * @throws InformationException
	 * @throws PropertyValidationException
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response select(@NotNull HomeSelectionDTO homeSelectionDTO) throws InformationException, PropertyValidationException {
		this.log.debug("select - BEGIN");
		
		// Validate object parameter
        Set<ConstraintViolation<HomeSelectionDTO>> violations = super.getValidator().validate(homeSelectionDTO);
		if (!violations.isEmpty()) {
			throw new PropertyValidationException(new HashSet<ConstraintViolation<HomeSelectionDTO>>(violations));
		}
		
		// Get session register object from session map
		SessionRegister sessionRegister = this.getSessionRegister(super.getTokenId());
		sessionRegister.setHomeId(homeSelectionDTO.getHomeId());
		sessionRegister.setProfileId(homeSelectionDTO.getProfileId());
		
		// Register the home and profile selection in the session.
		sessionRegister = this.authenticationBO.homeSelection(sessionRegister, super.getTokenId());
		
		// Updates session register in the session map
		HttpSession session = this.servletRequest.getSession();
        session.setAttribute(sessionRegister.getTokenId(), sessionRegister);
     	this.getHttpSessionMap().put(session.getId(), session);
     	
		// TODO: Add users of the same home to the session map. This because the device 
		// platform needs to notify for an home event to connected users of the 
		// same home.
		/*if(this.homeUsersTokenMap.containsKey(sessionRegister.getHomeId())) {
			this.homeUsersTokenMap.get(sessionRegister.getHomeId()).add(sessionRegister.getTokenId());
		} else {
			Set<String> sessionIds = new HashSet<String>();
			sessionIds.add(sessionRegister.getTokenId());
			this.homeUsersTokenMap.put(sessionRegister.getHomeId(), sessionIds);
		}*/
				
		// Assigns the default profile and generates the menu 
		this.log.debug("select - END");
		return super.ok();
	}
	
	/**
	 * 
	 * @param userToken
	 * @return
	 * @throws WebApplicationException
	 */
	@GET
	@Path(RestIdentityPath.IS_USER_LOGGED_IN)
	public Response isUserLoggedIn() throws WebApplicationException {
		this.log.debug("isUserLoggedIn - BEGIN");
		HttpSession session = this.servletRequest.getSession(false);
		if(session == null || session.getAttribute(super.getTokenId()) == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		this.log.debug("isUserLoggedIn - END");
		return Response.ok("OK").build();
	}
	
	/**
	 * 
	 * @param userToken
	 * @return
	 * @throws WebApplicationException
	 */
	@GET
	@Path(RestIdentityPath.GET_SESSION_AUDIT_VO)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserSessionVO getSessionAuditVO() throws WebApplicationException {
		this.log.debug("getSessionAuditVO - BEGIN");
		UserSessionVO sessionAuditVO = null;
		SessionRegister sessionRegister = this.getSessionRegister(super.getTokenId());
		if(sessionRegister == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} else {
			sessionAuditVO = new UserSessionVO(
					sessionRegister.getUserId(),
					sessionRegister.getHomeId(),
					sessionRegister.getProfileId(),
					sessionRegister.getIpConnection(),
					sessionRegister.getAccessChannel(),
					super.getTokenId());
		}
		this.log.debug("getSessionAuditVO - END");
		return sessionAuditVO;
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path(RestIdentityPath.LOGOUT)
	public Response logout() {
		this.log.debug("logout - BEGIN");
		if(StringUtils.isNotBlank(super.getTokenId())) {
			SessionRegister sessionRegister = this.getSessionRegister(super.getTokenId());
			if(sessionRegister != null) {
				try {
					this.auditManagerBO.updateLogoutDate(sessionRegister, sessionRegister.getTokenId());
					HttpSession session = this.servletRequest.getSession();
					session.invalidate(); // This calls SessionListener object to destroy session in the map.
					// TODO: Remove the session from home session map too.
					/*if(this.homeUsersTokenMap.containsKey(sessionRegister.getHomeId())) {
						this.homeUsersTokenMap.get(sessionRegister.getHomeId()).remove(tokenId);
						if(this.homeUsersTokenMap.get(sessionRegister.getHomeId()).isEmpty()) {
							this.homeUsersTokenMap.remove(sessionRegister.getHomeId());
						}
					}*/
				} catch (Exception e) {
					this.log.error(e.getMessage());
				}
			}
		}
		this.log.debug("logout - END");
		return Response.ok().build();
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, HttpSession> getHttpSessionMap(){
		ServletContext context = this.servletRequest.getServletContext();
		HashMap<String, HttpSession> sessions = (HashMap<String, HttpSession>) context.getAttribute(ConfigurationBean.SESSION_MAP_NAME);
		return sessions;
	}
	
	/**
	 * 
	 * @param tokenId
	 * @return
	 */
	private SessionRegister getSessionRegister(String tokenId) {
		HttpSession session = this.servletRequest.getSession();
		SessionRegister sessionRegister = (SessionRegister) session.getAttribute(tokenId);
		return sessionRegister;
	}
}
