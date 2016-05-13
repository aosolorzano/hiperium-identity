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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the profile functionality in the form of date transfer
 * object.
 * 
 * @author Andres Solorzano
 * 
 */
@XmlRootElement
public class ProfileFunctionalityVO implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = -3925756486842808344L;
    
	/** */
	private static final String HOUR_FORMAT = "HH";
	/** */
	private static final String MINUTE_FORMAT = "mm";
	
    /**  */
    private final Integer functionalityId;
    /**  */
    private final Date hourFrom;
    /**  */
    private final Date hourUntil;
	
    /**
	 * Class constructor.
	 * @param functionalityId
	 * @param hourFrom
	 * @param houUntil
	 */
	public ProfileFunctionalityVO(Integer functionalityId, Date hourFrom, Date houUntil) {
		this.functionalityId = functionalityId;
		this.hourFrom = hourFrom;
		this.hourUntil = houUntil;
	}

	/**
	 * Gets the functionalityId property.
	 * 
	 * @return the functionalityId.
	 */
	public Integer getFunctionalityId() {
		return functionalityId;
	}

	/**
	 * Gets the hourFrom property.
	 * 
	 * @return the hourFrom.
	 */
	public Date getHourFrom() {
		return hourFrom;
	}

	/**
	 * Gets the hourUntil property.
	 * 
	 * @return the hourUntil.
	 */
	public Date getHourUntil() {
		return hourUntil;
	}

	/**
	 * Gets the time in hours from which the profile has access to
	 * functionality.
	 * 
	 * @return the time in hours from which the profile has access to
	 *         functionality.
	 */
	public Integer getHourOfHourFrom() {
		if(this.hourFrom != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT);
			return Integer.valueOf(sdf.format(this.hourFrom));
		}
		return null;
	}

	/**
	 * Gets the time in string format from which the profile has access to
	 * functionality.
	 * 
	 * @return the time in string format from which the profile has access to
	 *         functionality.
	 */
	public String getHourStringFromHourFrom() {
		if(this.hourFrom != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT);
			return sdf.format(this.hourFrom);
		}
		return null;
	}

	/**
	 * Gets the minute of the hour from field in which the profile has access to
	 * functionality.
	 * 
	 * @return the minute of the hour from field in which the profile has access
	 *         to functionality.
	 */
	public Integer getMinuteOfHourFrom() {
		if(this.hourFrom != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(MINUTE_FORMAT);
			return Integer.valueOf(sdf.format(this.hourFrom));
		}
		return null;
	}

	/**
	 * Gets the minute of the hour from field in string format in which the
	 * profile has access to functionality.
	 * 
	 * @return the minute of the hour from field in string format in which the
	 *         profile has access to functionality.
	 */
	public String getMinuteStringFromHourFrom() {
		if(this.hourFrom != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(MINUTE_FORMAT);
			return sdf.format(this.hourFrom);
		}
		return null;
	}

	/**
	 * Gets the hour time from hour until field in which the profile has access
	 * to functionality.
	 * 
	 * @return the hour time from hour until field in which the profile has
	 *         access to functionality.
	 */
	public Integer getHourOfHourUntil() {
		if(this.hourUntil != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT);
			return Integer.valueOf(sdf.format(this.hourUntil));
		}
		return null;
	}

	/**
	 * Gets the hour time from hour until field in string format in which the
	 * profile has access to functionality.
	 * 
	 * @return the the hour time from hour until field in string format in which
	 *         the profile has access to functionality.
	 */
	public String getHourStringFromHourUntil() {
		if(this.hourUntil != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT);
			return sdf.format(this.hourUntil);
		}
		return null;
	}

	/**
	 * Gets the minute of the hour until in which the profile has access to
	 * functionality.
	 * 
	 * @return the the minute of the hour until in which the profile has access
	 *         to functionality.
	 */
	public Integer getMinuteOfHourUntil() {
		if(this.hourUntil != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(MINUTE_FORMAT);
			return Integer.valueOf(sdf.format(this.hourUntil));
		}
		return null;
	}

	/**
	 * Gets the minute of the hour until in string format in which the profile
	 * has access to functionality.
	 * 
	 * @return the minute of the hour until in string format in which the
	 *         profile has access to functionality.
	 */
	public String getMinuteStringFromHourUntil() {
		if(this.hourUntil != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(MINUTE_FORMAT);
			return sdf.format(this.hourUntil);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProfileFunctionalityVO [functionalityId=" + functionalityId
				+ ", hourFrom=" + hourFrom + ", hourUntil=" + hourUntil + "]";
	}
	
}
