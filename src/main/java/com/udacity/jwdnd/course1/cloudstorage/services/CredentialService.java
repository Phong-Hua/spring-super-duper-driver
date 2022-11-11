package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudCredential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;

@Service
public class CredentialService {
	
	private CredentialMapper credentialMapper;
	private EncryptionService encryptionService;
	
	
	public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
		this.credentialMapper = credentialMapper;
		this.encryptionService = encryptionService;
	}
	
	public List<CloudCredential> getCredentials() {
		return credentialMapper.getCredentials();
	}
	
	public int addOrUpdateCredential(CloudCredential credential) {
		String plainPassword = credential.getPassword();
		String encodedKey = generateEncodedKey();
		String encryptedPassword = encryptionService.encryptValue(plainPassword, encodedKey);
		credential.setPassword(encryptedPassword);
		credential.setEncodedKey(encodedKey);
		return credentialMapper.insertCredential(credential);
	}
	
	private String generateEncodedKey() {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		String encodedKey = Base64.getEncoder().encodeToString(key);
		return encodedKey;
	}
}
