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
package com.hiperium.identity.bo.module.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.services.exception.EnumInformationException;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.generic.GenericBO;
import com.hiperium.identity.bo.module.ProfileFunctionalityBO;
import com.hiperium.identity.model.security.ProfileFunctionality;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class ProfileFunctionalityBOImpl extends GenericBO implements ProfileFunctionalityBO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileFunctionality update(@NotNull ProfileFunctionality register, @NotNull String tokenId) 
				throws InformationException {
		this.log.debug("homeSelection - START");
		// You can not homeSelection the functionality profile for the
		// Administrator.
		if (1 == register.getPk().getProfileId()) {
			throw new InformationException(EnumInformationException.ADMIN_PROFILE_FUNCTION_UPDATED.getCode());
		}
		// VALIDATES THE ACCESS TIME
		if (register.getHourOfHourFrom() > register.getHourOfHourUntil()) {
			throw new InformationException(EnumInformationException.INVALID_ACCESS_TIME_PARAMETERS.getCode());
		} else if (register.getHourOfHourFrom() == register.getHourOfHourUntil()
				&& register.getMinuteOfHourFrom() > register.getMinuteOfHourUntil()) {
			throw new InformationException(EnumInformationException.INVALID_ACCESS_TIME_PARAMETERS.getCode());
		} else if (register.getHourFrom().equals(register.getHourUntil())) {
			throw new InformationException(EnumInformationException.INVALID_ACCESS_TIME_PARAMETERS.getCode());
		}
		// IF VALIDATION PASS, UPDATES THE OBJECT
		register = super.getDaoFactory().getProfileFunctionalityDAO().update(register);
		this.log.debug("homeSelection - END");
		return register;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(@NotNull ProfileFunctionality profileFunctionality,
			@NotNull String tokenId) 
					throws InformationException {
		this.log.debug("delete - START");
		// You can not delete the functionality profile for the
		// Administrator.
		if (1 == profileFunctionality.getPk().getProfileId()) {
			throw new InformationException(EnumInformationException.ADMIN_PROFILE_FUNCTION_REMOVED.getCode());
		} else {
			super.getDaoFactory().getProfileFunctionalityDAO().delete(profileFunctionality.getPk());
		}
		this.log.debug("delete - END");
	}
}
