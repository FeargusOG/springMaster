package org.feargus.springmaster.invites.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InviteRowEntryMapper implements RowMapper<InviteRowEntry> {
	
	public InviteRowEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
		InviteRowEntry invRowEntry = new InviteRowEntry();
		invRowEntry.setInviteToken(rs.getString("token"));
		invRowEntry.setUserEmail(rs.getString("useremail"));
		invRowEntry.setTimeCreated(rs.getDate("createtime"));
		
		return invRowEntry;
	}
}
