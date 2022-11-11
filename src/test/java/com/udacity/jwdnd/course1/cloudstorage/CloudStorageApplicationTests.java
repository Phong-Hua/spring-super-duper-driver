package com.udacity.jwdnd.course1.cloudstorage;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private WebDriverWait webDriverWait;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.webDriverWait = new WebDriverWait(driver, 2);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
	 * checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();
	}

	/**
	 * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
	 * checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();
	}

	/**
	 * Click on note tab at home page
	 */
	private void doClickNoteTab() {
		// click note tab
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("nav-notes-tab")));
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("add-note-button")));
	}

	/**
	 * Click Note Tab and Add Note when at home page.
	 */
	private void doAddNoteAtHomePage(String noteTitle, String noteDescription) {
		
		doClickNoteTab();

		// click add note button
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("add-note-button")));
		WebElement addNoteButton = driver.findElement(By.id("add-note-button"));
		addNoteButton.click();

		// wait for the form show up
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-form")));

		doInputNoteTitleDescriptionThenSubmit(noteTitle, noteDescription);
	}

	private void doInputNoteTitleDescriptionThenSubmit(String noteTitle, String noteDescription) {
		// Input title and description
		WebElement inputNoteTitle = driver.findElement(By.id("input-note-title"));
		inputNoteTitle.clear();
		inputNoteTitle.sendKeys(noteTitle);

		WebElement inputNoteDescription = driver.findElement(By.id("input-note-description"));
		inputNoteDescription.clear();
		inputNoteDescription.sendKeys(noteDescription);

		// Click submit button

		WebElement submitButton = driver.findElement(By.id("noteSubmit2"));
		submitButton.click();
	}

	/**
	 * When the result page display with the success. Click to home page.
	 */
	private void doClickHomeWhenResultSuccess() {
//		// Verify success in result page
		webDriverWait.until(ExpectedConditions.titleContains("Result"));
		WebElement successHeading = driver.findElement(By.id("success"));
		WebElement homeLink = driver.findElement(By.id("a-success"));
		Assertions.assertTrue(successHeading.isDisplayed());
		Assertions.assertTrue(homeLink.isDisplayed());

		homeLink.click();
	}

	private WebElement doFindRowWithHeaderContainsText(String text) {
		WebElement tbody = driver.findElement(By.xpath("//table[@id='noteTable']//tbody"));
		WebElement targetRow =  tbody.findElement(By.xpath("//following::tr[th//text()[contains(., '" + text + "')]]"));
		return targetRow;
	}
	
	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling redirecting
	 * users back to the login page after a succesful sign up. Read more about the
	 * requirement in the rubric: https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection", "Test", "RT", "123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL", "Test", "UT", "123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code. This test is provided by Udacity to perform some basic
	 * sanity testing of your code to ensure that it meets certain rubric criteria.
	 * 
	 * If this test is failing, please ensure that you are handling uploading large
	 * files (>1MB), gracefully in your code.
	 * 
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload
	 * Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File", "Test", "LFT", "123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file

		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void shouldSignupSuccess() {
		doMockSignUp("John", "Smith", "jsmith", "pass");

		/*
		 * Check that the sign up was successful. // You may have to modify the element
		 * "success-msg" and the sign-up // success message below depening on the rest
		 * of your code.
		 */
		Assertions
				.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	@Test
	public void shouldSignupFailedForDuplicateUsername() {

		String firstname = "Signup";
		String lastname = "Failed";
		String username = "sfDuplicated";
		String password = "pass";

		doMockSignUp(firstname, lastname, username, password);

		doMockSignUp(firstname, lastname, username, password);

		Assertions.assertTrue(driver.findElement(By.id("error-msg")).getText().contains("Username already exists."));
	}

	@Test
	public void shouldLoginSuccess() {

		doMockSignUp("login01", "test", "login01", "pass");
		doLogIn("login01", "pass");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}

	/**
	 * Login should failed when user is registered but input wrong password.
	 */
	@Test
	public void shouldLoginFailedWrongPassword() {

		doMockSignUp("login02", "test", "login02", "pass");
		doLogIn("login02", "wrongpass");
		Assertions
				.assertTrue(driver.findElement(By.id("error-msg")).getText().contains("Invalid username or password."));
	}

	/**
	 * Login should failed for non registered user.
	 */
	@Test
	public void shouldLoginFailedNonRegisteredUser() {

		doLogIn("login03", "wrongpass");
		Assertions
				.assertTrue(driver.findElement(By.id("error-msg")).getText().contains("Invalid username or password."));
	}

	@Test
	public void shouldLogoutSuccess() {

		doMockSignUp("logout01", "test", "logout01", "pass");
		doLogIn("logout01", "pass");

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Login"));
	}

	@Test
	public void shouldAddNoteSuccess() {

		doMockSignUp("note01", "test", "note01", "pass");
		doLogIn("note01", "pass");

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		String title = "title01";
		String description = "description01";

		doAddNoteAtHomePage(title, description);

//		// Verify success in result page
		doClickHomeWhenResultSuccess();

		// Verify the note display
		doClickNoteTab();

		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("add-note-button")));
		// Find the row that just added that contains the title01, we just added
		WebElement newRow = driver.findElement(By.xpath("//table[@id='noteTable']//tbody//following::tr[th//text()[contains(., 'title01')]]"));

		WebElement secondRowTitle = newRow.findElement(By.id("note-title"));
		WebElement secondRowDescription = newRow.findElement(By.id("note-description"));

		Assertions.assertTrue(secondRowTitle.getText().equals(title));
		Assertions.assertTrue(secondRowDescription.getText().equals(description));
	}
	
	@Test
	public void shouldEditNoteSuccess() {
		
		doMockSignUp("note02", "test", "note02", "pass");
		doLogIn("note02", "pass");
		
		String title = "title02";
		String description = "description02";
		
		doAddNoteAtHomePage(title, description);
		
		doClickHomeWhenResultSuccess();
		
		doClickNoteTab();
		
		// Find the row just added
		WebElement targetRow = doFindRowWithHeaderContainsText(title);
		
		// Click on the edit button
		WebElement editButton = targetRow.findElement(By.id("edit-note-button"));
		editButton.click();
		
		// wait for the form show up
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-form")));
		
		// edit the note
		title = title + " edited";
		description = description + " edited";
		
		doInputNoteTitleDescriptionThenSubmit(title, description);
		doClickHomeWhenResultSuccess();
		
		// click note tab
		doClickNoteTab();

//		Thread.sleep(3000);
		// Find the target row
		targetRow = doFindRowWithHeaderContainsText(title);
		Assertions.assertNotNull(targetRow);
		
		System.out.println("Target: " + targetRow.getText());
		// Verify the title and description
		Assertions.assertTrue(targetRow.findElement(By.id("note-title")).getText().equals(title));
		Assertions.assertTrue(targetRow.findElement(By.id("note-description")).getText().equals(description));
	}
	
	@Test
	public void shouldDeleteNoteSuccess() {
		
		doMockSignUp("note03", "test", "note03", "pass");
		doLogIn("note03", "pass");
		
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		
		// Add new note
		String title = "title03";
		String description = "description03";
		
		doAddNoteAtHomePage(title, description);
		
		doClickHomeWhenResultSuccess();
		
		doClickNoteTab();
		
		// Find the row just added
		WebElement targetRow = doFindRowWithHeaderContainsText(title);
		
		// Click on delete button
		WebElement deleteButton = targetRow.findElement(By.id("delete-note-button"));
		deleteButton.click();
		
		// Confirmation modal showup
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-modal")));
		WebElement deleteModal = driver.findElement(By.id("delete-modal"));
		Assertions.assertTrue(deleteModal.isDisplayed());
		
		// Click on delete button
		deleteButton = deleteModal.findElement(By.id("delete-button"));
		deleteButton.click();
		
		// We are back to home page, click note tab
		doClickNoteTab();
		
		// check the target row is not there
		Assertions.assertThrows(NoSuchElementException.class, ()->{
			doFindRowWithHeaderContainsText(title);
		});
	}
	
	/**
	 * User click delete button on the note, then cancel delete.
	 * The note should still be available
	 */
	@Test
	public void shouldCancelDeleteNoteSuccess() {
		
		doMockSignUp("note04", "test", "note04", "pass");
		doLogIn("note04", "pass");
		
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		
		// Add new note
		String title = "title04";
		String description = "description04";
		
		doAddNoteAtHomePage(title, description);
		
		doClickHomeWhenResultSuccess();
		
		doClickNoteTab();
		
		// Find the row just added
		WebElement targetRow = doFindRowWithHeaderContainsText(title);
		
		// Click on delete button
		WebElement deleteButton = targetRow.findElement(By.id("delete-note-button"));
		deleteButton.click();
		
		// Confirmation modal showup
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-modal")));
		WebElement deleteModal = driver.findElement(By.id("delete-modal"));
		Assertions.assertTrue(deleteModal.isDisplayed());
		
		// Click on cancel button
		WebElement cancelButton = deleteModal.findElement(By.id("cancel-button"));
		cancelButton.click();
		
		// We are back to home page, click note tab
		doClickNoteTab();
		
		// check the target row is not there
		targetRow = doFindRowWithHeaderContainsText(title);
		Assertions.assertTrue(targetRow.isDisplayed());

	}
}
