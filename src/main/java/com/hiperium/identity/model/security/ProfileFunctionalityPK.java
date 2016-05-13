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
package com.hiperium.identity.model.security;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the primary key of the user access profiles.
 * 
 * @author Andres Solorzano
 */
@XmlRootElement
public class ProfileFunctionalityPK implements Serializable {

	/** The property serialVersionUID. */
	private static final long serialVersionUID = -7916774371835615045L;

	/** The property functionalityId. */
	@Min(value = 1L)
	private Integer functionalityId;

	/** The property profileId. */
	@Min(value = 1L)
	private Long profileId;

	/**
	 * Default Constructor.
	 */
	public ProfileFunctionalityPK() {
		// Nothing to do.
	}

	/**
	 * Class constructor.
	 * 
	 * @param functionalityId
	 *            the functionality identifier.
	 * @param profileId
	 *            the profile identifier.
	 */
	public ProfileFunctionalityPK(Integer functionalityId, Long profileId) {
		this.functionalityId = functionalityId;
		this.profileId = profileId;
	}

	/**
	 * Get the functionalityId property.
	 * 
	 * @return the functionalityId property.
	 */
	public Integer getFunctionalityId() {
		return functionalityId;
	}

	/**
	 * Set the functionalityId property.
	 * 
	 * @param functionalityId
	 *            the functionalityId to set.
	 */
	public void setFunctionalityId(Integer functionalityId) {
		this.functionalityId = functionalityId;
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((functionalityId == null) ? 0 : functionalityId.hashCode());
		result = prime * result
				+ ((profileId == null) ? 0 : profileId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfileFunctionalityPK other = (ProfileFunctionalityPK) obj;
		if (functionalityId == null) {
			if (other.functionalityId != null)
				return false;
		} else if (!functionalityId.equals(other.functionalityId))
			return false;
		if (profileId == null) {
			if (other.profileId != null)
				return false;
		} else if (!profileId.equals(other.profileId))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProfileFunctionalityPK [functionalityId=" + functionalityId
				+ ", profileId=" + profileId + "]";
	}

}
