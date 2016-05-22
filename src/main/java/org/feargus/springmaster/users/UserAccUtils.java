package org.feargus.springmaster.users;

import java.security.NoSuchAlgorithmException;

import org.feargus.springmaster.db.PostgresqlDataSource;
import org.feargus.springmaster.db.SqlStmts;
import org.feargus.springmaster.security.crypto.StringHasher;
import org.feargus.springmaster.security.crypto.UniqueTokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserAccUtils {
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_DBA = "DBA";

    private JdbcTemplate jdbcTemplate;
    private StringHasher stringHasher;
    private static final Logger log = LoggerFactory.getLogger(UserAccUtils.class);

    public UserAccUtils() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
	this.stringHasher = new StringHasher();
    }

    public String hashUserPassword(String salt, String pswrd) throws NoSuchAlgorithmException {
	return stringHasher.hashThisString(combineSalt(salt, pswrd));
    }

    /*
     * We will hash the user email for sending an acc confirmation link. This is
     * because if we used the raw email as a parameter in the link, then a user
     * could create accounts for other users and then confirm them themselves by
     * adding this email parameter to the url. By hashing it, they would need
     * access to the salt and hashing algorithm to mimic it.
     */
    public String hashUserEmail(String salt, String userEmail) throws NoSuchAlgorithmException {
	return stringHasher.hashThisString(combineSalt(salt, userEmail));
    }

    public boolean compareHashedPassword(String userEmail, String inputHashedPswrd)
	    throws DataAccessException, NoSuchAlgorithmException {
	String dbHashedPswrd = selectPasswordFromDB(userEmail);

	if (inputHashedPswrd.equals(dbHashedPswrd)) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean compareHashedEmail(String userEmail, String inputHashedEmail)
	    throws NoSuchAlgorithmException {

	String dbSalt = selectSaltFromDB(userEmail);
	String hashedEmail = stringHasher.hashThisString(combineSalt(dbSalt, userEmail));

	if (inputHashedEmail.equals(hashedEmail)) {
	    return true;
	} else {
	    return false;
	}
    }

    public void activateUser(String userEmail) {
	jdbcTemplate.update(SqlStmts.UPDATE_IS_ACTIVE_SQL, userEmail);
    }

    public boolean selectIsNonExpiredFromDB(String userEmail) {
	String isActive = (String) this.jdbcTemplate.queryForObject(SqlStmts.SELECT_IS_NONEXPIRED_SQL,
		new Object[] { userEmail }, String.class);

	if (isActive.equals("t")) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean selectIsCredsNonExpiredFromDB(String userEmail) {
	String isActive = (String) this.jdbcTemplate.queryForObject(SqlStmts.SELECT_IS_CREDSNONEXPIRED_SQL,
		new Object[] { userEmail }, String.class);

	if (isActive.equals("t")) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean selectIsNonLockedFromDB(String userEmail) {
	String isActive = (String) this.jdbcTemplate.queryForObject(SqlStmts.SELECT_IS_NONLOCKED_SQL,
		new Object[] { userEmail }, String.class);

	if (isActive.equals("t")) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean selectIsActiveFromDB(String userEmail) throws DataAccessException {
	String isActive = (String) this.jdbcTemplate.queryForObject(SqlStmts.SELECT_IS_ACTIVE_SQL,
		new Object[] { userEmail }, String.class);

	if (isActive.equals("t")) {
	    return true;
	} else {
	    return false;
	}
    }

    public String selectPasswordFromDB(String userEmail) throws DataAccessException {
	return (String) this.jdbcTemplate.queryForObject(SqlStmts.SELECT_PSWRD_SQL,
		new Object[] { userEmail }, String.class);
    }

    public String selectSaltFromDB(String userEmail) throws DataAccessException {
	return (String) this.jdbcTemplate.queryForObject(SqlStmts.SELECT_SALT_SQL,
		new Object[] { userEmail }, String.class);
    }

    private String combineSalt(String salt, String inputString) {
	return salt + inputString;
    }

    public String generateSalt() {
	return new UniqueTokenGenerator().getUniqueToken();
    }
}