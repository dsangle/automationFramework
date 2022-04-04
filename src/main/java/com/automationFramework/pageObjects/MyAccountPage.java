package com.automationFramework.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automationFramework.helper.GenericHelper;
import com.automationFramework.helper.LoggerHelper;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;

/**
 * @author sangale_d
 *
 */
public class MyAccountPage {

	WebDriver driver;
	private final Logger log = LoggerHelper.getLogger(MyAccountPage.class);
	GenericHelper genericHelper;
	
	@FindBy(xpath="//*[contains(text(),'Welcome to your account. Here you can manage all of your personal information and orders.')]")
	private WebElement successRegistrationMsg;
	
	@FindBy(xpath="//*[contains(text(),'Order history and details')]")
	private WebElement OrderHistoryAndDetails;
	
	
	public MyAccountPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		genericHelper = new GenericHelper(driver);
		genericHelper.waitForElement(driver, OrderHistoryAndDetails,new Config(TestBase.OR).getExplicitWait());
	}
	
	  public boolean verifySuccessRegistrationMsg(){ 
		  boolean isDispalyed = false;
			try {
				 isDispalyed= successRegistrationMsg.isDisplayed();
				 log.info(successRegistrationMsg.getText()+" is dispalyed");
			}
			catch(Exception ex) {
				log.error("Element not found " + ex);
			}
			
			return isDispalyed;
	  }
	 
}
