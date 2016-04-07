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
package com.hiperium.security.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the response for home authentication.
 * 
 * @author Andres Solorzano
 * 
 */
@XmlRootElement
public class UserAuthResponseDTO implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = -5151190867212462748L;

	/** The property id. */
	@NotNull
	private Long id;
	
	/** The property username. */
	@NotNull
	private String username;

	/** The property language. */
	@NotNull
	private String language;

	/** The property authorizationToken. */
	@NotNull
	private String authorizationToken;

	/** The property changePassword. */
	@NotNull
	private Boolean changePassword;

	/**
	 * Default constructor.
	 */
	public UserAuthResponseDTO() {
		// Nothing to do.
	}

	/**
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getAuthorizationToken() {
		return authorizationToken;
	}

	/**
	 * 
	 * @param authorizationToken
	 */
	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the changePassword
	 */
	public Boolean getChangePassword() {
		return changePassword;
	}

	/**
	 * @param changePassword
	 *            the changePassword to set
	 */
	public void setChangePassword(Boolean changePassword) {
		this.changePassword = changePassword;
	}

}
