package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utils.GenericUtils;

public class SignUpCAIPage {

	@FindBy(xpath="//div[text()='OK']") 
	WebElement sapCAI_HomePage_CookiesOKBtn;

	@FindBy(xpath="//div[text()='Sign up']") 
	WebElement sapCAI_HomePage_SignUp;

	@FindBy(id="IDS_UI_Window")
	WebElement sapCAI_Registration_iFrame1;

	By sapCAI_Registration_iFrame = By.id("IDS_UI_Window");

	@FindBy(id="firstName")
	WebElement sapCAI_Registration_FirstName;

	@FindBy(id="lastName")
	WebElement sapCAI_Registration_LastName;

	@FindBy(id="mail")
	WebElement sapCAI_Registration_Email;

	@FindBy(id="newPasswordInput")
	WebElement sapCAI_Registration_Password;

	@FindBy(id="retypeNewPasswordInput")
	WebElement sapCAI_Registration_ReEnterPassword;

	@FindBy(id="pdAccept")
	WebElement sapCAI_Registration_PrivacyStatementCheckbox;

	@FindBy(id="touAccept")
	WebElement sapCAI_Registration_TermsAndCondSAPCAICheckBox;

	@FindBy(id="sapStoreRegisterFormSubmit")
	WebElement sapCAI_Registration_RegisterButton;

	By sapCAI_Registration_SuccessMessage = By.xpath("//h1[text()='Thank you for registering with SAP Conversational AI']");
		
	GenericUtils genUtils = new GenericUtils();
	private WebDriver driver=null;
	private boolean switchedToFrame = false;

	public SignUpCAIPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public void actionsClickSignUpBtn() throws Exception {
		new Actions(driver).moveToElement(sapCAI_HomePage_SignUp).click().perform();

	}

	public void clickOKBtn() throws Exception {

		genUtils.click(sapCAI_HomePage_CookiesOKBtn, "Cookies OK Button");
	}

	public void clickSignUpBtn() throws Exception {

		genUtils.click(sapCAI_HomePage_SignUp, "Sign Up");
	}

	public void switchToFrame() throws Exception {

		if(genUtils.checkFieldExist(sapCAI_Registration_iFrame ,true, "Registration page frame")) 
		{
			genUtils.switchToFrame(sapCAI_Registration_iFrame1);
			switchedToFrame = true;
		}
	}

	public void switchToDefaultContent() throws Exception {

		if(switchedToFrame) {
			genUtils.switchToDefaultContent();	
		}

	}

	public void typeFirstName(String value) throws Exception {

		genUtils.type(sapCAI_Registration_FirstName, value, "First Name");
	}

	public void typeLastName(String value) throws Exception {

		genUtils.type(sapCAI_Registration_LastName, value, "Last Name");

	}

	public void typeEmail(String value) throws Exception {

		genUtils.type(sapCAI_Registration_Email, value, "Email");

	}

	public void typePassword(String value)throws Exception {

		genUtils.type(sapCAI_Registration_Password, value, "Password");

	}

	public void typeReEnterPassword(String value) throws Exception {

		genUtils.type(sapCAI_Registration_ReEnterPassword, value, "Re-enter Password");

	}

	public void checkPrivacyStatementCheckbox() throws Exception {

		genUtils.click(sapCAI_Registration_PrivacyStatementCheckbox, "Privacy Statement");

	}

	public void checkTermsAndCondSAPCAICheckBox() throws Exception {

		genUtils.click(sapCAI_Registration_TermsAndCondSAPCAICheckBox, "Terms and Conditions");

	}

	public void clickRegisterButton() throws Exception {

		genUtils.click(sapCAI_Registration_RegisterButton, "Register button");

	}
	
	public void verifySuccessMessage() throws Exception {

		genUtils.checkFieldExist(sapCAI_Registration_SuccessMessage, true, "Success Message");

	}
	
}
