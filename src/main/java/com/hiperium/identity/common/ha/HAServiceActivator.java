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

import org.jboss.msc.service.DelegatingServiceContainer;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistryException;
import org.wildfly.clustering.singleton.SingletonServiceBuilderFactory;
import org.wildfly.clustering.singleton.SingletonServiceName;
import org.wildfly.clustering.singleton.election.NamePreference;
import org.wildfly.clustering.singleton.election.PreferredSingletonElectionPolicy;
import org.wildfly.clustering.singleton.election.SimpleSingletonElectionPolicy;

import com.hiperium.common.services.logger.HiperiumLogger;

/**
 *
 * @author Andres Solorzano
 * @version 1.0
 */
public class HAServiceActivator implements ServiceActivator {

	/** The LOGGER property for logger messages. */
	private static final HiperiumLogger LOGGER = HiperiumLogger.getLogger(HAServiceActivator.class);
	
	/** The property CONTAINER_NAME. */
	private static final String CONTAINER_NAME = "server";
	/** The property CACHE_NAME. */
	private static final String CACHE_NAME = "default";
	  
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activate(ServiceActivatorContext context) throws ServiceRegistryException {
		LOGGER.info("HAService will be installed!");
		HASingletonService service = new HASingletonService();
        ServiceName factoryServiceName = SingletonServiceName.BUILDER.getServiceName(CONTAINER_NAME, CACHE_NAME);
        ServiceController<?> factoryService = context.getServiceRegistry().getRequiredService(factoryServiceName);
        SingletonServiceBuilderFactory factory = (SingletonServiceBuilderFactory) factoryService.getValue();
        factory.createSingletonServiceBuilder(HASingletonService.SINGLETON_SERVICE_NAME, service)
            /*
             * The NamePreference is a combination of the node name (-Djboss.node.name) and the name of
             * the configured cache "singleton". If there is more than 1 node, it is possible to add more than
             * one name and the election will use the first available node in that list.
             *   - To pass a chain of election policies to the singleton and tell JGroups to run the
             *     singleton on a node with a particular name, uncomment the first line  and
             *     comment the second line below.
             *   - To pass a list of more than one node, comment the first line and uncomment the second line below.
             */
             //.electionPolicy(new PreferredSingletonElectionPolicy(new SimpleSingletonElectionPolicy(), new NamePreference("identity-node1/singleton")))
            .electionPolicy(new PreferredSingletonElectionPolicy(new SimpleSingletonElectionPolicy(), 
            		new NamePreference("identity-node1/singleton"), 
            		new NamePreference("identity-node2/singleton")))
            .build(new DelegatingServiceContainer(context.getServiceTarget(), context.getServiceRegistry()))
            .setInitialMode(ServiceController.Mode.ACTIVE)
            .install();
	}

}
