package com.automationFramework.helper;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

/**
 * GenericHelper class is to have generic methods
 * 
 * @author sangale_d
 */
public class GenericHelper {

	private WebDriver driver;
	private static final Logger log = LoggerHelper.getLogger(GenericHelper.class);

	public GenericHelper(WebDriver driver) {
		this.driver = driver;
		log.debug("WaitHelper : " + this.driver.hashCode());
	}

	/**
	 * Validate that element displayed
	 * 
	 * @param element
	 * @return
	 */
	public boolean isDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			log.info("element is displayed.." + element);
			return true;
		} catch (Exception e) {
			log.info(e);
			Reporter.log(e.fillInStackTrace().toString());
			return false;
		}
	}

	protected boolean isNotDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			log.info("element is displayed.." + element);
			return false;
		} catch (Exception e) {
			log.error(e);
			Reporter.log(e.fillInStackTrace().toString());
			return true;
		}
	}

	/**
	 * Select value from visible text
	 * 
	 * @param element
	 * @param visibleValue
	 */
	public void SelectUsingVisibleValue(WebElement element, String visibleValue) {
		Select select = new Select(element);
		select.selectByVisibleText(visibleValue);
	}

	public String getSelectedValue(WebElement element) {
		String value = new Select(element).getFirstSelectedOption().getText();
		return value;
	}

	/**
	 * Select value from index
	 * 
	 * @param element
	 * @param visibleValue
	 */
	public void SelectUsingIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	/**
	 * Select value from visible text
	 * 
	 * @param element
	 * @param visibleValue
	 */
	public void SelectUsingVisibleText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}
/**
 * Get all dropdown values
 * @param locator
 * @return
 */
	public List<String> getAllDropDownValues(WebElement locator) {
		Select select = new Select(locator);
		List<WebElement> elementList = select.getOptions();
		List<String> valueList = new LinkedList<String>();

		for (WebElement element : elementList) {
			valueList.add(element.getText());
		}
		return valueList;
	}

	/**
	 * To add implicit wait
	 * 
	 * @param timeout
	 */
	public void setImplicitWait(long timeout) {
		log.info(timeout);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
	}

	/**
	 * To set page load timeout
	 * 
	 * @param timeout
	 */
	public void setPageLoadTimeout(long timeout) {
		log.info(timeout);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
	}

	private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMiliSec) {
		log.info(timeOutInSeconds);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
		wait.pollingEvery(Duration.ofMillis(pollingEveryInMiliSec));
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}

	public void waitForElementVisible(WebElement locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		log.info(locator);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(ExpectedConditions.visibilityOf(locator));
	}
/**
 * Wait for element till it's visible
 * @param driver
 * @param element
 * @param timeout
 */
	public void waitForElement(WebDriver driver, WebElement element, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.visibilityOf(element));
		log.info("element found..." + element.getText());
	}
/**
 * Wait for element till it's clickable
 * @param driver
 * @param time
 * @param element
 * @return
 */
	public WebElement waitForElement(WebDriver driver, long time, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Ti execute script using JavascriptExecutor class
	 * @param script
	 * @param args
	 * @return
	 */
	public Object executeScript(String script, Object... args) {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		log.info(script);
		return exe.executeScript(script, args);
	}

	public void scrollIntoView(WebElement element) {
		executeScript("arguments[0].scrollIntoView()", element);
		log.info(element);
	}

	/**
	 * To scroll down vertically
	 */
	public void scrollDownVertically() {
		executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * this method will switchToFrame based on frame index
	 * 
	 * @param frameIndex
	 */
	public void switchToFrame(WebElement element) {
		driver.switchTo().frame(element);
		log.info("switched to :" + element + " frame");
	}

	/**
	 * To switch to default/parent frame
	 */
	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}
}
