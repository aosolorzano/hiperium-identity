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
package com.hiperium.security.bo.generic;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.hiperium.security.common.SessionManagerBean;
import com.hiperium.security.dao.factory.DataAccessFactory;

/**
 * This class is an generalization of business objects.
 *
 * @author Andres Solorzano
 *
 */
public class GenericBO {

    /** The property daoFactory. */
    @Inject
    private DataAccessFactory daoFactory;

    /** The property sessionManager. */
    @EJB
    private SessionManagerBean sessionManager;

    /**
     *
     */
    public GenericBO() {
        // Nothing to do.
    }

    /**
     *
     * @return
     */
    public DataAccessFactory getDaoFactory() {
        return daoFactory;
    }

    /**
     *
     * @return
     */
    public SessionManagerBean getSessionManager() {
        return sessionManager;
    }
}
