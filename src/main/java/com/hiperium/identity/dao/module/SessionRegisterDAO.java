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
package com.hiperium.identity.dao.module;

import java.util.UUID;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.audit.SessionRegister;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface SessionRegisterDAO {

	/**
	 * 
	 * @param sessionRegister
	 * @return
	 */
	SessionRegister create(@NotNull SessionRegister sessionRegister);
	
	/**
	 * 
	 * @param sessionRegister
	 */
	void updateHomeSelection(@NotNull SessionRegister sessionRegister);
	
	/**
	 * 
	 * @param sessionRegister
	 */
	void updateLogoutDate(@NotNull UUID id);
}
