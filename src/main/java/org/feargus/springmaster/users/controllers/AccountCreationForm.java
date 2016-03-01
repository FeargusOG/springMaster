package org.feargus.springmaster.users.controllers;

import javax.validation.constraints.Size;

public class AccountCreationForm {

    @Size(min = 2, max = 255)
    private String name;

    @Size(min = 3, max = 255)
    private String email;

    @Size(min = 3, max = 40)
    private String password;

    private String salt;

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getSalt() {
	return salt;
    }

    public void setSalt(String salt) {
	this.salt = salt;
    }

    public String toString() {
	return "Person(Name: " + this.name + ", Email: " + this.email + ")";
    }
}