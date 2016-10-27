package org.asu.ss.model;

import javax.persistence.Entity;

@Entity
public class LoginCredentials {

	String username;
	String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginCredentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public LoginCredentials() {
		super();
	}
	@Override
	public String toString() {
		return "LoginCredentials [username=" + username + ", password=" + password + "]";
	}
	
	
}
