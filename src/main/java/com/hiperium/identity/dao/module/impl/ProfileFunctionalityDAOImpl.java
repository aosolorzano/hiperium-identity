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
package com.hiperium.identity.dao.module.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hiperium.commons.client.exception.InformationException;
import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.identity.bo.utils.EnumNamedQuerySecurity;
import com.hiperium.identity.common.vo.ProfileFunctionalityVO;
import com.hiperium.identity.dao.GenericDAO;
import com.hiperium.identity.dao.module.ProfileFunctionalityDAO;
import com.hiperium.identity.model.security.ProfileFunctionality;
import com.hiperium.identity.model.security.ProfileFunctionalityPK;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProfileFunctionalityDAOImpl extends GenericDAO<ProfileFunctionality, ProfileFunctionalityPK> 
	implements ProfileFunctionalityDAO {
	
	/** The property QUERY. */
	private static final String QUERY = "SELECT NEW com.hiperium.commons.vo.ProfileFunctionalityVO(pf.pk.functionalityId, pf.hourFrom, pf.hourUntil) "
			+ "FROM  ProfileFunctionality pf JOIN pf.functionality f "
			+ "WHERE pf.pk.profileId = :profileId "
			+ "AND   :restURI LIKE CONCAT(f.restURI, '%')";
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/** The property entityManger. */
	@Inject
    private EntityManager entityManager;
	
	/**
	 * Component post contruct.
	 */
	@PostConstruct
	public void init() {
		super.setEntityManager(this.entityManager);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProfileFunctionality> findByProfileId(@NotNull @Min(value = 1L) Long profileId) throws InformationException {
		return super.createNamedQuery(EnumNamedQuerySecurity.PROFILE_FUNC_FIND_BY_PROFILE_ID.getName(), 
				ProfileFunctionality.class, true)
				.setParameter("profileId", profileId)
				.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileFunctionalityVO findByProfileAndURL(@NotNull String URL, @NotNull @Min(value = 1L) Long profileId) {
		ProfileFunctionalityVO profileFunctionality = null;
		try {
			profileFunctionality = super.createNamedQuery(
				EnumNamedQuerySecurity.PROFILE_FUNC_FIND_BY_PROFILE_AND_URL.getName(), 
				ProfileFunctionalityVO.class, true)
					.setParameter("profileId", profileId)
					.setParameter("url", URL)
					.getSingleResult();
		} catch (NoResultException e) {
			this.log.error("Error: No result to find profile functionality for: " + URL);
		}
		return profileFunctionality;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileFunctionalityVO findByProfileAndRestURI(@NotNull String restURI, @NotNull @Min(value = 1L) Long profileId) {
		ProfileFunctionalityVO profileFunctionality = null;
		try {
			// This query can not be configured in the security module query XML file for bad structured reasons.
			profileFunctionality = (ProfileFunctionalityVO) super.createQuery(QUERY, ProfileFunctionalityVO.class, true)
					.setParameter("profileId", profileId)
					.setParameter("restURI", restURI)
					.getSingleResult();
		} catch (NoResultException e) {
			this.log.error("Error: No result to find profile functionality for: " + restURI);
		}
		return profileFunctionality;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileFunctionality findById(ProfileFunctionalityPK id, boolean lock, boolean bypassCache) {
		return super.findById(id, lock, bypassCache);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileFunctionality update(ProfileFunctionality register) {
		return super.update(register);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(ProfileFunctionalityPK id) {
		ProfileFunctionality register = super.getReference(id);
		super.delete(register);
	}
}
