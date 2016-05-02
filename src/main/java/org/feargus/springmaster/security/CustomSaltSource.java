package org.feargus.springmaster.security;

import org.feargus.springmaster.users.model.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomSaltSource implements SaltSource {
    private static final Logger log = LoggerFactory.getLogger(CustomSaltSource.class);
    private UserUtils userUtils;

    public CustomSaltSource() {
	this.userUtils = new UserUtils();
    }

    @Override
    public Object getSalt(UserDetails userDetails) {
	/* getUsername actually gets the email for the user */
	String userSalt = this.userUtils.selectSaltFromDB(userDetails.getUsername());
	log.info("\n\nHere is the user salt: " + userSalt + "\n\n");
	return userSalt;
    }

}
