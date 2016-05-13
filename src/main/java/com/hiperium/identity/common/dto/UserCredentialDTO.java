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
 * This is a POJO class that will be used to authenticate against Web Services.
 * 
 * @author Andres Solorzano
 * 
 */
@XmlRootElement
public class UserCredentialDTO implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = 5219100976630516452L;

	/** The property email. */
	@NotNull
	@Size(min = 6, max = 100)
	private String email;
	
	/** The property password. */
	@NotNull
	@Size(min = 6, max = 20)
	private String password;

	/**
	 * Default constructor.
	 */
	public UserCredentialDTO() {
		// Nothing to do.
	}
	
	/**
	 * 
	 * @param userEmail
	 * @param password
	 */
	public UserCredentialDTO(String userEmail, String password) {
		this.email = userEmail;
		this.password = password;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
