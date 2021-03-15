package com.test.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.listener.RetryFailedTestCases;
import com.main.DriverFactory;
import com.main.DriverManager;
import com.pages.SignUpCAIPage;
import com.reports.ExtentFactory;
import com.reports.LogStatus;
import com.utils.Constants;
import com.utils.Helper;


public class PlatformSignUpTest extends BaseTest{

	SignUpCAIPage signupPage = null;

	@BeforeMethod
	public void beforeMethod() {

		test=extent.createTest("Platform Sign Up");
		ExtentFactory.setExtentTest(test);
		
	}

	@Test(dataProvider = "data") // , retryAnalyzer = RetryFailedTestCases.class
	public void signUpConversationalAI(String firstName, String lastName, String email,String password) throws Exception {

		openBrowser(DriverFactory.getBrowser());
		navigateToURL(DriverManager.getDriver());	
		signupPage = new SignUpCAIPage(DriverManager.getDriver());
		signupPage.clickOKBtn();
		signupPage.clickSignUpBtn();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		signupPage.switchToFrame();
		firstName = firstName+"_"+Helper.currentTimeStamp();
		signupPage.typeFirstName(firstName);
		lastName = lastName+"_"+Helper.currentTimeStamp();
		signupPage.typeLastName(lastName);
		signupPage.typeEmail(email);
		signupPage.typePassword(password);
		signupPage.typeReEnterPassword(password);
		signupPage.checkPrivacyStatementCheckbox();
		signupPage.checkTermsAndCondSAPCAICheckBox();
		signupPage.clickRegisterButton();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		signupPage.verifySuccessMessage();
		signupPage.switchToDefaultContent();
	}

	@AfterMethod
	public void afterMethod() {

		if(DriverManager.getDriver()!=null) {
			DriverManager.getDriver().close();
			DriverManager.getDriver().quit();
			LogStatus.info("Browser closed");
		}

		extent.flush();
	}

	
	@DataProvider(name = "data")
	public Object[][] dataSupplier() throws IOException {

		File file = new File(Constants.testDataLocation);
		FileInputStream fis = new FileInputStream(file);

		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum() ;
		int lastCellNum = sheet.getRow(0).getLastCellNum();

		Object[][] obj = new Object[lastRowNum][lastCellNum];

		for (int i = 0; i < lastRowNum; i++) {
			for (int k = 0; k < lastCellNum; k++) {
				obj[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		
		return  obj;
	}
}
