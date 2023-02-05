package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();
		System.out.println("Browser name is : " + browserName);

		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase("chrome")) {
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote execution on docker/cloud/grid
				init_remoteDriver("chrome");
			}
			else {
				//local execution 
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			} 
			}
			
			
			
		else if (browserName.equalsIgnoreCase("firefox")) {
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote execution on docker/cloud/grid
				init_remoteDriver("firefox");
			}
			else {
				//local execution 
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			} 
			

		
			
			
		} else if (browserName.equalsIgnoreCase("edge")) {
			
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote execution on docker/cloud/grid
				init_remoteDriver("edge");
			}
			else {
				//local execution 
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			} 
			
			
		} else if (browserName.equalsIgnoreCase("safari")) {
			tlDriver.set(new SafariDriver());
			
		} else {
			System.out.println("Please pass the right browser name...." + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}
	
	private void init_remoteDriver(String browserName) {
		
		System.out.println("Running test cases on remote grid server :"+browserName);
		
		   if(browserName.equalsIgnoreCase("chrome")) {
			   try {
			      tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
		   } catch   (MalformedURLException e) {
			   e.printStackTrace();
		   }
		}
		   if(browserName.equalsIgnoreCase("firefox")) {
			   try {
			      tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
		   } catch   (MalformedURLException e) {
			   e.printStackTrace();
		   }
		}
		   if(browserName.equalsIgnoreCase("edge")) {
			   try {
			      tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
		   } catch   (MalformedURLException e) {
			   e.printStackTrace();
		   }
		}
		

	
	}

	//get the local thread copy of the driver
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}
		

	public Properties initProp() {
		
		 prop = new Properties();
		FileInputStream ip = null;
		
		//mvn clean install -Denv="qa"
		//mvn clean install
		
		String envName = System.getProperty("env");
		System.out.println("------>Running test cases on environment------>"+ envName);
		
		if(envName==null) {
			System.out.println("No environment is given......hence running on QA environment");
			try {
				 ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
			switch (envName) {
			case "qa":
				 ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "stage":
				 ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "dev":
				 ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "uat":
				 ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			case "prod":
				 ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;

			default:
				System.out.println("Please pass the right env name :"+envName);
				break;
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		}
			try {
				prop.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
		return prop;
		
	}
	
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}

}