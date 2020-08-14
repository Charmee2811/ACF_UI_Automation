package cf.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import cf.Base.itemVerification;
import cf_Util.TestLog;

//To verify disclaimer about supported browsers
public class verifyBrowserDisclaimer  extends itemVerification
{
	String desc = "To verify disclaimer about supported browsers";
	int rowNo=16;

	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}

	
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 14  verifyBrowserDisclaimer Starting :- **************");
		openBrowser();
		openURL() ;
		login();	
		
	}
	
   @Test(priority=2,dependsOnMethods = "setUp")
	public void disclaimertext() throws InterruptedException
	{
		Thread.sleep(1000);
		scroll();
		String disclaimerMsg= config_getproperty("disclaimerMsg");
		verifyMessage("disclaimer",disclaimerMsg);;	
	}
	
   @AfterMethod //(alwaysRun=true)
   public void screenshotCall(ITestResult res) throws IOException
   {
	    String methodName = res.getMethod().getMethodName();
	    String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
	    String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
	    screenshot(failSCPath,passSCPath); 
	    ExtntRep(methodName,res);   
   }


    @AfterTest(alwaysRun=true)
    public void generateReportCall()
    {	
	  generateReport(rowNo,desc);	   
	  TestLog.info("************** Test Case 14  verifyBrowserDisclaimer ENDS :- **************");
    }
  }
