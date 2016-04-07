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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the existing functionalities in the system.
 * 
 * @author Andres Solorzano
 */
@XmlRootElement
public class Functionality implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = -4446465608710774407L;

	/** The property id. */
	@Min(value = 1)
	private Integer id;

	/** The property URL. */
	@NotNull
	@Size(min = 1, max = 100)
	private String url;
	
	/** The property restURI. */
	@NotNull
	@Size(min = 1, max = 100)
	private String restURI;
	
	/** The property soapURI. */
	@NotNull
	@Size(min = 1, max = 100)
	private String soapURI;

	/** The property name. */
	@NotNull
	@Size(min = 1, max = 100)
	private String name;

	/** The property handleInsertions. */
	private Boolean handleInsertions;

	/** The property handlesUpdates. */
	private Boolean handlesUpdates;

	/** The property handleDeletions. */
	private Boolean handleDeletions;

	/** The property comments. */
	@Size(min = 0, max = 300)
	private String comments;

	/**
	 * Default constructor.
	 */
	public Functionality() {

	}

	/**
	 * Get the id property.
	 * 
	 * @return the id property.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Set the id property.
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Get the URL property.
	 * 
	 * @return the URL property.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the URL property.
	 * 
	 * @param url
	 *            the url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * Get the handleInsertions property.
	 * 
	 * @return the handleInsertions property.
	 */
	public Boolean getHandleInsertions() {
		return handleInsertions;
	}

	/**
	 * Set the handleInsertions property.
	 * 
	 * @param handleInsertions
	 *            the handleInsertions to set.
	 */
	public void setHandleInsertions(Boolean handleInsertions) {
		this.handleInsertions = handleInsertions;
	}

	/**
	 * Get the handlesUpdates property.
	 * 
	 * @return the handlesUpdates property.
	 */
	public Boolean getHandlesUpdates() {
		return handlesUpdates;
	}

	/**
	 * Set the handlesUpdates property.
	 * 
	 * @param handlesUpdates
	 *            the handlesUpdates to set.
	 */
	public void setHandlesUpdates(Boolean handlesUpdates) {
		this.handlesUpdates = handlesUpdates;
	}

	/**
	 * Get the handleDeletions property.
	 * 
	 * @return the handleDeletions property.
	 */
	public Boolean getHandleDeletions() {
		return handleDeletions;
	}

	/**
	 * Set the handleDeletions property.
	 * 
	 * @param handleDeletions
	 *            the handleDeletions to set.
	 */
	public void setHandleDeletions(Boolean handleDeletions) {
		this.handleDeletions = handleDeletions;
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
	 * 
	 * @return
	 */
	public String getRestURI() {
		return restURI;
	}

	/**
	 * 
	 * @param restURI
	 */
	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}

	/**
	 * 
	 * @return
	 */
	public String getSoapURI() {
		return soapURI;
	}

	/**
	 * 
	 * @param soapURI
	 */
	public void setSoapURI(String soapURI) {
		this.soapURI = soapURI;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Functionality other = (Functionality) obj;
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
		return "Functionality [id=" + id + ", url=" + url + ", name=" + name
				+ ", handleInsertions=" + handleInsertions
				+ ", handlesUpdates=" + handlesUpdates + ", handleDeletions="
				+ handleDeletions + ", comments=" + comments + "]";
	}

}
