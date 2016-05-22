package org.feargus.springmaster.db;

public class SqlStmts {

    /* members table */
    public static final String INSERT_USER_SQL = "INSERT INTO members(userHandle, userNameEmail, salt, pswrd, "
	    + "accActive, accNonExpired, accNonLocked, credNonExpired) VALUES (?,?,?,?,?,?,?,?)";
    public static final String SELECT_SALT_SQL = "SELECT salt from members WHERE userNameEmail= ?";
    public static final String SELECT_PSWRD_SQL = "SELECT pswrd from members WHERE userNameEmail= ?";
    public static final String SELECT_IS_ACTIVE_SQL = "SELECT accActive from members WHERE userNameEmail= ?";
    public static final String SELECT_IS_NONEXPIRED_SQL = "SELECT accNonExpired from members WHERE userNameEmail= ?";
    public static final String SELECT_IS_CREDSNONEXPIRED_SQL = "SELECT userCredsNonExpired from members WHERE userNameEmail= ?";
    public static final String SELECT_IS_NONLOCKED_SQL = "SELECT accNonLocked from members WHERE userNameEmail= ?";
    public static final String UPDATE_IS_ACTIVE_SQL = "UPDATE members SET accActive='t' WHERE userNameEmail=?";

    /* members_roles table */
    public static final String INSERT_ROLE_SQL = "INSERT INTO members_roles(userEmail, role) VALUES (?,?)";
}
