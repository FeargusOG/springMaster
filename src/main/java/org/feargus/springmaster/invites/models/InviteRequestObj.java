package org.feargus.springmaster.invites.models;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InviteRequestObj {
    private static final Logger log = LoggerFactory.getLogger(InviteRequestObj.class);
    private ArrayList<String> projectList = null;
    private String userName = null;
    private String userEmail = null;
    private String projectName = null;

    public InviteRequestObj() {
	this.projectList = new ArrayList<String>();
	this.populateProjectList();
    }

    public void setUserName(String userName) {
	if (userName != null) {
	    this.userName = userName;
	} else {
	    log.info("NULL username provided for project request!");
	    throw new IllegalArgumentException("Username cannot be empty!");
	}
    }

    public void setUserEmail(String userEmail) {
	if (userEmail != null) {
	    this.userEmail = userEmail;
	} else {
	    log.info("NULL email provided for project request!");
	    throw new IllegalArgumentException("Email address cannot be empty!");
	}
    }

    public void setProjectName(String projectName) throws IllegalArgumentException {

	if (projectList.contains(projectName)) {
	    this.projectName = projectName;
	} else {
	    log.info("Illegal project requested: " + projectName);
	    throw new IllegalArgumentException(projectName + " is not a valid project.");
	}
    }

    private void populateProjectList() {
	this.projectList.add("Site");
	this.projectList.add("Tester");
    }

    public String getProjectName() {
	return projectName;
    }

    public String getUserEmail() {
	return userEmail;
    }

    public String getUserName() {
	return userName;
    }
}
