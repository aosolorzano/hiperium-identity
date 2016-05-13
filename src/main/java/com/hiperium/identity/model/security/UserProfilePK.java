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

/**
 * This class represents the primary key of the user's profile.
 * 
 * @author Andres Solorzano
 */
public class UserProfilePK implements Serializable {

	/** The property serialVersionUID. */
	private static final long serialVersionUID = 1415396606465772581L;

	/** The property userId. */
	@Min(value = 1L)
	private Long userId;

	/** The property profileId. */
	@Min(value = 1L)
	private Long profileId;

	/**
	 * Default constructor.
	 */
	public UserProfilePK() {
		// Nothing to do.
	}
	
	/**
	 * Default constructor.
	 */
	public UserProfilePK(Long userId, Long profileId) {
		this.userId = userId;
		this.profileId = profileId;
	}

	/**
	 * Gets the userId property value.
	 * 
	 * @return the userId property value.
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the value to the userId property.
	 * 
	 * @param userId
	 *            the userId to set.
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the profileId property value.
	 * 
	 * @return the profileId property value.
	 */
	public Long getProfileId() {
		return profileId;
	}

	/**
	 * Sets the value to the profileId property.
	 * 
	 * @param profileId
	 *            the profileId to set.
	 */
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((profileId == null) ? 0 : profileId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		UserProfilePK other = (UserProfilePK) obj;
		if (profileId == null) {
			if (other.profileId != null)
				return false;
		} else if (!profileId.equals(other.profileId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		return "UserProfilePK [userId=" + userId + ", profileId=" + profileId
				+ "]";
	}

}
