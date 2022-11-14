package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CloudNoteTests extends AbstractTests {
	
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
	 * Click Add Note button at the Note tab.
	 */
	private void doClickAddNoteButton() {
		
		// click add note button
		webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("add-note-button")));
		WebElement addNoteButton = driver.findElement(By.id("add-note-button"));
		addNoteButton.click();

		// wait for the form show up
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-form")));
	}

	/**
	 * Click Note Tab and Add Note when at home page.
	 */
	private void doAddNoteAtHomePage(String noteTitle, String noteDescription) {

		doClickNoteTab();

		doClickAddNoteButton();

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
	
	private WebElement doFindRowWithHeaderContainsText(String text) {
		WebElement tbody = driver.findElement(By.xpath("//table[@id='noteTable']//tbody"));
		WebElement targetRow = tbody.findElement(By.xpath("//following::tr[th//text()[contains(., '" + text + "')]]"));
		return targetRow;
	}
	
	private void doSignupLoginAndAddNote(String username, String password, String noteTitle, String noteDescription) {
		doMockSignUp("note", "test", username, password);
		doLogIn(username, password);

		doAddNoteAtHomePage(noteTitle, noteDescription);

//		// Verify success in result page
		doClickHomeWhenResultSuccess();

		// Verify the note display
		doClickNoteTab();
	}
	
	@Test
	public void shouldAddNoteSuccess() {

		String username = "note01", password = "pass";
		String title = "title01", description = "description01";

		doSignupLoginAndAddNote(username, password, title, description);
		
		// Find the target row
		WebElement targetRow = doFindRowWithHeaderContainsText(title);
		
		WebElement secondRowTitle = targetRow.findElement(By.id("note-title"));
		WebElement secondRowDescription = targetRow.findElement(By.id("note-description"));

		Assertions.assertTrue(secondRowTitle.getText().equals(title));
		Assertions.assertTrue(secondRowDescription.getText().equals(description));
	}
	
	@Test
	public void shouldEditNoteSuccess() {

		String username = "note02", password = "pass";
		String title = "title02", description = "description02";

		doSignupLoginAndAddNote(username, password, title, description);

		// Find the row just added
		WebElement targetRow = doFindRowWithHeaderContainsText(title);

		// Click on the edit button
		WebElement editButton = targetRow.findElement(By.id("edit-note-button"));
		editButton.click();

		// wait for the form show up
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-form")));

		// edit the note
		String newTitle = "title-edited-02";
		String newDescription = "description-edited-02";

		doInputNoteTitleDescriptionThenSubmit(newTitle, newDescription);
		doClickHomeWhenResultSuccess();

		// click note tab
		doClickNoteTab();

		// Verify the old row does not exist
		Assertions.assertThrows(NoSuchElementException.class, ()->{
			doFindRowWithHeaderContainsText(title);
		});
		
		// Find the new target row
		targetRow = doFindRowWithHeaderContainsText(newTitle);
		Assertions.assertNotNull(targetRow);

		// Verify the title and description
		Assertions.assertTrue(targetRow.findElement(By.id("note-title")).getText().equals(newTitle));
		Assertions.assertTrue(targetRow.findElement(By.id("note-description")).getText().equals(newDescription));
	}

	@Test
	public void shouldDeleteNoteSuccess() {

		String username = "note03", password = "pass";
		String title = "title03", description = "description03";

		doSignupLoginAndAddNote(username, password, title, description);

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
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			doFindRowWithHeaderContainsText(title);
		});
	}

	/**
	 * User click delete button on the note, then cancel delete. The note should
	 * still be available
	 */
	@Test
	public void shouldCancelDeleteNoteSuccess() {

		String username = "note04", password = "pass";
		String title = "title04", description = "description04";

		doSignupLoginAndAddNote(username, password, title, description);

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
	
	@Test
	public void shouldUserOnlySeeTheirNotes() {
		
		/**
		 * Create 2 users with note and logout
		 */
		String username01 = "note05-01", password01 = "pass";
		String title01 = "title05-01", description01 = "description05-01";

		doSignupLoginAndAddNote(username01, password01, title01, description01);
		doClickLogoutAtHome();
		
		String username02 = "note05-02", password02 = "pass";
		String title02 = "title05-02", description02 = "description05-02";

		doSignupLoginAndAddNote(username02, password02, title02, description02);
		doClickLogoutAtHome();
		
		/**
		 * Login as user 1, and we should not see user 2 notes
		 */
		doLogIn(username01, password01);
		Assertions.assertThrows(NoSuchElementException.class, ()->{
			doFindRowWithHeaderContainsText(title02);
		});
	}
}
