package extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
    public static final ExtentReports extentReports = new ExtentReports();
    
    
    public synchronized static ExtentReports createExtentReports(String fileName) {
    	
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setDocumentTitle(fileName);
        reporter.config().setEncoding("utf-8");
        reporter.config().setReportName(fileName);
        reporter.config().setReportName("Sample Extent Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Automation", "Hybrid");
        extentReports.setSystemInfo("Rakesh", "Luma");
        return extentReports;
    }
}
