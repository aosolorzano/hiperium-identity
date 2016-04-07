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
package com.hiperium.security.bo.module.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.exception.EnumInformationException;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.security.audit.bo.AuditManagerBO;
import com.hiperium.security.bo.generic.GenericBO;
import com.hiperium.security.bo.module.UpdatePasswordBO;
import com.hiperium.security.common.utils.HashMd5;
import com.hiperium.security.model.security.User;

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
    
	/** The property auditManagerBO. */
	@EJB
	private AuditManagerBO auditManagerBO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserPassword(@NotNull String newPassword, @NotNull String prevPassword, @NotNull String tokenId) 
				throws InformationException {
		this.log.debug("updateUserPassword - START");
		Long userId = super.getSessionManager().findUserId(tokenId);
		User user = super.getDaoFactory().getUserDAO().findById(userId, false, true);
		if(user == null) {
			throw InformationException.generate(EnumInformationException.USER_NOT_FOUND);
		} else if(!new HashMd5().hash(prevPassword).equals(user.getPassword())){
			throw InformationException.generate(EnumInformationException.USER_PREVIOUS_PASS_NOT_MATCH);
		} else {
			user.setPassword(new HashMd5().hash(newPassword));
			super.getDaoFactory().getUserDAO().update(user);
			try {
				this.auditManagerBO.updateLastPasswordChange(user.getId(), tokenId);
			} catch (Exception e) {
				throw new InformationException(e.getMessage());
			}
		}
		this.log.debug("updateUserPassword - END");
	}
}
