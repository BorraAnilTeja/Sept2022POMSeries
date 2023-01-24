package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.AppErrors;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic - 100: Design Login page for open cart shopping application")
@Story("US - 101: Create login page functionality for open cart login page")
public class LoginPageTest extends BaseTest {

	
	@Description("login page title test")
	@Severity(SeverityLevel.TRIVIAL)
	@Test()
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		System.out.println("login page title : " + actTitle);
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppErrors.NO_TITLE_MATCHED);
	}

	@Description("login page url test")
	@Severity(SeverityLevel.NORMAL)
	@Test()
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageUrl();
		System.out.println("login page url : " + actURL);
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppErrors.NO_URL_MATCHED);
	}

	@Description("forgot password link on login page test")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPWdLinkExist());
	}

	@Description("user is able to login on login page test")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogOutExist(), AppErrors.LOGIN_UNSUCCESSFUL);
	}

}