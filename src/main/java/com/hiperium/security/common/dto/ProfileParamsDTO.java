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
package com.hiperium.security.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hiperium.security.model.security.Profile;

/**
 * This class represents profile parameters needed to consume Restful web
 * services.
 * 
 * @author Andres Solorzano
 * 
 */
public class ProfileParamsDTO implements Serializable {

	/***/
	private static final long serialVersionUID = 2452264173675295267L;

	/** */
	private Profile profile;
	/** */
	private List<Integer> functionalities;
	/** */
	private List<Long> zones;

	/**
	 * Default constructor.
	 */
	public ProfileParamsDTO() {
		this.profile = new Profile();
		this.functionalities = new ArrayList<Integer>();
		this.zones = new ArrayList<Long>();
	}

	/**
	 * Get the profile property.
	 * 
	 * @return the profile property.
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * Set the profile property.
	 * 
	 * @param profile
	 *            the profile to set.
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	/**
	 * Get the functionalities property.
	 * @return the functionalities property.
	 */
	public List<Integer> getFunctionalities() {
		return functionalities;
	}

	/**
	 * Set the functionalities property.
	 * @param functionalities the functionalities to set.
	 */
	public void setFunctionalities(List<Integer> functionalities) {
		this.functionalities = functionalities;
	}

	/**
	 * Get the zones property.
	 * @return the zones property.
	 */
	public List<Long> getZones() {
		return zones;
	}

	/**
	 * Set the zones property.
	 * @param zones the zones to set.
	 */
	public void setZones(List<Long> zones) {
		this.zones = zones;
	}

}
