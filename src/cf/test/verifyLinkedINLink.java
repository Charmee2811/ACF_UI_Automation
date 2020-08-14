
package cf.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemAction;
import cf.Base.itemVerification;
import cf_Util.TestLog;

//  To verify whether  "LinkedIN" Link is navigated to LinkedIN Page
public class verifyLinkedINLink extends itemVerification
{
	String desc = "To verify whether LinkedIN Link is navigated to LinkedIN Page";
	int rowNo =7;
	
	
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=1)
		public void setUp() throws Exception
		{
		 TestLog.info("************** Test Case  07  verifyLinkedINLink Starting :- **************");
			openBrowser();
			openURL() ;
			 Thread.sleep(1000);	
			login();	
		}
		
	@Test(priority=2,dependsOnMethods = "setUp")
		public void linkedInLink() throws InterruptedException
		{
		    clickLink("linkedIn");
		    switchWin();
			Thread.sleep(3000);
			verifyLink();	
			//Assert.assertTrue(false);
		}
		
	
	@AfterMethod //(alwaysRun=true)
	public void screenshotCall(ITestResult res) throws IOException
	 {
		System.out.print(res);
		String methodName = res.getMethod().getMethodName();
	     String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName();
	     String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
	    screenshot(failSCPath,passSCPath);  
	   // String screenshots = config_getproperty("screenshots") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
	    //CaptureScreen(driver, screenshots);
	   ExtntRep(methodName,res);      
	}
	
	@AfterTest(alwaysRun=true)
	public void generateReportCall()
	{	
		generateReport(rowNo,desc);	     
		TestLog.info("************** Test Case 05 ENDS verifyLinkedINLink :- **************");
	}
	
}


