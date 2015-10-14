package com.jellopy;

import java.security.MessageDigest;

/**
 * A very helpful method.
 *
 * @author Jellopy
 */
public class Helper {

	/**
	 * Turn first character of string to uppercase otherwise lowercase.
	 *
	 * @param string
	 * @return String was converted.
	 */
	public static String ucfirst(String string) {
		return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
	}

	/**
	 * md5 hash string generator.
	 *
	 * @param message
	 * @return String was encrypt by md5.
	 */
    public static String md5(String message){
    	String digest = null;
    	try {
    		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    		byte[] hash = messageDigest.digest(message.getBytes("UTF-8"));

    		StringBuilder stringBuilder = new StringBuilder(2 * hash.length);
    		for(byte each : hash){
    			stringBuilder.append(String.format("%02x", each & 0xff));
    		}
    		digest = stringBuilder.toString();
    	} catch (Exception error) {
    		error.printStackTrace();
    	}
    	return digest;
    }
}
