package org.pan.voucher.utils;

import java.security.MessageDigest;


/**
 * Encryptor service for one way hash encryption
 *
 * @author Pance.Isajeski
 */
public class EncryptorService {

    public static String encryptUsingOneWayHash(String plainText) {

    	MessageDigest md = null;
    	try {  
    		md = MessageDigest.getInstance("SHA");
    	} catch (Exception exception) {
    		throw new RuntimeException(exception);
    	}
    	md.update(plainText.getBytes());
    	byte raw[] = md.digest();
    	String hash = Base64.encodeToString(raw, false);
    	return hash;   	

    }
}
