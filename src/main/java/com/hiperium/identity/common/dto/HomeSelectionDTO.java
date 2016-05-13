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
package com.hiperium.identity.common.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a POJO class that will be used to authenticate the user profile
 * against the system in the authentication process.
 * 
 * @author Andres Solorzano
 * 
 */
@XmlRootElement
public class HomeSelectionDTO implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = 3799843913165268708L;

	/** The property homeId. */
	@NotNull
	@Min(value = 1L)
	private Long homeId;

	/** The property profileId. */
	@NotNull
	@Min(value = 1L)
	private Long profileId;

	/**
	 * Default constructor.
	 */
	public HomeSelectionDTO() {
		// Nothing to do.
	}
	
	/**
	 * 
	 * Class constructor.
	 * 
	 * @param homeId
	 * @param profileId
	 */
	public HomeSelectionDTO(Long homeId, Long profileId) {
		this.homeId = homeId;
		this.profileId = profileId;
	}

	/**
	 * Get the homeId property.
	 * 
	 * @return the homeId property.
	 */
	public Long getHomeId() {
		return homeId;
	}

	/**
	 * Set the homeId property.
	 * 
	 * @param homeId
	 *            the homeId to set.
	 */
	public void setHomeId(Long homeId) {
		this.homeId = homeId;
	}

	/**
	 * Get the profileId property.
	 * 
	 * @return the profileId property.
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * Set the profileId property.
	 * 
	 * @param profileId
	 *            the profileId to set.
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HomeSelectionDTO [homeId=" + homeId + ", profileId="
				+ profileId + "]";
	}

}
