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
package com.hiperium.identity.dao.list.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import com.hiperium.identity.dao.list.ListOperationDAO;


/**
 * This class realizes collection objects operations against the data base.
 * 
 * @author Andres Solorzano
 */
@Stateless
public class ListOperationDAOImpl<T, ID extends Serializable> implements ListOperationDAO<T, ID> {
	
	/** The property entityManger. */
	@Inject
    private EntityManager entityManager;
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void process(@NotNull List<T> actualList, 
			@NotNull List<T> newList, 
			@NotNull String tokenId) {
		Collection<T> toUpdateOriginal = CollectionUtils.intersection(actualList, newList);
        Collection<T> toUpdateNew = CollectionUtils.intersection(newList, actualList);
        Collection<T> delete = CollectionUtils.subtract(actualList, newList);
        for (T entity : delete) {
        	this.entityManager.remove(entity);
		}
        Collection<T> insert = CollectionUtils.subtract(newList, actualList);
        for (T entity : insert) {
        	this.entityManager.persist(entity);
		}
        T[] updatedOriginalList = (T[])toUpdateOriginal.toArray();
        T[] updatedNewList = (T[])toUpdateNew.toArray();
        for (int i=0; i < updatedOriginalList.length; i++) {
        	boolean update = false;
            try {
            	Map<String, String> originalProperties = BeanUtils.describe(updatedOriginalList[i]);
            	Map<String, String> newProperties = BeanUtils.describe(updatedNewList[i]);
            	Set<String> properties = originalProperties.keySet();
            	for (String string : properties) {
    				if (!(originalProperties.get(string) == null && newProperties.get(string) == null) 
    						&& !originalProperties.get(string).equals(newProperties.get(string))) {
    					update = true;
    					break;
    				}
    			}
    		} catch (Exception e) {
    			update = true;
    		} 
    		if (update) {
    			this.entityManager.merge(updatedNewList[i]);
    		} 
		}
	}
}
