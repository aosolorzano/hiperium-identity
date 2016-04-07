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

import com.hiperium.security.model.EnumYesNo;

/**
 * This class represents the user's home in the system.
 * 
 * @author Andres Solorzano.
 * 
 */
public class Home implements Serializable {

	/** The property serialVersionUID. */
	private static final long serialVersionUID = 7302876505305961427L;

	/** The property id. */
	@Min(value = 1L)
	private Long id;

	/** The property name. */
	@Size(max = 100)
	private String name;
	
	/** The property active. */
	@NotNull
	private Boolean active;

	/** The property cloudEnable. */
	@NotNull
	private Boolean cloudEnable;

	/** The property protectedMode. */
	@NotNull
	private Boolean protectedMode;

	/** The property comments. */
	@Size(max = 300)
	private String comments;

	/** The property version. */
	private Long version;

	/**
	 * Default Constructor.
	 */
	public Home() {
		this.cloudEnable = Boolean.TRUE;
		this.protectedMode = Boolean.FALSE;
	}

	/**
	 * Get the id property.
	 * 
	 * @return the id property.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set the id property.
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get the name property.
	 * 
	 * @return the name property.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name property.
	 * 
	 * @param name
	 *            the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the cloudEnable property.
	 * 
	 * @return the cloudEnable property.
	 */
	public Boolean getCloudEnable() {
		return cloudEnable;
	}

	/**
	 * Set the cloudEnable property.
	 * 
	 * @param cloudEnable
	 *            the cloudEnable to set.
	 */
	public void setCloudEnable(Boolean cloudEnable) {
		this.cloudEnable = cloudEnable;
	}

	/**
	 * Get the protectedMode property.
	 * 
	 * @return the protectedMode property.
	 */
	public Boolean getProtectedMode() {
		return protectedMode;
	}

	/**
	 * Set the protectedMode property.
	 * 
	 * @param protectedMode
	 *            the protectedMode to set.
	 */
	public void setProtectedMode(Boolean protectedMode) {
		this.protectedMode = protectedMode;
	}

	/**
	 * Get the comments property.
	 * 
	 * @return the comments property.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Set the comments property.
	 * 
	 * @param comments
	 *            the comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Get the version property.
	 * 
	 * @return the version property.
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * Set the version property.
	 * 
	 * @param version
	 *            the version to set.
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * Gets the active property.
	 * @return the active.
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Sets the active property.
	 * @param active the active to set.
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Gets the property description.
	 * 
	 * @return the property description.
	 */
	public String getCloudEnableDescription() {
		return this.cloudEnable ? EnumYesNo.YES.getLabel() : EnumYesNo.NO
				.getLabel();
	}

	/**
	 * Gets the property description.
	 * 
	 * @return the property description.
	 */
	public String getProtectedModeDescription() {
		return this.protectedMode ? EnumYesNo.YES.getLabel() : EnumYesNo.NO
				.getLabel();
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
		Home other = (Home) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Home [id=" + id + ", name=" + name + ", active=" + active
				+ ", cloudEnable=" + cloudEnable + ", protectedMode="
				+ protectedMode + ", version=" + version + "]";
	}

}