package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountsPage {
	
	private WebDriver driver;
	ElementUtil eleUtil;
	
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	private By logoutLink = By.linkText("Logout");
	private By accsecHeaders = By.cssSelector("div#content h2");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		return eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
	}
	public String getAccPageURL() {
		return eleUtil.waitForUrlContains(AppConstants.ACCOUNTS_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
	}
	public boolean isSearchExist() {
		return eleUtil.doIsDisplayed(search);
	}
	public boolean isLogOutExist() {
		return eleUtil.doIsDisplayed(logoutLink);
	}
	public List<String> getAccountsPageSectionHeaders() {
		List<WebElement> secHeaderList = eleUtil.waitForElementsVisible(accsecHeaders, TimeUtil.DEFAULT_TIME_OUT);
		List<String> secHeaderValList = new ArrayList<String>();
		
		for(WebElement e :secHeaderList ) {
			String text = e.getText();
			secHeaderValList.add(text);
		}
		return secHeaderValList;
	}

	public ResultsPage performSearch(String productName) {
		System.out.println("product search for :" + productName);
		if(isSearchExist()) {
		eleUtil.dosendKeys(search, productName);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);
	   }
		return null;
	}
}
