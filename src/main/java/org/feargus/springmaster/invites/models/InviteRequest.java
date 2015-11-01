package org.feargus.springmaster.invites.models;

public class InviteRequest {
	private String userEmail = null;
	private String projectName = null;
	
	public String getUserEmail() {return userEmail;}
	public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
	public String getProjectName() {return projectName;}
	public void setProjectName(String projectName) {this.projectName = projectName;}
}
