package org.feargus.springmaster.security.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomGrantedAuthRowMapper implements RowMapper<CustomGrantedAuthority> {

    @Override
    public CustomGrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
	return new CustomGrantedAuthority(rs.getString("role"));
    }
}
