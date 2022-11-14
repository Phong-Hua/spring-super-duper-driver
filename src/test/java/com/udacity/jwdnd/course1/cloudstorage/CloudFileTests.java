package com.udacity.jwdnd.course1.cloudstorage;

import java.io.File;
import java.nio.file.FileSystems;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CloudFileTests extends AbstractTests {


	/**
	 * Find a row of file table that has file name.
	 * 
	 * @param fileName
	 * @return
	 */
	private WebElement doFindRowFileWithFilename(String fileName) {
		WebElement tbody = driver.findElement(By.xpath("//table[@id='file-table']//tbody"));
		WebElement targetRow = tbody
				.findElement(By.xpath("//following::tr[th//text()[contains(., '" + fileName + "')]]"));
		return targetRow;
	}

	/**
	 * Click credential tab from home page
	 */
	private void doClickFileTab() {

		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("nav-files-tab")));
		WebElement credentialTab = driver.findElement(By.id("nav-files-tab"));

		credentialTab.click();
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("file-upload-button")));
	}

	private void doWaitForFileDownloaded(File file, int timeoutSeconds) {
		webDriverWait.withTimeout(Duration.ofSeconds(timeoutSeconds)).pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		webDriverWait.until((driver) -> file.exists());
	}
	
	/**
	 * Hit the input file button, select the file then hit upload.
	 */
	private void doUploadFile(String fileName) {
		WebElement fileSelectButton = driver.findElement(By.id("input-file"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("file-upload-button"));
		uploadButton.click();
	}
	
	private void doUploadFileAtHomePage(String fileName) {
		
		doClickFileTab();
		
		doUploadFile(fileName);
	}
	
	/**
	 * Signup with username and password.
	 * Login with username and password and upload the file.
	 * The file must be available in the root.
	 * @param username
	 * @param password
	 * @param fileName
	 */
	private void doSignupLoginAndUploadFile(String username, String password, String fileName) {
		
		doMockSignUp("file", "test", username, password);
		doLogIn(username, password);
		doUploadFileAtHomePage(fileName);
		doClickHomeWhenResultSuccess();
		doClickFileTab();
	}
	
	@Test
	public void shouldUploadSmallFileSuccess() {

		String username = "file01", password = "pass";
		String fileName = "ExampleFileTest.txt";

		doSignupLoginAndUploadFile(username, password, fileName);

		Assertions.assertDoesNotThrow(() -> {
			doFindRowFileWithFilename(fileName);
		});
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
		
		String username = "file02", password = "pass";
		String fileName = "upload5m.zip";

		doSignupLoginAndUploadFile(username, password, fileName);

		Assertions.assertDoesNotThrow(() -> {
			doFindRowFileWithFilename(fileName);
		});
	}

	@Test
	public void shouldDownloadFileSuccess() {

		String username = "file03", password = "pass";
		String fileName = "ExampleFileTest.txt";

		doSignupLoginAndUploadFile(username, password, fileName);
		
		
		// Find the view button
		WebElement targetRow = doFindRowFileWithFilename(fileName);
		WebElement viewButton = targetRow.findElement(By.id("view-button"));

		viewButton.click();

		// Verify the file exist
		String separator = FileSystems.getDefault().getSeparator();
		File file = new File(System.getProperty("user.home") + separator + "Downloads" + separator + fileName);

		doWaitForFileDownloaded(file, 3);
		Assertions.assertTrue(file.exists());
	}

	@Test
	public void shouldDownloadLargeFileSuccess() {

		String username = "file04", password = "pass";
		String fileName = "upload5m.zip";
		
		doSignupLoginAndUploadFile(username, password, fileName);

		// Find the view button
		WebElement targetRow = doFindRowFileWithFilename(fileName);
		WebElement viewButton = targetRow.findElement(By.id("view-button"));
		viewButton.click();

		// Verify the file exist
		String separator = FileSystems.getDefault().getSeparator();
		File file = new File(System.getProperty("user.home") + separator + "Downloads" + separator + fileName);
		doWaitForFileDownloaded(file, 10);
		Assertions.assertTrue(file.exists());
	}

	@Test
	public void shouldDeleteFileSuccess() {

		String username = "file05", password = "pass";
		String fileName = "upload5m.zip";
		
		doSignupLoginAndUploadFile(username, password, fileName);

		// Find the view button
		WebElement targetRow = doFindRowFileWithFilename(fileName);
		WebElement deleteButton = targetRow.findElement(By.id("delete-file-button"));
		deleteButton.click();

		// Delete modal show up

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-modal")));
		WebElement deleteModal = driver.findElement(By.id("delete-modal"));
		Assertions.assertTrue(deleteModal.isDisplayed());

		// Click on delete button
		deleteButton = deleteModal.findElement(By.id("delete-button"));
		deleteButton.click();

		// Click on File tab
		doClickFileTab();
		
		// Verify the file is deleted
		Assertions.assertThrows(NoSuchElementException.class, ()->{
			doFindRowFileWithFilename(fileName);
		});
	}
	
	@Test
	public void shouldCancelDeleteFileSuccess() {

		String username = "file06", password = "pass";
		String fileName = "upload5m.zip";
		
		doSignupLoginAndUploadFile(username, password, fileName);

		// Find the view button
		WebElement targetRow = doFindRowFileWithFilename(fileName);
		WebElement deleteButton = targetRow.findElement(By.id("delete-file-button"));
		deleteButton.click();

		// Delete modal show up

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-modal")));
		WebElement deleteModal = driver.findElement(By.id("delete-modal"));
		Assertions.assertTrue(deleteModal.isDisplayed());

		// Click on delete button
		WebElement cancelButton = deleteModal.findElement(By.id("cancel-button"));
		cancelButton.click();

		// Click on File tab
		doClickFileTab();
		
		// Verify the file is deleted
		Assertions.assertDoesNotThrow(()->{
			doFindRowFileWithFilename(fileName);
		});
	}
}
