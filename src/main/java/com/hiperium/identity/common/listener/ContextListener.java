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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hiperium.identity.common.ConfigurationBean;

/**
 * Class that manages application context objects. Here we need to store the 
 * user and home session maps for a high availability purposes.
 * 
 * @author Andres Solorzano
 * @version 1.0
 * 
 */
public class ContextListener implements ServletContextListener {

	/** The LOGGER property for logger messages. */
	private static final Logger LOGGER = Logger.getLogger(ContextListener.class);
	
	/** The property context. */
	private ServletContext context;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		LOGGER.debug("contextInitialized - START");
		HashMap<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
		this.context = servletContextEvent.getServletContext();
		this.context.setAttribute(ConfigurationBean.SESSION_MAP_NAME, sessionMap);
		LOGGER.debug("contextInitialized - END");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		LOGGER.debug("contextDestroyed - START");
		this.context = servletContextEvent.getServletContext();
		this.context.removeAttribute(ConfigurationBean.SESSION_MAP_NAME);
		LOGGER.debug("contextDestroyed - END");
	}
}
