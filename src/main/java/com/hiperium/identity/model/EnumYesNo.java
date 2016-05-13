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
 * This enumeration represents the yes and no options.
 * 
 * @author Andres Solorzano
 */
public enum EnumYesNo {

	/** Resource YES for yes option. */
	YES("yes", true),

	/** Resource NO for no option. */
	NO("no", false);

	/** Property label. */
	private final String label;
	
	/** Property value. */
	private final Boolean value;

	/**
	 * The Enumeration constructor.
	 * 
	 * @param valor
	 *            Value for the element of the Enumeration
	 */
	private EnumYesNo(String label, Boolean value) {
		this.label = label;
		this.value = value;
	}

	/**
	 * Return the label associate to the element of the enumeration.
	 * 
	 * @return the value associate with the enumeration
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getValue() {
		return value;
	}
	
	/**
	 * Decodes a label of a specific value.
	 * 
	 * @param value
	 *            the value to be decoded
	 * @return a label of a specific value.
	 */
	public static String decodeValue(Boolean value) {
		for (EnumYesNo e : EnumYesNo.values()) {
			if (value.equals(e.getValue())) {
				return e.getLabel();
			}
		}
		return EnumYesNo.NO.getLabel();
	}
}
