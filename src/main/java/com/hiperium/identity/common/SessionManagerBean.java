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
package com.hiperium.identity.common;

import java.util.Calendar;
import java.util.Locale;

import javax.ejb.DependsOn;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.exception.EnumInformationException;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.identity.common.vo.ProfileFunctionalityVO;

/**
 * This bean is used only for user session validation and resource access
 * between business objects and security filters in the presentation layer.
 *
 * @author Andres Solorzano
 * @version 1.0
 */
@Startup
@Singleton
@LocalBean
@Lock(LockType.READ)
@DependsOn("ConfigurationBean")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SessionManagerBean {

	/** The property log. */
	@Inject
	protected HiperiumLogger log;
	
	/**
	 * {@inheritDoc}
	 */
	public void validateFunctionalityAccessTime(@NotNull ProfileFunctionalityVO profileFunctionalityVO,
			@NotNull Locale locale, @NotNull String sessionId) throws InformationException {
		this.log.debug("validateFunctionalityAccessTime - BEGIN");
		Calendar actualDate = Calendar.getInstance();
		int actualHour = actualDate.get(Calendar.HOUR_OF_DAY);
		int actualMinute = actualDate.get(Calendar.MINUTE);
		if (actualHour < profileFunctionalityVO.getHourOfHourFrom() || actualHour > profileFunctionalityVO.getHourOfHourUntil()) {
			throw InformationException.generate(EnumInformationException.INVALID_FUNCTION_ACCESS_TIME);
		} else if (actualHour == profileFunctionalityVO.getHourOfHourFrom() && actualMinute < profileFunctionalityVO.getMinuteOfHourFrom()) {
			throw InformationException.generate(EnumInformationException.INVALID_FUNCTION_ACCESS_TIME);
		} else if (actualHour == profileFunctionalityVO.getHourOfHourUntil() && actualMinute > profileFunctionalityVO.getMinuteOfHourUntil()) {
			throw InformationException.generate(EnumInformationException.INVALID_FUNCTION_ACCESS_TIME);
		}
		this.log.debug("validateFunctionalityAccessTime - END");
	}
}
