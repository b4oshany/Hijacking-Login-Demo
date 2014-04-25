package util;

import java.util.Random;

public class StringGenerator {
	
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
	
	public static String generateRandomString(int min_length, int max_length, Mode mode) throws Exception {
		StringBuffer buffer = new StringBuffer();
		String characters = getCharacters(mode);
		int charactersLength = characters.length();
		Random rand = new Random();
		int length = rand.nextInt(max_length) + min_length;
		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}
}
