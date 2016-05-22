package org.feargus.springmaster.users;

import org.feargus.springmaster.db.PostgresqlDataSource;
import org.feargus.springmaster.db.SqlStmts;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserRoleCreator {

    private JdbcTemplate jdbcTemplate;

    public UserRoleCreator() {
	PostgresqlDataSource psqlDataSource = new PostgresqlDataSource();
	this.jdbcTemplate = new JdbcTemplate(psqlDataSource.getDefaultDataSource());
    }

    public void addRoleForUser(String userEmail, String role) throws DataAccessException {
	jdbcTemplate.update(SqlStmts.INSERT_ROLE_SQL, userEmail, role);
    }
}
