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
		List<CloudCredential> credentials = credentialMapper.getCredentials();
		
		for(CloudCredential cre : credentials) {
			cre.setPlainPassword(encryptionService.decryptValue(cre.getEncryptedPassword(), 
					cre.getEncodedKey()));
		}
		return credentials;
	}
	
	public int addOrUpdateCredential(CloudCredential credential) {
		String plainPassword = credential.getPlainPassword();
		String encodedKey = generateEncodedKey();
		String encryptedPassword = encryptionService.encryptValue(plainPassword, encodedKey);
		credential.setPlainPassword(null);
		credential.setEncryptedPassword(encryptedPassword);
		credential.setEncodedKey(encodedKey);
		if (credential.getCredentialId() < 1) {
			return credentialMapper.insertCredential(credential);
		} else {
			return credentialMapper.updateCredential(credential);
		}
	}
	
	private String generateEncodedKey() {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		String encodedKey = Base64.getEncoder().encodeToString(key);
		return encodedKey;
	}
}
