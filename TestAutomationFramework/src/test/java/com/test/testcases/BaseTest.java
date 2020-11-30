package com.test.testcases;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.main.DriverFactory;
import com.main.DriverManager;
import com.reports.ExtentManager;


public class BaseTest {

	public static String projectPath = System.getProperty("user.dir");
	private WebDriver driver;
	public Properties config =new Properties(); 
	public FileInputStream inputstream;
	public WebDriverWait wait;
	public ExtentManager extentmanager;
	public ExtentReports report;
	public ExtentTest test;
	public ExtentTest logger;
	public ExtentHtmlReporter htmlReporter = null;
	ExtentReports extent = null;

	public String browser;
	//public ExcelUtilities excel;

	@BeforeSuite
	public void beforeSuite() throws Exception {

		setUpConfigFile();
		setUpExtentReport();
		setUpExecutablesPath();
		setUpExecutionMode();
		setUpOtherProperties();
		setUpRunModeForTestCases();
		setUpDocker();
	}

	@AfterSuite
	public void afterSuite() throws Exception {

		downDocker();

	}

	
	private void setUpConfigFile() {

		try 
		{
			inputstream=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
			config.load(inputstream);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private void setUpExtentReport() throws Exception {

		DriverFactory.setExtentConfigLocation(config.getProperty("ExtentConfigLocation"));
		extentmanager=new ExtentManager(); 
		extent=extentmanager.getInstance();

	}
	private void setUpRunModeForTestCases() {

	}

	private static void setUpDocker() throws IOException, Exception {	

		
		if (DriverFactory.getExecutionMode().equalsIgnoreCase("Remote")) {
			Runtime runtime=Runtime.getRuntime();
			if(DriverFactory.getRemoteMode().equalsIgnoreCase("Selenium")) {

				Process process = runtime.exec("cmd /c start dockerUp.bat");

				/*
				 * do { Thread.sleep(5000); System.out.println("Waiting for the process....");
				 * }while(exitVal!=0);
				 */
				
				process.waitFor();
				Thread.sleep(10000);
				verifyDockerIsUp();
				
				runtime.exec("taskkill /f /im cmd.exe") ;
			}
			else if(DriverFactory.getRemoteMode().equalsIgnoreCase("Zalenium")) {
				
			//	runtime.exec("cmd /c start zaleniumDown.bat");
				//Process process = runtime.exec("cmd /c start zaleniumUp.bat");
				//final int exitVal = process.waitFor();
				
				//String[] commands = {"cmd /c start zaleniumUp.bat", "-get t"};
				Process process  = runtime.exec("cmd /c start zaleniumUp.bat");
				process.waitFor();
				Thread.sleep(10000);
				verifyDockerIsUp();
			}
		}

	}

	public static void downDocker() throws IOException, InterruptedException {

		Runtime	runtime=Runtime.getRuntime();

		if (DriverFactory.getExecutionMode().equalsIgnoreCase("Remote")) {
			if(DriverFactory.getRemoteMode().equalsIgnoreCase("Selenium")) {
				runtime.exec("cmd /c start dockerDown.bat");
			}
			else if(DriverFactory.getRemoteMode().equalsIgnoreCase("Zalenium")){
				runtime.exec("cmd /c start zaleniumDown.bat");
			}

			Thread.sleep(20000);
			runtime.exec("taskkill /f /im cmd.exe") ;
		}
	}
	
	
	private static void verifyDockerIsUp() throws FileNotFoundException, Exception {
		Thread.sleep(10000);
		boolean flag=false;
		String file="output.txt";
		BufferedReader reader= new BufferedReader(new FileReader(file));
		String currentline=reader.readLine();

		while(currentline!=null) {
			if((currentline.contains("The node is registered to the hub and ready to use"))||(currentline.contains("Zalenium is now ready!"))) {
				flag=true;
				break;
			}
			currentline=reader.readLine();
		}
		reader.close();

		if(!flag) {
			throw new SkipException("Docker has not started. Please try again");
		}
	}

	
	private static void verifyDockerIsUp(Process proc) throws FileNotFoundException, Exception {
		Thread.sleep(10000);
		
		boolean flag=false;
		BufferedReader reader= new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String currentline=reader.readLine();
		System.out.println(currentline);
		while(currentline!=null) {
			//System.out.println(currentline);
			if((currentline.contains("The node is registered to the hub and ready to use"))||(currentline.contains("Zalenium is now ready!"))) {
				flag=true;
				break;
			}
			currentline=reader.readLine();
		}
		reader.close();

		if(!flag) {
			throw new SkipException("Docker has not started. Please try again!!");
		}
	}

	@SuppressWarnings("deprecation")
	public void openBrowser(String browser) throws MalformedURLException {

		DesiredCapabilities capability=null;

		if(DriverFactory.getExecutionMode().equalsIgnoreCase("Local")){
			ChromeOptions options=new ChromeOptions();

			switch(browser){

			case "chrome":

				System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.prompt_for_download", "true");

				chromePrefs.put("safebrowsing.enabled", "true"); 
				options.setExperimentalOption("prefs", chromePrefs);
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				//options.addArguments("--incognito");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(cap);
				break;

			case "firefox":

				System.setProperty("webdriver.gecko.driver", DriverFactory.getGeckoDriverExePath());
				FirefoxOptions FFoptions= new FirefoxOptions();
				//FFoptions.addArguments("--incognito");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				driver = new FirefoxDriver(capabilities);
				break;

			default:

				System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				//options.addArguments("--incognito");
				driver=new ChromeDriver(options);
				break;
			}
		}
		else {
			switch(browser){

			case "chrome":
				capability = DesiredCapabilities.chrome();
				capability.setBrowserName("chrome");
				capability.setPlatform(Platform.ANY);
				driver=new RemoteWebDriver(new URL(DriverFactory.getGridPath()),capability);
				break;
			case "firefox":
				capability = DesiredCapabilities.firefox();
				capability.setBrowserName("firefox");
				capability.setPlatform(Platform.ANY);
				driver=new RemoteWebDriver(new URL(DriverFactory.getGridPath()),capability);
				break;
			default:
				capability = DesiredCapabilities.chrome();
				capability.setBrowserName("chrome");
				capability.setPlatform(Platform.ANY);
				driver=new RemoteWebDriver(new URL(DriverFactory.getGridPath()),capability);
				break;
			}
		}

		DriverManager.setWebDriver(driver);
		maximizeWindow();
		setUpImplicitWait();
	}

	private void setUpOtherProperties() {

		if(DriverFactory.getExecutionMode().equalsIgnoreCase("Remote")) {
			DriverFactory.setGridPath(config.getProperty("RemoteURL"));
			DriverFactory.setRemoteMode(config.getProperty("RemoteMode"));
		}

		DriverFactory.setBrowser(config.getProperty("Browser"));
		DriverFactory.setTestDataLocation(config.getProperty("TestDataLocation"));
		DriverFactory.setWaitTime(Integer.parseInt(config.getProperty("WaitTime")));
		DriverFactory.setTestURL(config.getProperty("url"));
		DriverFactory.setScreenshotPath(config.getProperty("ScreenshotsPath"));
		DriverFactory.setFailedStepsScreenshots(config.getProperty("FailedStepsScreenshots"));
	}

	private void setUpExecutionMode() {
		DriverFactory.setExecutionMode(config.getProperty("ExecutionMode"));
	}

	private void setUpExecutablesPath() {

		DriverFactory.setChromeDriverExePath(".//src/test//resources//chromedriver.exe");
		DriverFactory.setGeckoDriverExePath(".//src//test//resources//geckodriver.exe");
	}

	public void navigateToURL(WebDriver driver) {

		driver.get(DriverFactory.getTestURL());
	}


	private void setUpImplicitWait() {

		DriverManager.getDriver().manage().timeouts().implicitlyWait(DriverFactory.getWaitTime(), TimeUnit.SECONDS);
	}

	private void maximizeWindow() {

		DriverManager.getDriver().manage().window().maximize();
	}

	public void explicitWait(WebElement ele)throws Exception{

		wait=new WebDriverWait(DriverManager.getDriver(),10);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
}
