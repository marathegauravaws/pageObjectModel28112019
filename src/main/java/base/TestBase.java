package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import customListner.WebEventListener;
import excelReader.Excel_Reader;

public class TestBase {
	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	public WebDriver driver;
	public WebDriverWait wait;
	public Properties prop;
	public ChromeOptions options;
	public DesiredCapabilities cap;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTest child;
	public ITestResult result;
	public Excel_Reader excel;

	public EventFiringWebDriver eventDriver;
	public WebEventListener webEventListner;
	By titleOfMenuSelected = By.xpath(".//*[@class='heading3']");

	/*
	 * public TestBase(WebDriver driver) { this.driver = driver; wait = new
	 * WebDriverWait(driver, 20); }
	 */
	public WebDriver getDriver() {
		return driver;

	}

	public void setDriver(EventFiringWebDriver driver) {
		this.driver = driver;
	}

	public boolean validateTitleOfPage(WebDriver driver, WebElement elem, String expectedTitle) {
		String actualTitle = elem.getText().trim();

		if (actualTitle.equals(expectedTitle)) {

			return true;
		}

		return false;

	}

	public String getTitleOfSelectedOption(WebDriver driver) {
		return driver.findElement(titleOfMenuSelected).getText().trim();

	}

	public void loadData() throws IOException {
		prop = new Properties();
		File file = new File(System.getProperty("user.dir") + "/src/main/java" + "/config/config.properties");
		FileInputStream f = new FileInputStream(file);
		prop.load(f);
	}

	public void selectBrowser(String browser) {
		System.out.println(System.getProperty("os.name"));
		if (browser.equals("chrome")) {
			options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.WINDOWS);
			driver = new ChromeDriver();
//	public WebEventListener eventListener;
			eventDriver = new EventFiringWebDriver(driver);
			webEventListner = new WebEventListener();
			eventDriver.register(webEventListner);
			setDriver(eventDriver);
		} else if (browser.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", "/Users/naveenkhunteta/Documents/SeleniumServer/geckodriver");
			driver = new FirefoxDriver();
		}
	}

	public void getUrl(String url) {
		log.info("navigating to :-" + url);
		driver.get(url);
		driver.manage().window().maximize();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void initialization() throws IOException {
		loadData();
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		cap = new DesiredCapabilities();
		System.out.println(prop.getProperty("browser"));
		String browser = prop.getProperty("browser");
		String url = prop.getProperty("url");
		selectBrowser(prop.getProperty("browser"));
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		getUrl(url);
	}

	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir") + "/Reports/YashDemoReport"
				+ formater.format(calendar.getTime()) + ".html", false);
	}

	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		test.log(LogStatus.INFO, data);
	}

	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath()
					+ "./Screenshots/ScreenShot_";

			destFile = new File(
					(String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);

			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}

	public void getresult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP,
					result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}

	@AfterMethod()
	public void afterMethod(ITestResult result) {
		getresult(result);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {		
		  test = extent.startTest(result.getName().toUpperCase());
		  test.log(LogStatus.INFO, result.getName() + " test Started");		 
	}

	@AfterClass(alwaysRun = true)
	public void endTest() {
		closeBrowser();
	}

	public void closeBrowser() {
		// driver.quit();
		log.info("browser closed");
		extent.endTest(test);
		extent.flush();
	}

	public String[][] getData(String excelName, String sheetName) {
		String path = System.getProperty("user.dir") + "/src/main/java/data/" + excelName + ".xlsx";
		// "C:\Users\gaurav.marathe\eclipse-workspace\demo\src\main\java\data\TestData.xlsx"
		excel = new Excel_Reader(path);
		String[][] data = excel.getDataFromSheet(sheetName, excelName);
		return data;
	}

	public String getDataFromCell(String sheetName, String colName, int rowNum) {
		String path = System.getProperty("user.dir") + "/src/main/java/data/" + "TestData" + ".xlsx";
		// "C:\Users\gaurav.marathe\eclipse-workspace\demo\src\main\java\data\TestData.xlsx"
		excel = new Excel_Reader(path);
		String data = excel.getCellData(sheetName, colName, rowNum);
		return data;
	}

}
