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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the basic fields used to filter a basic searching.
 * 
 * @author Andres Solorzano
 * 
 */
@XmlRootElement
public class UpdatePasswordDTO implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = 4139110385093518612L;
	
	/** The property prevPassword. */
	@NotNull
	@Size(min = 6, max = 20)
	private String prevPassword;
	
	/** The property newPassword. */
	@NotNull
	@Size(min = 6, max = 20)
	private String newPassword;
	
	/** The property confirmPassword. */
	@NotNull
	@Size(min = 6, max = 20)
	private String confirmPassword;

	/**
	 * Default constructor.
	 */
	public UpdatePasswordDTO() {
		// Nothing to do.
	}

	/**
	 * Get the prevPassword property.
	 * 
	 * @return the prevPassword property.
	 */
	public String getPrevPassword() {
		return prevPassword;
	}

	/**
	 * Set the prevPassword property.
	 * 
	 * @param prevPassword
	 *            the prevPassword to set.
	 */
	public void setPrevPassword(String prevPassword) {
		this.prevPassword = prevPassword;
	}

	/**
	 * Get the newPassword property.
	 * 
	 * @return the newPassword property.
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Set the newPassword property.
	 * 
	 * @param newPassword
	 *            the newPassword to set.
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * Get the confirmPassword property.
	 * 
	 * @return the confirmPassword property.
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Set the confirmPassword property.
	 * 
	 * @param confirmPassword
	 *            the confirmPassword to set.
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpdatePasswordDTO [prevPassword=" + prevPassword
				+ ", newPassword=" + newPassword + ", confirmPassword="
				+ confirmPassword + "]";
	}

}
