package org.feargus.springmaster.security.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringHasher {
    private final String HASH_ALGORITHM = "SHA-256";
    private static final Logger log = LoggerFactory.getLogger(StringHasher.class);

    public String hashThisString(String stringToHash) throws NoSuchAlgorithmException {
	String hashedOutput = null;
	try {
	    MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
	    byte[] bytes = md.digest(stringToHash.getBytes());
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < bytes.length; i++) {
		sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    hashedOutput = sb.toString();
	} catch (NoSuchAlgorithmException e) {
	    log.info(e.getMessage());
	    throw e;
	}

	return hashedOutput;
    }
}
