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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.hiperium.identity.model.EnumYesNo;

/**
 * This class represents the user access to every functionality in the system
 * based in the assigned profile.
 * 
 * @author Andres Solorzano
 */
@XmlRootElement
public class ProfileFunctionality implements Serializable {

	/**
	 * The property serialVersionUID.
	 */
	private static final long serialVersionUID = -784008595525100424L;

	/** The property HOUR_FORMAT. */
	private static final String HOUR_FORMAT = "HH";
	/** The property MINUTE_FORMAT. */
	private static final String MINUTE_FORMAT = "mm";

	/** The property pk. */
	@NotNull
	private ProfileFunctionalityPK pk;

	/** The property allowInsert. */
	@NotNull
	private Boolean allowInsert;

	/** The property allowUpdate. */
	@NotNull
	private Boolean allowUpdate;

	/** The property allowDelete. */
	@NotNull
	private Boolean allowDelete;

	/** The property hourFrom. */
	@NotNull
	private Date hourFrom;

	/** The property hourUntil. */
	@NotNull
	private Date hourUntil;

	/** The property state. */
	@NotNull
	private Boolean active;

	/** The property version. */
	private Long version;

	/** The property functionality. */
	@NotNull
	private Functionality functionality;

	/**
	 * Default constructor.
	 */
	public ProfileFunctionality() {
		this.pk = new ProfileFunctionalityPK();
	}

	/**
	 * 
	 * @param AccessProfilePK
	 */
	public ProfileFunctionality(ProfileFunctionalityPK AccessProfilePK) {
		this.pk = AccessProfilePK;
	}

	/**
	 * Class constructor.
	 * 
	 * @param profileFunctionalityPK
	 *            the profile functionality object identifier.
	 * @param allowInsert
	 *            if this profile can insert data.
	 * @param allowUpdate
	 *            if this profile can update data.
	 * @param allowDelete
	 *            if this profile can delete data.
	 */
	public ProfileFunctionality(
			ProfileFunctionalityPK profileFunctionalityPK, Boolean allowInsert,
			Boolean allowUpdate, Boolean allowDelete) {
		this.pk = profileFunctionalityPK;
		this.allowInsert = allowInsert;
		this.allowUpdate = allowUpdate;
		this.allowDelete = allowDelete;
	}

	/**
	 * Class constructor.
	 * 
	 * @param functionalityId
	 *            the functionality identifier.
	 * @param profileId
	 *            the profile identifier.
	 */
	public ProfileFunctionality(Integer functionalityId, Long profileId) {
		this.pk = new ProfileFunctionalityPK(functionalityId, profileId);
	}

	/**
	 * Gets the pk property.
	 * 
	 * @return the pk property.
	 */
	public ProfileFunctionalityPK getPk() {
		return pk;
	}

	/**
	 * Sets the pk property.
	 * 
	 * @param pk
	 *            the variable pk to be assigned.
	 */
	public void setPk(ProfileFunctionalityPK pk) {
		this.pk = pk;
	}

	/**
	 * Get the allowInsert property.
	 * 
	 * @return the allowInsert property.
	 */
	public Boolean getAllowInsert() {
		return allowInsert;
	}

	/**
	 * Set the allowInsert property.
	 * 
	 * @param allowInsert
	 *            the allowInsert to set.
	 */
	public void setAllowInsert(Boolean allowInsert) {
		this.allowInsert = allowInsert;
	}

	/**
	 * Get the allowUpdate property.
	 * 
	 * @return the allowUpdate property.
	 */
	public Boolean getAllowUpdate() {
		return allowUpdate;
	}

	/**
	 * Set the allowUpdate property.
	 * 
	 * @param allowUpdate
	 *            the allowUpdate to set.
	 */
	public void setAllowUpdate(Boolean allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	/**
	 * Get the allowDelete property.
	 * 
	 * @return the allowDelete property.
	 */
	public Boolean getAllowDelete() {
		return allowDelete;
	}

	/**
	 * Set the allowDelete property.
	 * 
	 * @param allowDelete
	 *            the allowDelete to set.
	 */
	public void setAllowDelete(Boolean allowDelete) {
		this.allowDelete = allowDelete;
	}

	/**
	 * Get the hourFrom property.
	 * 
	 * @return the hourFrom property.
	 */
	public Date getHourFrom() {
		return hourFrom;
	}

	/**
	 * Set the hourFrom property.
	 * 
	 * @param hourFrom
	 *            the hourFrom to set.
	 */
	public void setHourFrom(Date hourFrom) {
		this.hourFrom = hourFrom;
	}

	/**
	 * Get the hourUntil property.
	 * 
	 * @return the hourUntil property.
	 */
	public Date getHourUntil() {
		return hourUntil;
	}

	/**
	 * Set the hourUntil property.
	 * 
	 * @param hourUntil
	 *            the hourUntil to set.
	 */
	public void setHourUntil(Date hourUntil) {
		this.hourUntil = hourUntil;
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
	 * Get the functionality property.
	 * 
	 * @return the functionality property.
	 */
	public Functionality getFunctionality() {
		return functionality;
	}

	/**
	 * Set the functionality property.
	 * 
	 * @param functionality
	 *            the functionality to set.
	 */
	public void setFunctionality(Functionality functionality) {
		this.functionality = functionality;
	}

	/**
	 * Gets the property description.
	 * 
	 * @return the property description.
	 */
	public String getAllowInsertDescription() {
		return EnumYesNo.decodeValue(this.allowInsert);
	}

	/**
	 * Gets the property description.
	 * 
	 * @return the property description.
	 */
	public String getAllowUpdateDescription() {
		return EnumYesNo.decodeValue(this.allowUpdate);
	}

	/**
	 * Gets the property description.
	 * 
	 * @return the property description.
	 */
	public String getAllowDeleteDescription() {
		return EnumYesNo.decodeValue(this.allowDelete);
	}

	/**
	 * Gets the time in hours from which the profile has access to
	 * functionality.
	 * 
	 * @return the time in hours from which the profile has access to
	 *         functionality.
	 */
	public Integer getHourOfHourFrom() {
		SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT);
		return Integer.valueOf(sdf.format(this.hourFrom));
	}

	/**
	 * Sets the hour time to hour form field in which the profile has access to
	 * functionality.
	 * 
	 * @param hour
	 *            the hour time to hour form field in which the profile has
	 *            access to functionality.
	 */
	public void setHourOfHourFrom(Integer hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.hourFrom);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		this.hourFrom = calendar.getTime();
	}

