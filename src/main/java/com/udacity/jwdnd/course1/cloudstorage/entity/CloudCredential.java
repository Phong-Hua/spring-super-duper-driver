package com.udacity.jwdnd.course1.cloudstorage.entity;

public class CloudCredential {

	private int credentialId;
	private String url;
	private String username;
	private String password;
	private int userId;
	private String encodedKey;
	
	public CloudCredential() {
	}
	
	public CloudCredential(String url, String username, String password, int userId) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.userId = userId;
	}

	public int getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(int credentialId) {
		this.credentialId = credentialId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	@Override
	public String toString() {
		return "CloudCredential [credentialId=" + credentialId + ", url=" + url + ", username=" + username
				+ ", password=" + password + ", userId=" + userId + ", encodedKey=" + encodedKey + "]";
	}
}



