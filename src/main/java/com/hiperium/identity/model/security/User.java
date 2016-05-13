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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This Entity represents the User object for application access.
 * 
 * @author Andres Solorzano
 */
public class User implements Serializable {

	/** The property serialVersionUID. */
	private static final long serialVersionUID = 4932964297692895600L;

	/** The property id. */
	@Min(value = 1L)
	private Long id;

	/** The property email. */
	@NotNull
	@Size(max = 100)
	private String email;

	/** The property firstname. */
	@NotNull
	@Size(max = 100)
	private String firstname;

	/** The property lastname. */
	@NotNull
	@Size(max = 100)
	private String lastname;

	/** The property languageId. */
	@NotNull
	@Size(min = 2, max = 2)
	private String languageId;
	
	/** The property active. */
	@NotNull
	private Boolean active;

	/** The property mobilePhoneNumber. */
	@Size(max = 30)
	private String mobilePhoneNumber;
	
	/** The property password. */
	@NotNull
	@Size(max = 300)
	private String password;

	/** The property comments. */
	@Size(max = 300)
	private String comments;

	/** The property version. */
	private Long version;

	/**
	 * Default constructor.
	 */
	public User() {
		// Nothing to do.
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
	 * Get the email property.
	 * 
	 * @return the email property.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email property.
	 * 
	 * @param email
	 *            the email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the firstname property.
	 * 
	 * @return the firstname property.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Set the firstname property.
	 * 
	 * @param firstname
	 *            the firstname to set.
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Get the lastname property.
	 * 
	 * @return the lastname property.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Set the lastname property.
	 * 
	 * @param lastname
	 *            the lastname to set.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Get the password property.
	 * 
	 * @return the password property.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password property.
	 * 
	 * @param password
	 *            the password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the mobilePhoneNumber property.
	 * 
	 * @return the mobilePhoneNumber property.
	 */
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	/**
	 * Set the mobilePhoneNumber property.
	 * 
	 * @param mobilePhoneNumber
	 *            the mobilePhoneNumber to set.
	 */
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
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
	 * Get the languageId property.
	 * 
	 * @return the languageId property.
	 */
	public String getLanguageId() {
		return languageId;
	}

	/**
	 * Set the languageId property.
	 * 
	 * @param languageId
	 *            the languageId to set.
	 */
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
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
		User other = (User) obj;
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
		return "User [id=" + id + ", email=" + email + ", firstname="
				+ firstname + ", lastname=" + lastname + ", languageId="
				+ languageId + ", active=" + active + ", mobilePhoneNumber="
				+ mobilePhoneNumber + ", version=" + version + "]";
	}

}
