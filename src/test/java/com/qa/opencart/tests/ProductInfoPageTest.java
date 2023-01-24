package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetUp() {		
		accPage =loginPage.doLogin("anilteja72@gmail.com","Anil@9465");
	
	}
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro"},
			{"Macbook","MacBook Air"},
			{"iMac","iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Apple","Apple Cinema 30\""},
		};
	}
	@Test(dataProvider="getProductTestData")
	public void productHeaderTest(String searchKey,String mainProductName) {
		resultsPage = accPage.performSearch(searchKey);
		prodInfoPage = resultsPage.selectProduct(mainProductName);
		String actHeader = prodInfoPage.getProductHeader();
		Assert.assertEquals(actHeader, mainProductName);
	}
	@DataProvider
	public Object[][] getProductImageTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro",4},
			{"Macbook","MacBook Air",4},
			{"iMac","iMac",3},
			{"Samsung","Samsung SyncMaster 941BW",1},
			{"Apple","Apple Cinema 30\"",6},
		};
	}
	@Test(dataProvider="getProductImageTestData")
	public void productImageCountTest(String searchKey,String mainProductName,int imagesCount) {
		resultsPage = accPage.performSearch(searchKey);
		prodInfoPage =resultsPage.selectProduct(mainProductName);
		int actImgCount = prodInfoPage.getProductimgCount();
		Assert.assertEquals(actImgCount, imagesCount);
		
	}
	@Test
	public void productMetaDataTest() {
		resultsPage = accPage.performSearch("Macbook");
		prodInfoPage =resultsPage.selectProduct("MacBook Pro");
		Map<String,String> actProdInfoMap = prodInfoPage.getProductInformation();
		
		softAssert.assertEquals(actProdInfoMap.get("Brand"),"Apple");
		softAssert.assertEquals(actProdInfoMap.get("Availability"),"In Stock");
		softAssert.assertEquals(actProdInfoMap.get("actprice"),"$2,000.00");
		softAssert.assertEquals(actProdInfoMap.get("Reward Points"),"800");
		softAssert.assertAll();

		
	}
	

}
