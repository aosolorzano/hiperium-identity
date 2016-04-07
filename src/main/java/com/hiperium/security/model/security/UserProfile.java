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
package com.hiperium.security.model.security;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * This class represents the register user' profiles assigned to the user.
 * 
 * @author Andres Solorzano
 */
public class UserProfile implements Serializable {

	/** The property serialVersionUID. */
	private static final long serialVersionUID = 1407680457408816513L;

	/** The property pk. */
	@NotNull
	protected UserProfilePK pk;

	/** The property active. */
	@NotNull
	private Boolean active;

	/** The property profile. */
	@NotNull
	private Profile profile;

	/**
	 * Default constructor.
	 */
	public UserProfile() {
		this.pk = new UserProfilePK();
	}

	/**
	 * 
	 * Class constructor.
	 * 
	 * @param userId
	 * @param profileId
	 * @param state
	 */
	public UserProfile(Long userId, Long profileId, Boolean state) {
		this.pk = new UserProfilePK(userId, profileId);
		this.active = state;
	}

	/**
	 * 
	 * Class constructor.
	 * 
	 * @param UserProfilePK
	 */
	public UserProfile(UserProfilePK UserProfilePK) {
		this.pk = UserProfilePK;
	}

	/**
	 * 
	 * Class constructor.
	 * 
	 * @param UserProfilePK
	 * @param state
	 */
	public UserProfile(UserProfilePK UserProfilePK, Boolean state) {
		this.pk = UserProfilePK;
		this.active = state;
	}

	/**
	 * 
	 * @return
	 */
	public UserProfilePK getPk() {
		return pk;
	}

	/**
	 * 
	 * @param UserProfilePK
	 */
	public void setPk(UserProfilePK UserProfilePK) {
		this.pk = UserProfilePK;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * 
	 * @param active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * 
	 * @return
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * 
	 * @param Profile
	 */
	public void setProfile(Profile Profile) {
		this.profile = Profile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (pk != null ? pk.hashCode() : 0);
		return hash;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof UserProfile)) {
			return false;
		}
		UserProfile other = (UserProfile) object;
		if ((this.pk == null && other.pk != null)
				|| (this.pk != null && !this.pk.equals(other.pk))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserProfile [pk=" + pk + ", state=" + active + "]";
	}

}
