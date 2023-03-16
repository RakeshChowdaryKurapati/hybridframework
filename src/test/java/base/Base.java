package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.exec.OS;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import logs.Log;
import utility.PropertyFileUtil;
import utility.ReadJsonData;


public class Base {
	public static ReadJsonData testDataFrmJson = null;
	public static ReadJsonData readJsonData = null;
	public static Collection<String> scenarioTags = null;
	public static String testDataPath = null;
	public static String testDataName = null;
	public static int testDataIndexNumber;
	public static String appURL;

	public static String mainWinHandleBefore;

	protected static Properties testProp;
	public static WebDriver driver;
	
	
	public WebDriver getDriver() {
        return driver;
    }

	public static void getTestProperties(String propertyFilePath) {
		testProp = new PropertyFileUtil().readProperty(propertyFilePath);
	}

	public static void setUpScenarioTagName(Scenario scenario) {
		scenarioTags = scenario.getSourceTagNames();
	}

	public static void setUpTestDataPath() {
		if (scenarioTags.contains("@UAT")) {
			testDataPath = OS.isFamilyWindows() ? System.getProperty("user.dir") + ".//HybridFramework/Testdata/QAtestData.json"
					: System.getProperty("user.dir") + ".//HybridFramework/Testdata/UATtestData.json";
		} else {
			testDataPath = OS.isFamilyWindows() ? System.getProperty("user.dir") + ".//HybridFramework/Testdata/QAtestData.json"
					: System.getProperty("user.dir") + ".//HybridFramework/Testdata/UATtestData.json";
		}
	}

	public static void initializeTestData(String testDataFilePath) throws IOException {
		testDataFrmJson = new ReadJsonData();
		testDataFrmJson.getTestDataFromJson(testDataFilePath);
	}

	public void launchApp() {	
		String browserName=testProp.getProperty("browser");

		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}else Assert.fail(
				"Driver Initializing fail: Please enter one of the webBrowser value: safari, chrome, opera, firefox, edge");

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(testProp.getProperty("testURL"));

	}
	public void navigateToURL(String URL) {
		System.out.println("Navigating to: " + URL);
		System.out.println("Thread id = " + Thread.currentThread().getId());

		try {
			driver.navigate().to(URL);
		} catch (Exception e) {
			System.out.println("URL did not load: " + URL);
			throw new TestException("URL did not load");
		}
	}
	public String getPageTitle() {
		try {
			System.out.print(String.format("The title of the page is: %s\n\n", driver.getTitle()));
			return driver.getTitle();
		} catch (Exception e) {
			throw new TestException(String.format("Current page title is: %s", driver.getTitle()));
		}
	}
	public List<WebElement> findElements(By locator){
		try {
			List<WebElement> element = driver.findElements(locator);
			return element;
		}
		catch (NoSuchElementException e){
			Log.error(this.getClass().getName() + "findElements" + "element not found" + locator);
			throw new NoSuchElementException(e.getMessage());
		}
	}

	public void enterText(By xpath, String text) {
		WebElement ele = driver.findElement(xpath);
		ele.click();
		ele.clear();
		ele.sendKeys(text);
	}

	public void enterTextAndSubmit(By xpath, String text) {
		WebElement ele = driver.findElement(xpath);
		ele.click();
		ele.clear();
		ele.sendKeys(text);
		//	ele.sendKeys(Keys.ENTER); "you can use either any one of these"
		ele.submit();
	}
	public void moveToTheElement(By xpath) {
		WebElement ele = driver.findElement(xpath);
		Actions actions = new Actions(driver);
		actions.moveToElement(ele);
	}
	public void click(By xpath) {
		WebElement ele = driver.findElement(xpath);
		Actions actions = new Actions(driver);
		actions.moveToElement(ele).click().perform();
	}
	public String getText(By xpath) {
		WebElement ele = driver.findElement(xpath);
		String text = ele.getText();
		return text;
	}
	public void acceptAlert(){
		try {
			Alert alert = driver.switchTo().alert(); 
			alert.accept();


		} catch (NoAlertPresentException e){
			throw new NoAlertPresentException();
		}	
	}
	public String getAlertText() 
	{ 
		try {
			Alert alert = driver.switchTo().alert(); 
			String alertText = alert.getText(); 
			return alertText; 
		} catch (NoAlertPresentException e){
			throw new NoAlertPresentException();
		}
	}   
	public boolean isAlertPresent() 
	{ 
		try 
		{ 
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert();
			return true; 
		}   
		catch (NoAlertPresentException e) 
		{   
			throw new NoAlertPresentException(); 
		}   
	}   
	public void selectValuefromDropdownviaIndex(By selectLocator, int valueToBeSelectedindex){
		Select  selectFromDropdown = new Select(driver.findElement(selectLocator));
		selectFromDropdown.selectByIndex(valueToBeSelectedindex);

	}

	 @AfterSuite
	    public void teardown() {
	        Log.info("Tests are ending!");
	        driver.quit();
	    }
}

