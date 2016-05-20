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

import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.msc.service.ServiceController;

import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.commons.services.model.SessionRegister;
import com.hiperium.commons.services.vo.UserSessionVO;
import com.hiperium.identity.bo.module.SessionManagerBO;
import com.hiperium.identity.common.bean.SessionManagerBean;
import com.hiperium.identity.common.ha.HASingletonService;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Stateless
public class SessionManagerBOImpl implements SessionManagerBO {
	
	/** The property log. */
	@Inject
	private HiperiumLogger log;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUserSessionRegister(SessionRegister sessionRegister) {
		this.log.debug("addUserSessionRegister - BEGIN");
		SessionManagerBean managerBean = this.getSessionManagerBean();
		if(managerBean != null) {
			managerBean.addUserSessionRegister(sessionRegister);
		}
		this.log.debug("addUserSessionRegister - END");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addHomeSessionRegister(SessionRegister sessionRegister) {
		this.log.debug("addHomeSessionRegister - BEGIN");
		SessionManagerBean managerBean = this.getSessionManagerBean();
		if(managerBean != null) {
			managerBean.addHomeSessionRegister(sessionRegister);
		}
		this.log.debug("addHomeSessionRegister - END");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserSessionRegister(SessionRegister sessionRegister) {
		this.log.debug("updateUserSessionRegister - BEGIN");
		SessionManagerBean managerBean = this.getSessionManagerBean();
		if(managerBean != null) {
			managerBean.updateUserSessionRegister(sessionRegister);
		}
		this.log.debug("updateUserSessionRegister - END");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SessionRegister delete(String tokenId) {
		this.log.debug("delete - START");
		SessionManagerBean managerBean = this.getSessionManagerBean();
		if(managerBean != null) {
			return managerBean.delete(tokenId);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUserLoggedIn(String tokenId) {
		this.log.debug("isUserLoggedIn - START");
		SessionManagerBean managerBean = this.getSessionManagerBean();
		if(managerBean != null) {
			return managerBean.isUserLoggedIn(tokenId);
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isHomeLoggedIn(String tokenId) {
		this.log.debug("isHomeLoggedIn - START");
		SessionManagerBean managerBean = this.getSessionManagerBean();
		if(managerBean != null) {
			return managerBean.isHomeLoggedIn(tokenId);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SessionRegister findUserSessionRegister(String tokenId) {
		this.log.debug("findUserSessionRegister - START");
		SessionManagerBean managerBean = this.getSessionManagerBean();
		if(managerBean != null) {
			return managerBean.findUserSessionRegister(tokenId);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserSessionVO findUserSessionVO(String tokenId) {
		this.log.debug("findUserSessionVO - START");
		SessionManagerBean managerBean = this.getSessionManagerBean();
		if(managerBean != null) {
			return managerBean.findUserSessionVO(tokenId);
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	private SessionManagerBean getSessionManagerBean() {
		this.log.debug("getSessionManagerBean() - BEGIN");
        ServiceController<?> service = CurrentServiceContainer.getServiceContainer().getService(HASingletonService.SINGLETON_SERVICE_NAME);
        SessionManagerBean managerBean = null;
        if (service == null) {
        	this.log.error("SERVICE '" + HASingletonService.SINGLETON_SERVICE_NAME + "' NOT FOUND!");
        } else {
        	managerBean = (SessionManagerBean) service.getValue();
        }
        this.log.debug("getSessionManagerBean() - END");
        return managerBean;
	}
}
