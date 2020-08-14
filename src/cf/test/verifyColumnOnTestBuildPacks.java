
package cf.test;

import java.io.File;

import cf_Util.TestLog;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemAction;
import cf.Base.itemVerification;

//To verify whether  On Test console build packs section sr.no column is  visible:- 
public class verifyColumnOnTestBuildPacks extends itemVerification
{
	private static Logger Log = Logger.getLogger(Logger.class.getName());
	
	String desc = "To verify whether On Test console build packs section sr.no column is  visible:- ";
	int rowNo = 10;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}

	@Test(priority=1)
		public void setUp() throws Exception
		{
		  TestLog.info("************** Test Case 08  verifyColumnOnTestBuildPacks Starting :- **************");
		 
			openBrowser();
			openURL() ;
			
			login();		
		}
		
	@Test(priority=2,dependsOnMethods = "setUp")
		public void verifySrNOClmn() throws InterruptedException
		{
			scroll();
			Thread.sleep(1000);
			verifySrNo();
			
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
		TestLog.info("************** Test Case 08 verifyColumnOnTestBuildPacks  ENDS Here :- **************"); 
	}
}




