package com.automationFramework.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.automationFramework.helper.GenericHelper;
import com.automationFramework.testBase.Config;
import com.automationFramework.testBase.TestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * 
 * @author sangale_d
 *
 */
public class TestBase {

	public static final Logger logger = Logger.getLogger(TestBase.class.getName());
	public WebDriver driver;
	public static Properties OR;
	public File f1;
	public FileInputStream file;
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;

	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy");

		extent = new ExtentReports(System.getProperty("user.dir") + "/extentReports"
				+"/"+ formater.format(calendar.getTime()) +"/"+calendar.getTimeInMillis()+ ".html", false);
	
	}

	/**
	 * To load the properties file
	 * @throws IOException
	 */
	public void loadPropertiesFile() throws IOException {

		String log4jConfPath = System.getProperty("user.dir") + "/src/main/resources/config/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		OR = new Properties();
		f1 = new File(System.getProperty("user.dir") + "/src/main/resources/config/config.properties");
		file = new FileInputStream(f1);
		OR.load(file);
		logger.info("loading config.properties.");

	}

	@BeforeTest
	public void launchBrowser() {
		try {
			loadPropertiesFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Config config = new Config(OR);
		getBrowser(config.getBrowser());
		GenericHelper genericHelper = new GenericHelper(driver);
		genericHelper.setImplicitWait(config.getImplicitWait());
		genericHelper.setPageLoadTimeout(config.getPageLoadTimeOut());
		extent.addSystemInfo("Host Name","Windows");
		extent.addSystemInfo("User Name","sangale_d");
		extent.addSystemInfo("Environment","QA");		
	}

	/**
	 * WebDriverManager class allows to download and set the browser driver binaries 
	 * @param browser
	 */
	public void getBrowser(String browser) {
		if (System.getProperty("os.name").contains("Windows")) {
			if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}
		}
	}

	/**
	 * Get screenshot and copy to the screenshots folder
	 * @param imageName
	 * @return
	 * @throws IOException
	 */
	public String getScreenShot() throws IOException {

		File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imagelocation = System.getProperty("user.dir") + "/screenshots/";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImageName = imagelocation + "_" + formater.format(calendar.getTime()) + ".png";
		File destFile = new File(actualImageName);
		FileHelper.copyFile(image, destFile);
		return actualImageName;
	}

	/**
	 * Get test result and log it in test report
	 * @param result
	 * @throws IOException
	 */
	public void getresult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is pass");
			String screen = getScreenShot();
			test.log(LogStatus.PASS, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP,
					result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getName() + " test is failed" + result.getThrowable());
			String screen = getScreenShot();
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}

	@AfterMethod()
	public void afterMethod(ITestResult result) throws IOException {
		getresult(result);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}

	/**
	 * to end selenium session
	 */
	@AfterTest(alwaysRun = true)
	public void endTest() {
		driver.quit();
		extent.endTest(test);
		extent.flush();
	}


}
