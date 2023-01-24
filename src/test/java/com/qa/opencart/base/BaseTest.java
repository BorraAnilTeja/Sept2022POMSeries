package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegPage;
import com.qa.opencart.pages.ResultsPage;

public class BaseTest {
	
	
	
	
	DriverFactory df;
	WebDriver driver;
	protected LoginPage loginPage;
	protected AccountsPage accPage ;
	protected ResultsPage resultsPage ;
	protected SoftAssert softAssert;
	protected ProductInfoPage prodInfoPage ;
	protected Properties prop;
	protected RegPage regPage;
	
	
	@BeforeTest
	public void setUp() {
		df= new DriverFactory();
		prop = df.initProp();
	    driver =df.initDriver(prop);
	    loginPage= new LoginPage(driver);
	    softAssert = new SoftAssert();
	}
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
	
	
		
	

}
