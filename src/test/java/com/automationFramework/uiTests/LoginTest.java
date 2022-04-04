package com.automationFramework.uiTests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationFramework.helper.LoggerHelper;
import com.automationFramework.pageObjects.LoginPage;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;
import com.automationFramework.testUtil.Excel_reader;

/**
 * 
 * @author sangale_d
 *
 */
public class LoginTest extends TestBase{
	private final Logger log = LoggerHelper.getLogger(LoginTest.class);
	Excel_reader excelReader;
	
	@Test(dataProvider="testData")
	public void testLoginToApplication(String userName, String password){
		log.info(LoginTest.class.getName()+" started");
		Config config = new Config(OR);
		driver.get(config.getWebsite());
		
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage.loginToApplication(userName, password);
		boolean status = loginPage.verifySuccessLoginMsg();
		if(status){
		   log.info("login is sucessful");	
		}
		else{
			Assert.assertTrue(false, "login is not sucessful");
		}
		
		loginPage.clickOnSignOutLink();
	}
	
	@DataProvider(name="testData")
	public Object[][] testData() {
		excelReader = new Excel_reader();
		Object[][] data = excelReader.getExcelData(excelReader.excellocation,excelReader.LoginSheetName);
		return data;
	}

}
