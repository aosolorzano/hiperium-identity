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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.services.exception.EnumInformationException;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.generic.GenericBO;
import com.hiperium.identity.bo.module.UpdatePasswordBO;
import com.hiperium.identity.common.utils.HashMd5;
import com.hiperium.identity.dao.module.UserStatisticDAO;
import com.hiperium.identity.model.security.User;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class UpdatePasswordImpl extends GenericBO implements UpdatePasswordBO {

	/** The property log. */
    @Inject
    protected HiperiumLogger log;
    
	/** The property userStatisticsDAO. */
	@EJB
	private UserStatisticDAO userStatisticsDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserPassword(@NotNull @Min(value = 1L) Long userId, @NotNull String newPassword, @NotNull String prevPassword, 
			@NotNull String tokenId) throws InformationException {
		this.log.debug("updateUserPassword - START");
		User user = super.getDaoFactory().getUserDAO().findById(userId, false, true);
		if(user == null) {
			throw new InformationException(EnumInformationException.USER_NOT_FOUND.getCode());
		} else if(!new HashMd5().hash(prevPassword).equals(user.getPassword())){
			throw new InformationException(EnumInformationException.USER_PREVIOUS_PASS_NOT_MATCH.getCode());
		} else {
			user.setPassword(new HashMd5().hash(newPassword));
			super.getDaoFactory().getUserDAO().update(user);
			try {
				this.userStatisticsDAO.updateLastPasswordChange(user.getId());
			} catch (Exception e) {
				throw new InformationException(e.getMessage());
			}
		}
		this.log.debug("updateUserPassword - END");
	}
}
