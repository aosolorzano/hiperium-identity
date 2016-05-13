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

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the register user' home.
 * 
 * @author Andres Solorzano
 */
@XmlRootElement
public class UserHome implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = -5359748529025856037L;

	/** The property pk. */
	@NotNull
	private UserHomePK pk;

	/** The property active. */
	@NotNull
	private Boolean active;

	/** The property home. */
	@NotNull
	private Home home;

	/**
	 * Default constructor.
	 */
	public UserHome() {
		this.pk = new UserHomePK();
	}

	/**
	 * 
	 * @param userId
	 * @param homeId
	 */
	public UserHome(Long userId, Long homeId) {
		this.pk = new UserHomePK(userId, homeId);
	}

	/**
	 * 
	 * @return
	 */
	public UserHomePK getPk() {
		return pk;
	}

	/**
	 * 
	 * @param userHomePK
	 */
	public void setPk(UserHomePK userHomePK) {
		this.pk = userHomePK;
	}

	/**
	 * Gets the active property.
	 * 
	 * @return the active.
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Sets the active property.
	 * 
	 * @param active
	 *            the active to set.
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Get the home property.
	 * 
	 * @return the home property.
	 */
	public Home getHome() {
		return home;
	}

	/**
	 * Set the home property.
	 * 
	 * @param home
	 *            the home to set.
	 */
	public void setHome(Home home) {
		this.home = home;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserHome other = (UserHome) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
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
		return "UserHome [pk=" + pk + ", active=" + active + "]";
	}
}
