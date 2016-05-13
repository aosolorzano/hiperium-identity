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
package com.hiperium.identity.restful;

/**
 * 
 * @author Andres Solorzano
 */
public final class RestSecurityPath {

	// ***************************************************************** //
	// ***************************************************************** //
	// ************************ APPLICATION CONTEXT ******************** //
	// ***************************************************************** //
	// ***************************************************************** //

	/** The property SECURITY_CONTEXT_ROOT with value /hiperium-security. */
	public static final String SECURITY_CONTEXT_ROOT = "/hiperium-security";

	// ***************************************************************** //
	// ***************************************************************** //
	// ************************ APPLICATION PATH *********************** //
	// ***************************************************************** //
	// ***************************************************************** //

	/** The property SECURITY_PATH with value /api/security. */
	public static final String SECURITY_PATH = "/api/security";

	// ***************************************************************** //
	// ***************************************************************** //
	// ************************* AUTHENTICATION ************************ //
	// ***************************************************************** //
	// ***************************************************************** //

	/**
	 * The property AUTHENTICATION with REST path /authentication.
	 */
	public static final String AUTHENTICATION = "/authentication";

	/**
	 * The property HOME_SELECTION with REST path /homeSelection.
	 */
	public static final String HOME_SELECTION = "/homeSelection";

	// ***************************************************************** //
	// ***************************************************************** //
	// ******************************** PATHS ************************** //
	// ***************************************************************** //
	// ***************************************************************** //

	/**
	 * The property UPDATE_PASSWORD with REST path /updatePassword.
	 */
	public static final String UPDATE_PASSWORD = "/updatePassword";

	/**
	 * The property HOME with REST path /home.
	 */
	public static final String HOMES = "/home";

	/**
	 * The property PROFILE_FUNCTIONALITY with REST path /profileFunctionality.
	 */
	public static final String PROFILE_FUNCTIONALITY = "/profileFunctionality";

	/**
	 * The property PROFILES with REST path /profile.
	 */
	public static final String PROFILES = "/profile";

	/**
	 * The property USERS with REST path /user.
	 */
	public static final String USERS = "/user";

	/**
	 * The property APPLICATION_USER with REST path /appUser.
	 */
	public static final String APPLICATION_USER = "/appUser";

	// ***************************************************************** //
	// ***************************************************************** //
	// ************************ FUNCTIONALITIES ************************ //
	// ***************************************************************** //
	// ***************************************************************** //

	/** The CREATE property path. */
	public static final String CREATE = "/create";

	/** The UPDATE property path. */
	public static final String UPDATE = "/update";

	/** The DELETE property path. */
	public static final String DELETE = "/delete";
	
	/** The USER_AUTH property path. */
	public static final String USER_AUTH = "/userAuthentication";

	/** The HOME_AUTH property path. */
	public static final String HOME_AUTH = "/homeAuthentication";

	/** The FIND_HOME_USER_BY_ID property path. */
	public static final String FIND_HOME_USER_BY_ID = "/findByUserId";

	/** The FIND_PROFILE_BY_HOME_ID property path. */
	public static final String FIND_PROFILE_BY_HOME_ID = "/findByHomeId";

	/** The IS_USER_LOGGED_IN property path. */
	public static final String IS_USER_LOGGED_IN = "/isUserLoggedIn";

	/** The GET_SESSION_AUDIT_VO property path. */
	public static final String GET_SESSION_AUDIT_VO = "/getSessionAuditVO";

	/** The END_SESSION property path. */
	public static final String LOGOUT = "/logout";
	
	/** The FIND_USER_BY_ID property path. */
	public static final String FIND_USER_BY_ID = "/findByUserId";

}
