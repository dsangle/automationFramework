package com.automationFramework.pageObjects;

import java.util.List;

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
public class ProductCategoryPage {

	WebDriver driver;
	private final Logger log = LoggerHelper.getLogger(ProductCategoryPage.class);
	GenericHelper genericHelper;
	
	public String Black = "Black";
	public String Orange = "Orange";
	public String Yellow = "Yellow";
	
	@FindBy(xpath="//*[@id='layered_block_left']/p")
	private WebElement catalogTextObj;
	
	@FindBy(xpath="//*[@id='layer_cart']/div[1]/div[1]/h2")
	private WebElement productAddedSucessfully;
	
	@FindBy(xpath="//*[@id='center_column']/ul/li[4]/div/div[2]/div[2]/a[1]/span")
	private WebElement addToCart;
	
	@FindBy(xpath="//*[@id='layer_cart']/div[1]/div[2]/div[4]/a/span")
	private WebElement proceedToCheckOut;
	
	@FindBy(xpath="//*[@id='center_column']/ul/li")
	private List<WebElement> totalProducts;
	
	@FindBy(xpath="//*[@id='selectProductSort']")
	private WebElement sortBy;
	
	@FindBy(xpath="//*[@id='center_column']/ul/li/div/div[2]/div/span[1]")
	private List<WebElement> allpriceElements;
	
	
	public ProductCategoryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		genericHelper = new GenericHelper(driver);
		genericHelper.waitForElement(driver, catalogTextObj,new Config(TestBase.OR).getExplicitWait());
	}
	
	public void clickOnAddToCart(){
		log.info("clickin on add to cart");
		addToCart.click();
	}

	public void clickOnProceedTocheckOut(){
		log.info("clickin on :"+proceedToCheckOut.getText());
		proceedToCheckOut.click();
	}
	
	/**
	 * To Select color from product category page
	 * @param data
	 */
	public void selectColor(String data){
		new GenericHelper(driver).scrollIntoView(driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]/parent::*/preceding-sibling::input[1]")));
		driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]/parent::*/preceding-sibling::input[1]")).click();
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

/**
 * To select first product	
 */
	public void selectFirstProduct() {
		Actions obj = new Actions(driver);
		log.info("performning mouse over on first product of page..");
		obj.moveToElement(driver.findElements(By.xpath(".//*[@id='center_column']/ul/li")).get(0)).build().perform();
		log.info("clicking on add to basket..");
		driver.findElement(By.xpath(".//*[@id='center_column']/ul/li[1]/div/div[2]/div[2]/a[1]/span")).click();
	}
	/**
	 * To get total number of products
	 * @return
	 */
	public int getTotalProducts(){
		return totalProducts.size();
	}
		
	public boolean verifyProductAddedMsg() {
		boolean isDispalyed = false;
		try {
			WebElement element = genericHelper.waitForElement(driver, 2, productAddedSucessfully);
			 isDispalyed= element.isDisplayed();
			 log.info(productAddedSucessfully.getText()+" is dispalyed");
		}
		catch(Exception ex) {
			log.error("Element not found " + ex);
		}
		
		return isDispalyed;
	}
	
}
