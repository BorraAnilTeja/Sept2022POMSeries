package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. private By locators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");


	// 2. page constructor:
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. page actions:
	@Step("getting login page title...")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
	}

	@Step("get Login Page Url")
	public String getLoginPageUrl() {
		return eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
	}

	@Step("checking forgot pwd link exist")
	public boolean isForgotPWdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwdLink);
	}

	@Step("login with username : {0} and password: {1}")
	public AccountsPage doLogin(String un, String pwd) {
		System.out.println("Creds are : " + un + " : " + pwd);
		eleUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_TIME_OUT).sendKeys(un);
		eleUtil.dosendKeys(password, pwd);
		eleUtil.doClick(loginBtn);

		return new AccountsPage(driver);
	}

	@Step("navigating to regiter page")
	public RegPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegPage(driver);
	}

}