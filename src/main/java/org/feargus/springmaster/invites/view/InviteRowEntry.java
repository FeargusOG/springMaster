package org.feargus.springmaster.invites.view;

import java.sql.Date;

public class InviteRowEntry {
    private String inviteToken = null;
    private String userEmail = null;
    private Date timeCreated = null;

    public InviteRowEntry() {
    };

    public InviteRowEntry(String token, String email, Date timeCrtd) {
	this.inviteToken = token;
	this.userEmail = email;
	this.timeCreated = timeCrtd;
    }

    public String getInviteToken() {
	return inviteToken;
    }

    public void setInviteToken(String inviteToken) {
	this.inviteToken = inviteToken;
    }

    public String getUserEmail() {
	return userEmail;
    }

    public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
    }

    public Date getTimeCreated() {
	return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
	this.timeCreated = timeCreated;
    }

}
