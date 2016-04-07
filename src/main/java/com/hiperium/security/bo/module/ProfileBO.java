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
package com.hiperium.security.bo.module;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.common.services.dto.SelectionDTO;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.security.model.security.Functionality;
import com.hiperium.security.model.security.Profile;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface ProfileBO {

	/**
	 * 
	 * @param profile
	 * @param tokenId
	 *            the session identifier
	 * @throws InformationException
	 */
	void create(@NotNull Profile profile, @NotNull String tokenId)
			throws InformationException;

	/**
	 * 
	 * @param profile
	 * @param tokenId
	 *            the session identifier
	 * @return
	 * @throws InformationException
	 */
	Profile update(@NotNull Profile profile, @NotNull String tokenId)
			throws InformationException;

	/**
	 * 
	 * @param profileId
	 * @param tokenId
	 * @throws InformationException
	 */
	void delete(@NotNull @Min(value = 1L) Long profileId,
			@NotNull String tokenId) throws InformationException;

	/**
	 * 
	 * @param profileId
	 * @param functionalities
	 * @param tokenId
	 * @throws InformationException
	 */
	void updateProfileFunc(@NotNull @Min(value = 1L) Long profileId,
			@NotNull List<Functionality> functionalities,
			@NotNull String tokenId) throws InformationException;

	/**
	 * 
	 * @param profileId
	 * @param selectedZones
	 * @param tokenId
	 * @return
	 * @throws InformationException
	 */
	Profile updateProfileZones(@NotNull @Min(value = 1L) Long profileId,
			@NotNull List<Integer> selectedZones, @NotNull String tokenId)
			throws InformationException;

	/**
	 * 
	 * @param list
	 * @param newState
	 * @param tokenId
	 * @return
	 * @throws InformationException
	 */
	List<Profile> updateRegisterState(@NotNull List<Profile> list,
			boolean newState, @NotNull String tokenId)
			throws InformationException;
	
	/**
	 * 
	 * @param homeId
	 * @param tokenId
	 * @return
	 */
	List<SelectionDTO> findByHomeId(
			@NotNull @Min(value = 1L) Long homeId, @NotNull String tokenId);
}
