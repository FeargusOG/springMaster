package org.feargus.springmaster.users;

import java.util.Collection;

import javax.validation.constraints.Size;

import org.feargus.springmaster.utils.UtilVars;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = -8200771636764034343L;
    private Collection<? extends GrantedAuthority> userAuthorities;

    @Size(min = 2, max = 255)
    private String userNameEmail;

    @Size(min = 3, max = 255)
    private String userHandle;

    private transient String salt;
    private transient String saltedUserPassword;
    private transient String nonSaltedUserPassword;
    private int userId;
    private boolean userActive;
    private boolean userNonExpired;
    private boolean userNonLocked;
    private boolean userCredsNonExpired;

    public CustomUserDetails() {
	this.userActive = false;
	this.userNonExpired = true;
	this.userNonLocked = true;
	this.userCredsNonExpired = true;
    }

    /* Setters */
    public void setUserAuthorities(Collection<? extends GrantedAuthority> userAuthorities) {
	this.userAuthorities = userAuthorities;
    }

    public void setPassword(String userPassword) {
	this.saltedUserPassword = userPassword;
    }

    public void setNonSaltedUserPassword(String nonSaltedUserPassword) {
	this.nonSaltedUserPassword = nonSaltedUserPassword;
    }

    public void setSalt(String salt) {
	this.salt = salt;
    }

    public void setUsername(String userNameEmail) {
	this.setUserNameEmail(userNameEmail);
    }

    public void setUserNameEmail(String userNameEmail) {
	this.userNameEmail = userNameEmail;
    }

    public void setUserHandle(String userHandle) {
	this.userHandle = userHandle;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public void setUserActive(boolean userActive) {
	this.userActive = userActive;
    }

    public void setUserNonExpired(boolean userNonExpired) {
	this.userNonExpired = userNonExpired;
    }

    public void setUserNonLocked(boolean userNonLocked) {
	this.userNonLocked = userNonLocked;
    }

    public void setUserCredsNonExpired(boolean userCredsNonExpired) {
	this.userCredsNonExpired = userCredsNonExpired;
    }

    /* Getters */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return this.userAuthorities;
    }

    @Override
    public String getPassword() {
	return this.saltedUserPassword;
    }

    public String getNonSaltedUserPassword() {
	return nonSaltedUserPassword;
    }

    public String getSalt() {
	return salt;
    }

    @Override
    public String getUsername() {
	return this.getUserNameEmail();
    }

    public String getUserNameEmail() {
	return this.userNameEmail;
    }

    public String getUserHandle() {
	return userHandle;
    }

    public int getUserId() {
	return userId;
    }

    @Override
    public boolean isEnabled() {
	return this.userActive;
    }

    @Override
    public boolean isAccountNonExpired() {
	return this.userNonExpired; // true is not expired
    }

    @Override
    public boolean isAccountNonLocked() {
	return this.userNonLocked; // true is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return this.userCredsNonExpired; // true is not expired
    }

    /* General Functions */
    public String toString() {
	return "Member(" + UtilVars.PII_START + "Handle: " + this.userHandle + ", Email: "
		+ this.userNameEmail + UtilVars.PII_END + ")";
    }

}
