package com.automationFramework.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automationFramework.helper.GenericHelper;
import com.automationFramework.helper.LoggerHelper;
import com.automationFramework.pageObjects.LoginPage;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;


/**
 * @author sangale_d
 *
 */
public class LoginPage {
	
	WebDriver driver;
	private final Logger log = LoggerHelper.getLogger(LoginPage.class);
	GenericHelper genericHelper;
	
	@FindBy(xpath="//*[@id='header']/div[2]/div/div/nav/div[1]/a")
	private WebElement signin;
	
	@FindBy(xpath="//*[@id='header']/div[2]/div/div/nav/div[2]/a")
	private WebElement signout;
	
	@FindBy(xpath="//*[@id='email']")
	private WebElement emailAddress;
	
	@FindBy(xpath="//*[@id='passwd']")
	private WebElement password;
	
	@FindBy(xpath="//*[@id='SubmitLogin']")
	private WebElement submitLogin;
	
	@FindBy(xpath="//*[@id='center_column']/p")
	private WebElement successMsgObject;
	
	@FindBy(xpath="//*[@id='email_create']")
	private WebElement registration;
	
	@FindBy(xpath="//*[@id='SubmitCreate']")
	private WebElement createAnAccount;
	
/**
 * Constructor to initialize webelements
 * @param driver
 */
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		genericHelper = new GenericHelper(driver);
		genericHelper.waitForElement(driver, signin,new Config(TestBase.OR).getExplicitWait());
	}
	
	public void clickOnSignInLink(){
		log.info("clicked on sign in link...");
		signin.click();
	}
	
	public void enterEmailAddress(String emailAddress){
		log.info("entering email address...."+emailAddress);
		this.emailAddress.sendKeys(emailAddress);
	}
	
	public void enterPassword(String password){
		log.info("entering password...."+password);
		this.password.sendKeys(password);
	}
	
	public HomePage clickOnSubmitButton(){
		log.info("clicking on submit button...");
		new GenericHelper(driver).scrollDownVertically();
		submitLogin.click();
		return new HomePage(driver);
	}
	
	public boolean verifySuccessLoginMsg(){
		return new GenericHelper(driver).isDisplayed(successMsgObject);
	}
	
	public void enterRegistrationEmail(){
		String email = System.currentTimeMillis()+"@gmail.com";
		log.info("entering registration email.."+email);
		registration.sendKeys(email);
	}
	
	public RegistrationPage clickOnCreateAnAccount(){
		createAnAccount.click();
		return new RegistrationPage(driver);
	}
	
	public void loginToApplication(String emailAddress, String password){
		clickOnSignInLink();
		enterEmailAddress(emailAddress);
		enterPassword(password);
		clickOnSubmitButton();
	}

	public void clickOnSignOutLink(){
		log.info("clicked on sign out link...");
		signout.click();
	}

	}
