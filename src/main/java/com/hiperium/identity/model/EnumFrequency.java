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
package com.hiperium.identity.model;

/**
 * This enumeration specifies the allowed frequencies in the system.
 * 
 * @author Andres Solorzano
 */
public enum EnumFrequency {

	/** The ONCE element witch value is once. */
	ONCE("once"),

	/** The EVERY_MIMUTE element witch value is everyMinute. */
	EVERY_MIMUTE("everyMinute"),

	/** The QUARTER_HOUR element witch value is eachQuarterHour. */
	QUARTER_HOUR("eachQuarterHour"),

	/** The ONCE element witch value is half. */
	HALF_HOUR("everyHalfHour"),

	/** The HOURLY element witch value is hourly. */
	HOURLY("hourly"),

	/** The DAILY element witch value is daily. */
	DAILY("daily"),

	/** The WEEKLY element witch value is weekly. */
	WEEKLY("weekly"),

	/** The MONTHLY element witch value is monthly. */
	MONTHLY("monthly"),

	/** The TRIMESTER element witch value is quarterly. */
	QUARTERLY("quarterly"),

	/** The SEMESTER element witch value is semiannual. */
	SEMIANNUAL("semiannual"),

	/** The ANUALLY element witch value is annual. */
	ANNUAL("annual"),

	/** The CUSTOM element witch value is custom. */
	CUSTOM("custom");

	/** Property that contains the label of the element */
	private final String label;

	/**
	 * Enumeration constructor.
	 * 
	 * @param value
	 *            Value of the enumeration element
	 * @param label
	 *            Label of the enumeration element
	 */
	EnumFrequency(String label) {
		this.label = label;
	}

	/**
	 * Return the label associate to the element of the enumeration.
	 * 
	 * @return the label associate with the enumeration
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Decodes a label of a specific frequency.
	 * 
	 * @param value
	 *            the value to be decoded
	 * @return a frequency of a specific value.
	 */
	public static String decodeValue(EnumFrequency frequency) {
		for (EnumFrequency e : EnumFrequency.values()) {
			if (e.equals(frequency)) {
				return e.getLabel();
			}
		}
		return EnumFrequency.ONCE.getLabel();
	}
}
