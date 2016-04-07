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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanPropertyValueChangeClosure;
import org.apache.commons.collections.CollectionUtils;

import com.hiperium.common.services.dto.SelectionDTO;
import com.hiperium.common.services.exception.InformationException;
import com.hiperium.common.services.logger.HiperiumLogger;
import com.hiperium.security.bo.generic.GenericBO;
import com.hiperium.security.bo.module.ProfileBO;
import com.hiperium.security.dao.list.ListOperationDAO;
import com.hiperium.security.model.security.Functionality;
import com.hiperium.security.model.security.Profile;
import com.hiperium.security.model.security.ProfileFunctionality;
import com.hiperium.security.model.security.ProfileFunctionalityPK;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class ProfileBOImpl extends GenericBO implements ProfileBO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/** The property listOperationBO. */
	@EJB
	private ListOperationDAO<ProfileFunctionality, ProfileFunctionalityPK> listOperationDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(@NotNull Profile profile, 
			@NotNull String tokenId) throws InformationException {
		this.log.debug("create - START");
		super.getDaoFactory().getProfileDAO().create(profile);
		this.log.debug("create - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Profile update(@NotNull Profile profile, @NotNull String tokenId) 
			throws InformationException {
		this.log.debug("homeSelection - START");
		// TODO: VALIDATE THAT DO NOT INACTIVATE ADMINISTRATOR PROFILE
		profile = super.getDaoFactory().getProfileDAO().update(profile);
		this.log.debug("homeSelection - END");
		return profile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(@NotNull @Min(value = 1L) Long profileId, 
			@NotNull String tokenId)
					throws InformationException {
		this.log.debug("delete - START");
		// TODO: VALIDATE THAT DO NOT DELETE ADMINISTRATOR PROFILE
		super.getDaoFactory().getProfileDAO().delete(profileId);
		this.log.debug("delete - END");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateProfileFunc(@NotNull @Min(value = 1L) Long profileId, 
			@NotNull List<Functionality> functionalities,
			@NotNull String tokenId) 
					throws InformationException {
		this.log.debug("updateProfileFunc - START");
		Profile profile = super.getDaoFactory().getProfileDAO().findById(profileId, false, true);
		// TODO: VALIDATE THAT DO NOT REMOVE THE USER ADMINISTRATOR FUNCTIONALITIES
		if (functionalities != null && !functionalities.isEmpty()) {
			List<ProfileFunctionality> selectedFunctionalities = new ArrayList<ProfileFunctionality>();
			for (Functionality functionality : functionalities) {
				ProfileFunctionalityPK pk = new ProfileFunctionalityPK(functionality.getId(), profile.getId());
				ProfileFunctionality profileFunctionality = super.getDaoFactory().getProfileFunctionalityDAO().findById(pk, false, true);
				if (profileFunctionality == null) {
					profileFunctionality = this.persistProfileFunctionality(profile, functionality);
				}
				selectedFunctionalities.add(profileFunctionality);
			}
			List<ProfileFunctionality> actualProfileFunctionalities = super.getDaoFactory().getProfileFunctionalityDAO()
					.findByProfileId(profile.getId());
			this.listOperationDAO.process(
					actualProfileFunctionalities, selectedFunctionalities, tokenId);
		}
		this.log.debug("updateProfileFunc - END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Profile updateProfileZones(@NotNull @Min(value = 1L) Long profileId, 
			@NotNull List<Integer> selectedZones,
			@NotNull String tokenId) throws InformationException {
		this.log.debug("updateProfileZones - START");
		
		this.log.debug("updateProfileZones - END");
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Profile> updateRegisterState(@NotNull List<Profile> list,
			boolean newState, @NotNull String tokenId) 
					throws InformationException {
		this.log.debug("updateRegisterState - START");
		List<Profile> updatedList = new ArrayList<Profile>();
		if (list != null && !list.isEmpty()) {
			// TODO: VALIDATE THAT DO NOT INACTIVATE ADMINISTRATOR PROFILE
			BeanPropertyValueChangeClosure closure = new BeanPropertyValueChangeClosure(
					"state", newState);
			CollectionUtils.forAllDo(list, closure);
			for (Profile profile : list) {
				Profile updated = super.getDaoFactory().getProfileDAO().update(profile);
				updatedList.add(updated);
			}
		}
		this.log.debug("updateRegisterState - END");
		return updatedList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SelectionDTO> findByHomeId(@NotNull @Min(value = 1L) Long homeId, @NotNull String tokenId) {
		return super.getDaoFactory().getProfileDAO().findByHomeId(super.getSessionManager().findUserId(tokenId), homeId);
	}
	
	/**
	 * 
	 * @param profile
	 * @param functionality
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private ProfileFunctionality persistProfileFunctionality(Profile profile,
			Functionality functionality) {
		ProfileFunctionality profileFunctionality = new ProfileFunctionality(
				functionality.getId(), profile.getId());
		profileFunctionality.setAllowDelete(functionality.getHandleDeletions());
		profileFunctionality
				.setAllowInsert(functionality.getHandleInsertions());
		profileFunctionality.setAllowUpdate(functionality.getHandlesUpdates());
		profileFunctionality.setHourFrom(new Date());
		profileFunctionality.setHourOfHourFrom(0);
		profileFunctionality.setMinuteOfHourFrom(0);
		profileFunctionality.setHourUntil(new Date());
		profileFunctionality.setHourOfHourUntil(23);
		profileFunctionality.setMinuteOfHourUntil(59);
		return profileFunctionality;
	}
	
}