	/**
	 * Gets the time in string format from which the profile has access to
	 * functionality.
	 * 
	 * @return the time in string format from which the profile has access to
	 *         functionality.
	 */
	public String getHourStringFromHourFrom() {
		SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT);
		return sdf.format(this.hourFrom);
	}

	/**
	 * Gets the minute of the hour from field in which the profile has access to
	 * functionality.
	 * 
	 * @return the minute of the hour from field in which the profile has access
	 *         to functionality.
	 */
	public Integer getMinuteOfHourFrom() {
		SimpleDateFormat sdf = new SimpleDateFormat(MINUTE_FORMAT);
		return Integer.valueOf(sdf.format(this.hourFrom));
	}

	/**
	 * Sets the minutes in the hour from field in which the profile has access
	 * to functionality.
	 * 
	 * @param minute
	 *            the minutes in the hour from field in which the profile has
	 *            access to functionality.
	 */
	public void setMinuteOfHourFrom(Integer minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.hourFrom);
		calendar.set(Calendar.MINUTE, minute);
		this.hourFrom = calendar.getTime();
	}

	/**
	 * Gets the minute of the hour from field in string format in which the
	 * profile has access to functionality.
	 * 
	 * @return the minute of the hour from field in string format in which the
	 *         profile has access to functionality.
	 */
	public String getMinuteStringFromHourFrom() {
		SimpleDateFormat sdf = new SimpleDateFormat(MINUTE_FORMAT);
		return sdf.format(this.hourFrom);
	}

	/**
	 * Gets the hour time from hour until field in which the profile has access
	 * to functionality.
	 * 
	 * @return the hour time from hour until field in which the profile has
	 *         access to functionality.
	 */
	public Integer getHourOfHourUntil() {
		SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT);
		return Integer.valueOf(sdf.format(this.hourUntil));
	}

	/**
	 * Sets the hour time to hour until field in which the profile has access to
	 * functionality.
	 * 
	 * @param hour
	 *            the hour time to hour until field in which the profile has
	 *            access to functionality.
	 */
	public void setHourOfHourUntil(Integer hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.hourUntil);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		this.hourUntil = calendar.getTime();
	}

	/**
	 * Gets the hour time from hour until field in string format in which the
	 * profile has access to functionality.
	 * 
	 * @return the the hour time from hour until field in string format in which
	 *         the profile has access to functionality.
	 */
	public String getHourStringFromHourUntil() {
		SimpleDateFormat sdf = new SimpleDateFormat(HOUR_FORMAT);
		return sdf.format(this.hourUntil);
	}

	/**
	 * Gets the minute of the hour until in which the profile has access to
	 * functionality.
	 * 
	 * @return the the minute of the hour until in which the profile has access
	 *         to functionality.
	 */
	public Integer getMinuteOfHourUntil() {
		SimpleDateFormat sdf = new SimpleDateFormat(MINUTE_FORMAT);
		return Integer.valueOf(sdf.format(this.hourUntil));
	}

	/**
	 * Sets the minute of the hour to the hour until field in which the profile
	 * has access to functionality.
	 * 
	 * @param minute
	 *            the minute of the hour to the hour until field in which the
	 *            profile has access to functionality.
	 */
	public void setMinuteOfHourUntil(Integer minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.hourUntil);
		calendar.set(Calendar.MINUTE, minute);
		this.hourUntil = calendar.getTime();
	}

	/**
	 * Gets the minute of the hour until in string format in which the profile
	 * has access to functionality.
	 * 
	 * @return the minute of the hour until in string format in which the
	 *         profile has access to functionality.
	 */
	public String getMinuteStringFromHourUntil() {
		SimpleDateFormat sdf = new SimpleDateFormat(MINUTE_FORMAT);
		return sdf.format(this.hourUntil);
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
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		ProfileFunctionality other = (ProfileFunctionality) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProfileFunctionality [pk=" + pk + ", allowInsert="
				+ allowInsert + ", allowUpdate=" + allowUpdate
				+ ", allowDelete=" + allowDelete + ", hourFrom=" + hourFrom
				+ ", hourUntil=" + hourUntil + ", active=" + active
				+ ", version=" + version + "]";
	}

}
