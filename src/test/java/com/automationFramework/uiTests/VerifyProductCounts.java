package com.automationFramework.uiTests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automationFramework.helper.GenericHelper;
import com.automationFramework.helper.LoggerHelper;
import com.automationFramework.pageObjects.HomePage;
import com.automationFramework.pageObjects.LoginPage;
import com.automationFramework.pageObjects.ProductCategoryPage;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

/**
 * 
 * @author sangale_d
 *
 */
public class VerifyProductCounts extends TestBase {
	private final Logger log = LoggerHelper.getLogger(VerifyProductCounts.class);
	GenericHelper genericHelper;
	HomePage homePage;
	LoginPage loginPage;

	@Test(description = "validate follow to facebook and verify product counts for color orange")
	public void testVerifyProductCounts() {
		
		log.info(VerifyProductCounts.class.getName() + " started");
		Config config = new Config(OR);
		driver.get(config.getWebsite());
		homePage = new HomePage(driver);
		genericHelper = new GenericHelper(driver);
		loginPage = new LoginPage(driver);

		test.log(LogStatus.INFO, "navigate to follow on facebook iframe");
		log.info("Navigate to follow on facebook iframe");
		homePage.navigateToFollowOnFaceBook();
		genericHelper.switchToDefaultFrame();

		test.log(LogStatus.INFO, "Navigate to product category page");

		log.info("Navigate to product category page ");
		ProductCategoryPage pCate = homePage.clickOnMenu(homePage.womenMenu);
		
		log.info("select color as : "+pCate.Orange);
		pCate.selectColor(pCate.Orange);
		int count = pCate.getTotalProducts();

		log.info("Validate that count of oracle products is 3");
		Assert.assertEquals(count, 3);
		
		log.info("select first product and verify that product successfully added message is displayed");
		pCate.selectFirstProduct();	
		boolean flag = pCate.verifyProductAddedMsg();
		Assert.assertTrue(flag);
		
		
	}

}
