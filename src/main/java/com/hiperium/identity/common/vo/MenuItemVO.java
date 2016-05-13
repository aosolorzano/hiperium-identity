/**
 * Open Source Solutions Corp.
 * Product: Hiperium
 * Created: 12-Mar-2011 - 00:00:00
 * 
 * 
 * The contents of this file are copyrighted by Open Source Solutions Corp. 
 * and it is protected by the license: "GPL V3." You can find a copy of this 
 * license at: http://www.aldebaran-solutions.com
 * 
 * Copyright 2011 Open Source Solutions Corp. All rights reserved.
 * 
 */
package com.hiperium.identity.common.vo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the menu register in the form of Value Object,
 * with the purpose of manage user navigation.
 * 
 * @author Andres Solorzano.
 * 
 */
@XmlRootElement
public class MenuItemVO implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = -7071938831636242674L;

	/** */
	private String text;
	/** */
	private String url;

	/** */
	private ProfileFunctionalityVO profileFunctionalityVO;

	/**
	 * 
	 * Class constructor.
	 * 
	 * @param functionalityId
	 * @param text
	 * @param url
	 * @param hourFrom
	 * @param houUntil
	 */
	public MenuItemVO(Integer functionalityId, String text, String url,
			Date hourFrom, Date houUntil) {
		this.text = text;
		this.url = url;
		this.profileFunctionalityVO = new ProfileFunctionalityVO(
				functionalityId, hourFrom, houUntil);
	}

	/**
	 * Gets the text property.
	 *
	 * @return the text property.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text property.
	 *
	 * @param text
	 *            the variable text to be assigned.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the URL property.
	 *
	 * @return the URL property.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the URL property.
	 *
	 * @param url
	 *            the variable URL to be assigned.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the profileFunctionalityDTO property.
	 *
	 * @return the profileFunctionalityDTO property.
	 */
	public ProfileFunctionalityVO getProfileFunctionalityDTO() {
		return profileFunctionalityVO;
	}

	/**
	 * Sets the profileFunctionalityDTO property.
	 *
	 * @param profileFunctionalityDTO
	 *            the variable profileFunctionalityDTO to be assigned.
	 */
	public void setProfileFunctionalityDTO(
			ProfileFunctionalityVO profileFunctionalityDTO) {
		this.profileFunctionalityVO = profileFunctionalityDTO;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MenuItemVO [text=" + text + ", url=" + url
				+ ", profileFunctionalityVO=" + profileFunctionalityVO + "]";
	}

}
