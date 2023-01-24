package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	ElementUtil eleUtil;
	private By productHeader = By.cssSelector("div#content h1");
	private By productimgs = By.cssSelector("a.thumbnail");
	private By prodMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]//li");
	private By prodPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]//li");
	
	private Map<String,String> productMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}
	public String  getProductHeader() {
		return eleUtil.doGetElementText(productHeader);
	}
	public int getProductimgCount() {
		int imgsCount = eleUtil.waitForElementsVisible(productimgs, TimeUtil.DEFAULT_TIME_OUT).size();
		System.out.println("product images count is :"+imgsCount);
		return imgsCount;
	}
	public Map<String,String> getProductInformation() {
		productMap = new HashMap<String,String>();
		getProductMetaData();
		getProductPriceData();
		System.out.println(productMap);
		return productMap;
		
		
		
	}
	
	private void getProductMetaData() {
		List<WebElement> prodMetaList = eleUtil.getElements(prodMetaData);
		System.out.println("product meta data list count is :"+prodMetaList.size());
		
		for(WebElement e:prodMetaList) {
			String meta = e.getText();
			String metaData[] = meta.split(":");
			String metaKey = metaData[0].trim();
			String metaVal = metaData[1].trim();
			productMap.put(metaKey, metaVal);
		}
	}
	private void getProductPriceData() {
		List<WebElement> prodPriceList = eleUtil.getElements(prodPriceData);
		System.out.println("product price list count is :"+prodPriceList);
		String price = prodPriceList.get(0).getText().trim();
		String exTaxprice = prodPriceList.get(1).getText().trim();
		productMap.put("actprice", price);
		productMap.put("actextaxprice", exTaxprice);

	}

}
