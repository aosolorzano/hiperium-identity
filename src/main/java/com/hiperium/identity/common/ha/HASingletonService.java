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
package com.hiperium.identity.common.ha;

import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

import com.hiperium.commons.services.logger.HiperiumLogger;
import com.hiperium.identity.common.bean.SessionManagerBean;

/**
 *
 * @author Andres Solorzano
 * @version 1.0
 */
public class HASingletonService implements Service<SessionManagerBean> {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(HASingletonService.class);
	
	/** The property SINGLETON_SERVICE_NAME. */
	public static final ServiceName SINGLETON_SERVICE_NAME = ServiceName.JBOSS.append("ha", "singleton", "SessionManagerBean");
	
	/** The property started. */
	private final AtomicBoolean started = new AtomicBoolean(false);
	
	/** The property sessionManagerBean. */
	private SessionManagerBean sessionManagerBean;

	 /**
     * @return the name of the server node
     */
	@Override
	public SessionManagerBean getValue() throws IllegalStateException, IllegalArgumentException {
		if(!this.started.get()) {
			throw new IllegalStateException("The service '" + this.getClass().getName() + "' is not ready.");
	    }
        return this.sessionManagerBean;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(StartContext context) throws StartException {
		if (!this.started.compareAndSet(false, true)) {
            throw new StartException("The service " + this.getClass().getName() + " is already active.");
        }
        LOGGER.info("Start HA Singleton Service: '" + this.getClass().getName() + "'");
        this.sessionManagerBean = new SessionManagerBean();
        this.sessionManagerBean.initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(StopContext context) {
		if (!this.started.compareAndSet(true, false)) {
            LOGGER.info("The service '" + this.getClass().getName() + "' is not active.");
        }
	}

}
