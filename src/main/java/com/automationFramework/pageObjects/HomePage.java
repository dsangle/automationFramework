package com.automationFramework.pageObjects;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automationFramework.helper.GenericHelper;
import com.automationFramework.helper.LoggerHelper;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;


/**
 * 
 * @author sangale_d
 *
 */
public class HomePage {

	WebDriver driver;
	private final Logger log = LoggerHelper.getLogger(HomePage.class);
	GenericHelper genericHelper;

	String Tshirts = "T-shirts";
	String Blouses = "Blouses";
	String CasualDresses = "Casual Dresses";

	@FindBy(xpath = "//*[@id='block_top_menu']/ul/li[1]/a")
	public WebElement womenMenu;

	@FindBy(xpath = "//*[@id='block_top_menu']/ul/li[2]/a")
	private WebElement dressesMenu;

	@FindBy(xpath = "//*[@id='block_top_menu']/ul/li[3]/a")
	private WebElement tshirtsMenu;
	
	@FindBy(xpath = "//iframe[contains(@src,'facebook')]")
	private WebElement iframe;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		genericHelper = new GenericHelper(driver);
		genericHelper.waitForElement(driver, womenMenu, new Config(TestBase.OR).getExplicitWait());
	}

	public void mouseOver(String data) {
		log.info("doing mouse over on :" + data);
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//*[contains(text(),'" + data + "')]"))).build().perform();
	}

	public ProductCategoryPage clickOnIntem(String data) {
		log.info("clickin on :" + data);
		driver.findElement(By.xpath("//*[contains(text(),'" + data + "')]")).click();
		return new ProductCategoryPage(driver);
	}

	public ProductCategoryPage clickOnMenu(WebElement element) {
		log.info("clickin on : " + element.getText());
		element.click();
		return new ProductCategoryPage(driver);
	}
	
	public void navigateToFollowOnFaceBook() {
		log.info("navigating to facebook iframe ");
		genericHelper.scrollDownVertically();
		genericHelper.switchToFrame(iframe);	
		
	}
	
	

}
