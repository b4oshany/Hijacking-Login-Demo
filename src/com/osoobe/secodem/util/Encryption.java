package com.osoobe.secodem.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encryption Class is a predefined encryption algorithm that is use to encrypt data in MD5 and SHA1
 * @author Oshane Bailey
 *
 */
public class Encryption {
	String entype;

	public static enum TYPE {
		MD5("MD5"), SHA("SHA");
		private String value;

		private TYPE(String value) {
			this.value = value;
		}
	}

	/**
	 * The encrypt function is used as a cryptographic function to encrypt data or string in MD5 or SHA1 format
	 * @param type - takes in enumerated type define with the following value, TYPE.MD5, TYPE.SHA
	 * @param str - the value or string to be encrypted
	 * @return a string that is encrypted with the given encryption algorithm.
	 */
	public static String encrypt(TYPE type, String str) {
		// TODO Auto-generated constructor stub
		try {
			MessageDigest md = MessageDigest.getInstance(type.value);
			md.update(str.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
