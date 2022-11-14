package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractTests {

	@LocalServerPort
	protected int port;
	protected WebDriver driver;
	protected WebDriverWait webDriverWait;

	@BeforeAll
	static void beforeAllAbstractTests() {
		WebDriverManager.chromedriver().setup();
		System.out.println("Before all: Abstract");
	}
	
	@BeforeEach
	void beforeEachAbstractTests() {
		this.driver = new ChromeDriver();
		// init the driver wait with timeout is 10 seconds
		this.webDriverWait = new WebDriverWait(driver, 10);
	}
	
	@AfterEach
	void afterEach() {
		if (this.driver != null)
			this.driver.quit();
	}
	
	/**
	 * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
	 * checks.
	 **/
	protected void doLogIn(String userName, String password) {
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
		
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}
	
	/**
	 * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
	 * checks.
	 **/
	protected void doMockSignUp(String firstName, String lastName, String userName, String password) {
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
	 * When the result page display with the success. Click to home page.
	 */
	protected void doClickHomeWhenResultSuccess() {
//		// Verify success in result page
		webDriverWait.until(ExpectedConditions.titleContains("Result"));
		WebElement successHeading = driver.findElement(By.id("success"));
		WebElement homeLink = driver.findElement(By.id("a-success"));
		Assertions.assertTrue(successHeading.isDisplayed());
		Assertions.assertTrue(homeLink.isDisplayed());
		homeLink.click();
		
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}
	
	/**
	 * Click logout at home page.
	 */
	protected void doClickLogoutAtHome() {
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Login"));
	}
}
