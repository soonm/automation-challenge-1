package demo.resources;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listeners implements ITestListener{
    ExtentTest test;
    ExtentReports extent=ExtentReporterNG.getReportObject();
    
    public void onTestStart(ITestResult result) {
        test=extent.createTest(result.getMethod().getMethodName());
    }
    
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }
    
    public void onTestFailure(ITestResult result) {
    	WebDriver driver=null;
    
    	try {
    	    driver =(WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver")
    	            .get(result.getInstance());
    	} catch (Exception e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
    
    	test.log(Status.FAIL, "Test Failed");
    	String filepath=null;
    	
    	try {
    	    filepath = Keywords.takeScreenShot(driver, result.getMethod().getMethodName());
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	
    	test.addScreenCaptureFromPath(filepath,result.getMethod().getMethodName());
    	test.fail(result.getThrowable());
    }
    
    public void onTestSkipped(ITestResult result) {
    	// TODO Auto-generated method stub
    }
    
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    	// TODO Auto-generated method stub
    }
    
    public void onTestFailedWithTimeout(ITestResult result) {
    	// TODO Auto-generated method stub
    }
    
    public void onStart(ITestContext context) {
    	// TODO Auto-generated method stub
    }
    
    public void onFinish(ITestContext context) {
    	extent.flush();
    }
}
