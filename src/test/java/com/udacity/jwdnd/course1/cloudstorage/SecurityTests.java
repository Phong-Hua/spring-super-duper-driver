package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class SecurityTests extends AbstractTests {

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
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
	}

	/**
	 * Login should failed when user is registered but input wrong password.
	 */
	@Test
	public void shouldLoginFailedWrongPassword() {

		doMockSignUp("login02", "test", "login02", "pass");
		Assertions.assertThrows(TimeoutException.class, ()->{
			doLogIn("login02", "wrongpass");
		});
	}

	/**
	 * Login should failed for non registered user.
	 */
	@Test
	public void shouldLoginFailedNonRegisteredUser() {

		Assertions.assertThrows(TimeoutException.class, ()->{
			doLogIn("login03", "wrongpass");
		});
	}
	
	@Test
	public void shouldLogoutSuccess() {

		doMockSignUp("logout01", "test", "logout01", "pass");
		doLogIn("logout01", "pass");

		doClickLogoutAtHome();
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
		
		Assertions.assertTrue(driver.findElement(By.id("signup-success")).getText().contains("You successfully signed up!"));
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
		Assertions.assertTrue(driver.getPageSource().contains("The page you requested is not found."));
	}
}
