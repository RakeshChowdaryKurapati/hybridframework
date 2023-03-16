package listeners;

import java.io.IOException;

import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import static extentReports.ExtentTestManager.getTest;
import base.Base;
import logs.Log;
import utility.ScreenShot;

public class TestNgListeners extends ScreenShot implements ITestListener,ISuiteListener {

	private static String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }

	@Override 
	public void onTestStart(ITestResult result) {
		ScreenShot scn = new ScreenShot();
		try {
			scn.getScreenShot();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Test case started is : " + result.getName());
	
		Log.info(getTestMethodName(result) + " test is starting.");
	
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ScreenShot scn = new ScreenShot();
		try {
			scn.getScreenShot();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Test case pass is : " + result.getName());
		Log.info(getTestMethodName(result)+ "test is succeed");
		
	}
	
	@Override
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		ScreenShot scn = new ScreenShot();
		if (result.getStatus() == ITestResult.FAILURE)
		try {
			scn.getScreenShot();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		Log.info(getTestMethodName(result) + " test is failed.");

	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		
		 Log.info(getTestMethodName(result) + " test is skipped.");
		 
		ScreenShot scn = new ScreenShot();
		try {
			scn.getScreenShot();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	        
	        getTest().log(Status.SKIP, "Test Skipped");
	        
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
		Log.info("Test failed but it is in defined success ratio " +getTestMethodName(result));
		
		ScreenShot s=new ScreenShot();
		try {
			s.getScreenShot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 getTest().log(Status.INFO, "Test failed but it is in defined success ratio");
		
	}
	
	
	
	
	
	
	
	
	
	
	}
	

