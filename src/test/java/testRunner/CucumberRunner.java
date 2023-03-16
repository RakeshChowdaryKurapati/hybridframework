package testRunner;

import org.junit.runner.RunWith;
import org.testng.annotations.AfterSuite;

import extentReports.ExtentManager;
import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@RunWith(Cucumber.class)
@CucumberOptions(features ="C:\\Users\\kurapati.naidu\\Downloads\\book_shop\\src\\test\\resources\\Features",
glue = {"StepDefinations"},monochrome = true,
dryRun = true,
plugin = {"pretty","html:target/HtmlReports/HtmlReports.html"
})

public class CucumberRunner extends AbstractTestNGCucumberTests{
	@AfterSuite
	public void generateReport(){
		ExtentManager.createExtentReports(null);
}
	
}
