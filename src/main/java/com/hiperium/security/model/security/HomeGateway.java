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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Andres Solorzano
 * @version 1.0
 *
 */
public class HomeGateway implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = 3325869292944471153L;

	/** The property id. */
	@Min(value = 1L)
	private Long id;

	/** The property homeId. */
	@Min(value = 1L)
	private Long homeId;

	/** The property name. */
	@NotNull
	@Size(max = 100)
	private String name;

	/** The property serial. */
	@NotNull
	@Size(max = 50)
	private String serial;

	/** The property token. */
	@Size(max = 50)
	private String token;

	/**
	 * 
	 * Class constructor.
	 */
	public HomeGateway() {
		// Nothing to do.
	}

	/**
	 * Gets the id property.
	 * 
	 * @return the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id property.
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the homeId property.
	 * 
	 * @return the homeId.
	 */
	public Long getHomeId() {
		return homeId;
	}

	/**
	 * Sets the homeId property.
	 * 
	 * @param homeId
	 *            the homeId to set.
	 */
	public void setHomeId(Long homeId) {
		this.homeId = homeId;
	}

	/**
	 * Gets the name property.
	 * 
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name property.
	 * 
	 * @param name
	 *            the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the serial property.
	 * 
	 * @return the serial.
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * Sets the serial property.
	 * 
	 * @param serial
	 *            the serial to set.
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the tokenId to set
	 */
	public void setToken(String token) {
		this.token = token;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		HomeGateway other = (HomeGateway) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "HomeGateway [id=" + id + ", homeId=" + homeId + ", name="
				+ name + ", serial=" + serial + "]";
	}

}
