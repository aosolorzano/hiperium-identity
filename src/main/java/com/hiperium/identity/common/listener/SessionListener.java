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
package com.hiperium.identity.common.listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.hiperium.identity.common.ConfigurationBean;

/**
 * This class represents the HTTP Session Listener to be used by the JSF
 * application.
 * 
 * @author Andres Solorzano
 * @version 1.0
 * 
 */
public class SessionListener implements HttpSessionListener {

	/** The LOGGER property for logger messages. */
	private static final Logger LOGGER = Logger.getLogger(SessionListener.class);
	
	/**
	 * {@inheritDoc}
	 */
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		if (session.isNew()) {
			LOGGER.info("Session is NEW");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent se) {
		LOGGER.debug("sessionDestroyed - START");
		ServletContext context = se.getSession().getServletContext();
		HashMap<String, HttpSession> sesiones = (HashMap<String, HttpSession>) context.getAttribute(ConfigurationBean.SESSION_MAP_NAME);
		sesiones.remove(se.getSession().getId());
		context.setAttribute(ConfigurationBean.SESSION_MAP_NAME, sesiones);
		LOGGER.debug("sessionDestroyed - END");
	}
}
