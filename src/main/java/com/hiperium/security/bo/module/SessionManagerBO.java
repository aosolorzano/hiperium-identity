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
package com.hiperium.security.bo.module;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import com.hiperium.security.common.vo.ProfileFunctionalityVO;

/**
 * 
 * @author Andres Solorzano
 * 
 */
@Local
public interface SessionManagerBO {

	/**
	 *
	 * @param URL
	 * @param tokenId
	 * @return
	 */
	ProfileFunctionalityVO findByProfileAndURL(@NotNull String URL,
			@NotNull String tokenId);

	/**
	 *
	 * @param restURI
	 * @param tokenId
	 * @return
	 */
	ProfileFunctionalityVO findByProfileAndRestURI(@NotNull String restURI,
			@NotNull String tokenId);

}
