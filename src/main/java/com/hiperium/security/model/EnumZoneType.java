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
package com.hiperium.security.model;

/**
 * This enumeration specifies the existing zones in the system.
 * 
 * @author Andres Solorzano
 * 
 */
public enum EnumZoneType {

	/** The MAIN_ENTRANCE element witch value is mainEntrance. */
	MAIN_ENTRANCE("mainEntrance"),

	/** The HALL element witch value is hall. */
	HALL("hall"),

	/** The BATHROOM element witch value is bath. */
	BATH("bath"),

	/** The BEDROOM element witch value is bedroom. */
	BEDROOM("bedroom"),

	/** The LAUNDRY element witch value is laundry. */
	LAUNDRY("laundry"),

	/** The TERRACE element witch value is terrace. */
	TERRACE("terrace"),

	/** The KITCHEN element witch value is kitchen. */
	KITCHEN("kitchen"),

	/** The DINING element witch value is dining. */
	DINING("dining"),

	/** The DOOR element witch value is garden. */
	GARDEN("garden"),
	
	/** The BAR element witch value is bar. */
	BAR("bar"),

	/** The GARAGE element witch value is garage. */
	GARAGE("garage");

	/** Property that contains the label of the element */
	private final String value;

	/**
	 * Enumeration constructor.
	 * 
	 * @param value
	 *            Label of the enumeration element
	 */
	private EnumZoneType(String value) {
		this.value = value;
	}

	/**
	 * Return the label associate to the element of the enumeration.
	 * 
	 * @return the label associate with the enumeration
	 */
	public String getValue() {
		return this.value;
	}
}
