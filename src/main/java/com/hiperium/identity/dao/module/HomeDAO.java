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

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.dto.SelectionDTO;
import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.identity.model.security.Home;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface HomeDAO {

	/**
	 * 
	 * @param homeId
	 * @return
	 * @throws InformationException
	 */
	Boolean findCloudEnable(@NotNull @Min(value = 1L) Long homeId)
			throws InformationException;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<SelectionDTO> findByUserId(@NotNull @Min(value = 1L) Long userId);

	/**
	 * 
	 * @param register
	 * @return
	 */
	Home update(Home register);

	/**
	 * 
	 * @param id
	 * @param lock
	 * @param bypassCache
	 * @return
	 */
	Home findById(Long id, boolean lock, boolean bypassCache);
}
