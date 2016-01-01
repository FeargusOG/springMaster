package org.feargus.springmaster.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

/*
 * This code is from http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
 */
public class UniqueTokenGenerator {
    private static SecureRandom random = new SecureRandom();

    public String getUniqueToken() {
	return new BigInteger(130, random).toString(32);
    }
}
