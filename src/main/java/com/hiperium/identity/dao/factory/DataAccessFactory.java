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
package com.hiperium.identity.dao.factory;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

import com.hiperium.identity.dao.module.ApplicationUserDAO;
import com.hiperium.identity.dao.module.HomeDAO;
import com.hiperium.identity.dao.module.HomeGatewayDAO;
import com.hiperium.identity.dao.module.ProfileDAO;
import com.hiperium.identity.dao.module.ProfileFunctionalityDAO;
import com.hiperium.identity.dao.module.UserDAO;
import com.hiperium.identity.dao.module.UserHomeDAO;

/**
 * Abstract Factory class that contains references to all DAO objects.
 * 
 * @author Andres Solorzano
 *
 */
@ApplicationScoped
public class DataAccessFactory {

	/** The property applicationUserDAO */
	@EJB
	private ApplicationUserDAO applicationUserDAO;

	/** The property homeDAO */
	@EJB
	private HomeDAO homeDAO;

	/** The property profileDAO */
	@EJB
	private ProfileDAO profileDAO;

	/** The property profileFunctionalityDAO */
	@EJB
	private ProfileFunctionalityDAO profileFunctionalityDAO;

	/** The property userDAO */
	@EJB
	private UserDAO userDAO;

	/** The property userHomeDAO */
	@EJB
	private UserHomeDAO userHomeDAO;

	/** The property homeGatewayDAO */
	@EJB
	private HomeGatewayDAO homeGatewayDAO;

	/**
	 * Class constructor.
	 */
	public DataAccessFactory() {
		// Nothing to do
	}

	/**
	 *
	 * @return
	 */
	public ApplicationUserDAO getApplicationUserDAO() {
		return applicationUserDAO;
	}

	/**
	 *
	 * @return
	 */
	public HomeDAO getHomeDAO() {
		return homeDAO;
	}

	/**
	 *
	 * @return
	 */
	public ProfileDAO getProfileDAO() {
		return profileDAO;
	}

	/**
	 *
	 * @return
	 */
	public ProfileFunctionalityDAO getProfileFunctionalityDAO() {
		return profileFunctionalityDAO;
	}

	/**
	 *
	 * @return
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 *
	 * @return
	 */
	public UserHomeDAO getUserHomeDAO() {
		return userHomeDAO;
	}

	/**
	 * 
	 * @return
	 */
	public HomeGatewayDAO getHomeGatewayDAO() {
		return homeGatewayDAO;
	}
}
