package com.osoobe.secodem.util;

import java.util.Random;

public class StringGenerator {
	
	public static void main(String[] ar){
		try {
			Random rand = new Random();
			System.out.println( generateRandomString(9,10, Mode.ALPHANUMERIC));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static enum Mode {
	    ALPHA, ALPHANUMERIC, NUMERIC 
	}
	
	public static String characters;
	
	private static String getCharacters(Mode mode){
		switch(mode){		
		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		
		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;
	
		case NUMERIC:
			characters = "1234567890";
		    break;
	    default:
	    	characters = null;
	    	break;
		}
		return characters;
	}
	
	public static String generateRandomString(int length, Mode mode) throws Exception {
		StringBuffer buffer = new StringBuffer();
		String characters = getCharacters(mode);
		int charactersLength = characters.length();
		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param min_length
	 * @param max_length
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public static String generateRandomString(int min_length, int max_length, Mode mode) throws Exception {
		StringBuffer buffer = new StringBuffer();
		String characters = getCharacters(mode);
		int charactersLength = characters.length();
		int length = min_length + (int)(Math.random() * ((max_length - min_length) + 1));
		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}
}
