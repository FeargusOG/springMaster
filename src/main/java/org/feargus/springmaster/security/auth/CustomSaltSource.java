package org.feargus.springmaster.security.auth;

import org.feargus.springmaster.users.UserAccUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomSaltSource implements SaltSource {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(CustomSaltSource.class);
    private UserAccUtils userUtils;

    public CustomSaltSource() {
	this.userUtils = new UserAccUtils();
    }

    @Override
    public Object getSalt(UserDetails userDetails) {

	/* getUsername actually gets the email for the user */
	String userSalt = this.userUtils.selectSaltFromDB(userDetails.getUsername());
	// log.info("\n\nHere is the user salt: " + userSalt + "\n\n");
	return userSalt;
    }

}
