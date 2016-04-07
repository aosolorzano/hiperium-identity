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
package com.hiperium.security.dao.list;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface ListOperationDAO<T, ID extends Serializable> {

	/**
	 * This method realizes collection objects operations against the data base.
	 * 
	 * @param actualList
	 * @param newList
	 * @param tokenId
	 */
	void process(@NotNull List<T> actualList, @NotNull List<T> newList,
			@NotNull String tokenId);
}
