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
package com.hiperium.identity.common.utils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author Andres Solorzano
 * 
 */
public class HashMd5 implements Serializable {

	/** */
	private static final long serialVersionUID = -4411751762093229639L;
	/** */
	private MessageDigest md;
	/** */
	private StringBuffer sb;

	/**
	 * Default constructor.
	 */
	public HashMd5() {
		try {
			this.md = MessageDigest.getInstance("MD5");
			this.sb = new StringBuffer();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	public String hash(String source) {
		try {
			byte[] bytes = this.md.digest(source.getBytes("UTF-8"));
			return getString(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 */
	private String getString(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			String hex = Integer.toHexString((int) 0x00FF & b);
			if (hex.length() == 1) {
				this.sb.append("0");
			}
			this.sb.append(hex);
		}
		return this.sb.toString();
	}
}
