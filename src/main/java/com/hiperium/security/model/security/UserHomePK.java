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

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the primary key of the user's home.
 * 
 * @author Andres Solorzano
 */
@XmlRootElement
public class UserHomePK implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = -1424239882752153128L;

	/** The property userId. */
	@Min(value = 1L)
	private Long userId;

	/** The property homeId. */
	@Min(value = 1L)
	private Long homeId;

	/**
	 * Default Constructor.
	 */
	public UserHomePK() {
		// Nothing to do.
	}

	/**
	 * 
	 * @param userId
	 * @param homeId
	 */
	public UserHomePK(Long userId, Long homeId) {
		this.userId = userId;
		this.homeId = homeId;
	}

	/**
	 * Get the userId property.
	 * 
	 * @return the userId property.
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Set the userId property.
	 * 
	 * @param userId
	 *            the userId to set.
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((homeId == null) ? 0 : homeId.hashCode());
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
		UserHomePK other = (UserHomePK) obj;
		if (homeId == null) {
			if (other.homeId != null)
				return false;
		} else if (!homeId.equals(other.homeId))
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
		return "UserHomePK [userId=" + userId + ", homeId=" + homeId + "]";
	}

}