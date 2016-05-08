package org.feargus.springmaster.security.crypto;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@SuppressWarnings("deprecation")
// The un-deprecated version does not allow custom salt
public class CustomPasswordEncoder implements PasswordEncoder {
    private StringHasher stringHasher;
    private static final Logger log = LoggerFactory.getLogger(CustomPasswordEncoder.class);

    public CustomPasswordEncoder() {
	this.stringHasher = new StringHasher();
    }

    @Override
    public String encodePassword(String rawPass, Object salt) {
	String hashedPswr = null;
	try {
	    hashedPswr = stringHasher.hashThisString((String) salt + rawPass);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	return hashedPswr;
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
	String encodedPswr = encodePassword(rawPass, salt);
	log.info("\n\n\tWe are comparing the passwords....\n\n");
	if (encodedPswr.equals(encPass)) {
	    return true;
	} else {
	    return false;
	}

    }
}
