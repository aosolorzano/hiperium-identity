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
package com.hiperium.identity.bo.utils;

/**
 * This enumeration specifies the names of Named Queries that exists in the
 * application.
 * 
 * @author Andres Solorzano.
 */
public enum EnumNamedQuerySecurity {

	/** The PROFILE_NEXT_SEQUENTIAL element witch value is Profile.findNextSequential. */
	PROFILE_NEXT_SEQUENTIAL("Profile.findNextSequential"),

	/** The USER_FIND_BY_EMAIL element witch value is User.findByEmail. */
	USER_FIND_BY_EMAIL("User.findByEmail"),
	
	/** The HOME_FIND_BY_USER_ID element witch value is Home.findByUserId. */
	HOME_FIND_SELECTION_BY_USER_ID("Home.findSelectionByUserId"),
	
	/** The HOME_IS_CLOUD_ENABLE element witch value is Home.isCloudEnable. */
	HOME_IS_CLOUD_ENABLE("Home.isCloudEnable"),
	
	/** The PROFILE_FIND_PROFILE_SELECTION element witch value is Profile.findByUserAndHomeId. */
	PROFILE_FIND_SELECTION_BY_HOME_ID("Profile.findByUserAndHomeId"),
	
	/** The PROFILE_FUNC_FIND_BY_PROFILE_AND_URL element witch value is ProfileFunctionalityVO.findByProfileAndURL. */
	PROFILE_FUNC_FIND_BY_PROFILE_AND_URL("ProfileFunctionalityVO.findByProfileAndURL"),
	
	/** The HOME_GATEWAY_VALIDATE_HOME_APPLICATION element witch value is HomeGateway.validateHomeApplication. */
	HOME_GATEWAY_FIND_BY_HOME_SERIAL("HomeGateway.validateHomeApplication"),
	
	/** The HOME_GATEWAY_FIND_TOKEN_BY_HOME_ID element witch value is HomeGateway.findTokenByHomeId. */
	HOME_GATEWAY_FIND_TOKEN_BY_HOME_ID("HomeGateway.findTokenByHomeId"),
	
	/** The USER_HOME_FIND_BY_USERI_ID element witch value is UserHome.findByUserId. */
	USER_HOME_FIND_BY_USERI_ID("UserHome.findByUserId"),
	
	/** The PROFILE_FUNC_FIND_BY_PROFILE_ID element witch value is ProfileFunctionality.findByProfileId. */
	PROFILE_FUNC_FIND_BY_PROFILE_ID("ProfileFunctionality.findByProfileId"),
	
	/** The SYSTEM_CONFIG_FIND_BY_HOME_ID element witch value is SystemConfiguration.findByHomeId. */
	SYSTEM_CONFIG_FIND_BY_HOME_ID("SystemConfiguration.findByHomeId");

	/** Property name. */
	private final String name;

	/**
	 * The Enumeration constructor.
	 * 
	 * @param name
	 *            Value for the element of the Enumeration
	 */
	private EnumNamedQuerySecurity(String name) {
		this.name = name;
	}

	/**
	 * Return the value associate to the element of the enumeration.
	 * 
	 * @return the value associate with the enumeration
	 */
	public String getName() {
		return this.name;
	}
}
