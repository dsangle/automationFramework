package com.automationFramework.uiTests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationFramework.helper.LoggerHelper;
import com.automationFramework.pageObjects.LoginPage;
import com.automationFramework.pageObjects.MyAccountPage;
import com.automationFramework.pageObjects.RegistrationPage;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;
import com.automationFramework.testUtil.Excel_reader;

/**
 * 
 * @author sangale_d
 *
 */
public class Registration extends TestBase {

	private final Logger log = LoggerHelper.getLogger(Registration.class);
	LoginPage loginPage;
	RegistrationPage register;
	MyAccountPage myAccountPage;
    Excel_reader excelReader;
    
	@Test(dataProvider = "testData",description="Verify user registration with 3 user details")
	public void testLoginToApplication(String firstName, String lastName, String password, String address,
			String addressFirstName, String addressLastName, String company, String city, String state) {
		
		log.info(Registration.class.getName() + " started");
		Config config = new Config(OR);
		driver.get(config.getWebsite());

		log.info("Click on sign on link..");
		loginPage = new LoginPage(driver);
		loginPage.clickOnSignInLink();
		loginPage.enterRegistrationEmail();
		loginPage.clickOnCreateAnAccount();

		log.info("Navigate to registration page and enter values..");
		register = new RegistrationPage(driver);
		register.setMrRadioButton();
		register.setFirstName(firstName);
		register.setLastname(lastName);
		register.setPassword(password);
		register.setAddress(address);
		
		log.info("Set date of birth..");
		register.setDay("5");
		register.setMonth("June");
		register.setYear("2017");
		register.setYourAddressFirstName(addressFirstName);
		register.setYourAddressLastName(addressLastName);
		register.setYourAddressCompany(company);
		register.setYourAddressCity(city);
		register.setYourAddressState(state);
		register.setYourAddressPostCode("99501");
		register.setMobilePhone("9999999999");
		register.setAddressAlias("addressAlias");
		
		log.info("Click on Registration and validate successful registration message..");
		register.clickRegistration();
		myAccountPage = new MyAccountPage(driver);

		boolean flag = myAccountPage.verifySuccessRegistrationMsg();
		Assert.assertTrue(flag, "Registration is not sucessful");
		
		log.info("click on sign out..");
		loginPage.clickOnSignOutLink();
	}

	@DataProvider(name = "testData")
	public Object[][] testData() {
		excelReader = new Excel_reader();
		String excellocation = System.getProperty("user.dir") + "/src/main/resources/testData/TestData.xlsx";
		Object[][] data = excelReader.getExcelData(excellocation,"Registration");
		return data;
	}

}
