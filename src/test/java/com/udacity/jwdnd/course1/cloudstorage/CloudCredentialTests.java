package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CloudCredentialTests extends AbstractTests {

	/**
	 * Click credential tab from home page
	 */
	private void doClickCredentialTab() {

		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("nav-credentials-tab")));
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));

		credentialTab.click();
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("add-credential-button")));
	}

	/**
	 * Assume the credential modal is open, Input credential then hit submit button.
	 * 
	 * @param url
	 * @param username
	 * @param password
	 */
	private void doInputCredentialThenSubmit(String url, String username, String password) {
		// Input url, username, and password
		WebElement urlElement = driver.findElement(By.id("input-credential-url"));
		urlElement.clear();
		urlElement.sendKeys(url);

		WebElement usernameElement = driver.findElement(By.id("input-credential-username"));
		usernameElement.clear();
		usernameElement.sendKeys(username);

		WebElement passwordElement = driver.findElement(By.id("input-credential-password"));
		passwordElement.clear();
		passwordElement.sendKeys(url);

		WebElement submitButton = driver.findElement(By.id("credential-submit-2"));
		submitButton.click();
	}

	/**
	 * Click the add credential button at credential tab.
	 */
	private void doClickAddCredentialButton() {
		// click add note button
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("add-credential-button")));
		WebElement addCreButton = driver.findElement(By.id("add-credential-button"));
		addCreButton.click();

		// wait for the form show up
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-form")));

	}

	/**
	 * Click Credential Tab and Add Credential when at home page.
	 */
	private void doAddCredentialAtHomePage(String url, String username, String password) {

		doClickCredentialTab();
		doClickAddCredentialButton();
		doInputCredentialThenSubmit(url, username, password);
	}

	/**
	 * Find the credential with url in the credential table.
	 * 
	 * @param url
	 * @return
	 */
	private WebElement doFindRowCredentialContainsUrl(String url) {
		WebElement tbody = driver.findElement(By.xpath("//table[@id='credential-table']//tbody"));
		WebElement targetRow = tbody.findElement(By.xpath("//following::tr[th//text()[contains(., '" + url + "')]]"));
		return targetRow;
	}

	/**
	 * Signup with username and password, login with that username and password.
	 * Create credential
	 * @param username
	 * @param password
	 * @param creUrl
	 * @param creUsername
	 * @param crePassword
	 */
	private void doSignupLoginAndAddCredential(String username, String password, String creUrl, String creUsername, String crePassword) {
		
		doMockSignUp("credential", "test", username, password);
		doLogIn(username, password);

		doAddCredentialAtHomePage(creUrl, creUsername, crePassword);

//		// Verify success in result page
		doClickHomeWhenResultSuccess();

		// Verify the note display
		doClickCredentialTab();
	}
	
	@Test
	public void shouldAddCredentialSuccess() {

		String username = "credential01", password = "pass";
		String creUrl = "credential01.com", creUsername = "credential01", crePassword = "credential01pass";
		
		doSignupLoginAndAddCredential(username, password, creUrl, creUsername, crePassword);
		
		// Find the new row
		WebElement targetRow = doFindRowCredentialContainsUrl(creUrl);

		// Verify result
		Assertions.assertTrue(targetRow.findElement(By.id("credential-url")).getText().equals(creUrl));
		Assertions.assertTrue(targetRow.findElement(By.id("credential-username")).getText().equals(creUsername));
	}

	@Test
	public void shouldEditCredentialSuccess() {

		String username = "credential02", password = "pass";
		String creUrl = "credential02.com", creUsername = "credential02", crePassword = "credential02pass";
		
		doSignupLoginAndAddCredential(username, password, creUrl, creUsername, crePassword);
		
		// Find the new row
		WebElement targetRow = doFindRowCredentialContainsUrl(creUrl);

		// Find the edit button
		WebElement editButton = targetRow.findElement(By.id("edit-credential-button"));
		editButton.click();

		// wait for the form show up
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-form")));

		// new credential
		String newUrl = "credential-after-02.com";
		String newUsername = "credential-after-02";
		String newPassword = "credential-after-02pass";

		doInputCredentialThenSubmit(newUrl, newUsername, newPassword);
		doClickHomeWhenResultSuccess();

		// click on credential tab
		doClickCredentialTab();

		// Wait until the credentials showup
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-button")));

		// Verify result
		// the old row does not appear
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			doFindRowCredentialContainsUrl(creUrl);
		});

		targetRow = doFindRowCredentialContainsUrl(newUrl);
		Assertions.assertTrue(targetRow.findElement(By.id("credential-username")).getText().equals(newUsername));
		Assertions.assertTrue(targetRow.findElement(By.id("credential-url")).getText().equals(newUrl));
	}

	@Test
	public void shouldDeleteCredentialSuccess() {

		String username = "credential03", password = "pass";
		String creUrl = "credential03.com", creUsername = "credential03", crePassword = "credential03pass";
		
		doSignupLoginAndAddCredential(username, password, creUrl, creUsername, crePassword);

		// Find the new row
		WebElement targetRow = doFindRowCredentialContainsUrl(creUrl);

		// Find the edit button
		WebElement deleteButton = targetRow.findElement(By.id("delete-credential-button"));
		deleteButton.click();

		// Confirmation modal showup
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-modal")));
		WebElement deleteModal = driver.findElement(By.id("delete-modal"));
		Assertions.assertTrue(deleteModal.isDisplayed());

		// Click on delete button
		deleteButton = deleteModal.findElement(By.id("delete-button"));
		deleteButton.click();

		// Click on the credential tab
		doClickCredentialTab();

		// Verify the row is deleted
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			doFindRowCredentialContainsUrl(creUrl);
		});
	}

	@Test
	public void shouldCancelDeleteCredentialSuccess() {
		
		String username = "credential04", password = "pass";
		String creUrl = "credential04.com", creUsername = "credential04", crePassword = "credential04pass";
		
		doSignupLoginAndAddCredential(username, password, creUrl, creUsername, crePassword);

		// Find the new row
		WebElement targetRow = doFindRowCredentialContainsUrl(creUrl);

		// Find the edit button
		WebElement deleteButton = targetRow.findElement(By.id("delete-credential-button"));
		deleteButton.click();

		// Confirmation modal showup
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-modal")));
		WebElement deleteModal = driver.findElement(By.id("delete-modal"));
		Assertions.assertTrue(deleteModal.isDisplayed());

		// Click on delete button
		WebElement cancelButton = deleteModal.findElement(By.id("cancel-button"));
		cancelButton.click();

		// Click on the credential tab
		doClickCredentialTab();

		// Verify the row is not deleted
		Assertions.assertDoesNotThrow(() -> {
			doFindRowCredentialContainsUrl(creUrl);
		});
	}
}
