package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.AppErrors;

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));	
		}
	@Test
	public void  accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		System.out.println("account page title is :"+actTitle);
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
		
		
	}
	@Test
	public void accPageURLTest() {
		String actURL = accPage.getAccPageURL();
		System.out.println("Acc page URL is :"+actURL);
		Assert.assertTrue(actURL.contains(AppConstants.ACCOUNTS_PAGE_FRACTION_URL),AppErrors.NO_URL_MATCHED);

	}
	@Test
	public void searchExistTest() {
		Assert.assertTrue(accPage.isSearchExist());
	}
	@Test
	public void logoutExistTest() {
		Assert.assertTrue(accPage.isLogOutExist());
	}
	@Test
	public void accPageHeadersTest() {
		List<String> actHeaderList = accPage.getAccountsPageSectionHeaders();
		Assert.assertEquals(actHeaderList, AppConstants.EXPECTED_ACC_HEADER_LIST);
	}
	
	@DataProvider
	public Object[][] getProductName() {
		return new Object[][] {
			{"macbook"},
			{"imac"},
			{"samsung"}
		
		};
	}
	
	
	//TDD
	@Test(dataProvider = "getProductName")
	public void productSearchTest(String productName) {
		
		resultsPage = accPage.performSearch(productName);
		String actTitle = resultsPage.getSearchPageTitle(productName);
		System.out.println("search page title is :"+actTitle);
		softAssert.assertEquals(actTitle, AppConstants.SEARCH_PAGE_TITLE+" "+productName);
		
		Assert.assertTrue(resultsPage.getsearchProductsCount()>0);
	}
	
	

}
