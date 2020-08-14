package cf.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.TestBase;
import cf.Base.itemAction;
import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

//Verify CF products details and its usage are available on the dashboard page at the bottom. It should be in different link
public class verifyCFProductDetails extends itemVerification
{
		
	public String orgurl;
	public String spaceurl;
	public String marketurl;
	int rowNo=9;
	public String failSCPath="";
	
	public String desc="Verify CF products details and its usage are available on the dashboard page at the bottom. It should be in different link";

	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}

	    @Test(priority=1)
		public void setUp() throws Exception
		{
	    	TestLog.info("************** Test Case 07  verifyCFProductDetails Starting :- **************");
			openBrowser();
			openURL() ;
			login();	
		}
		
		@Test(priority=2,dependsOnMethods ="setUp")
		public void gettingStartedTab() throws InterruptedException
		{

			int i =driver.findElements(By.xpath((or_getproperty("gettingstarted")))).size();
			if(i>0)
			{
				driver.findElement(By.xpath((or_getproperty("gettingstarted")))).click();
				String getting=driver.getCurrentUrl();
				
				Assert.assertTrue(getting.contains("getting_started"));
				Thread.sleep(1200);
				String app=driver.findElement(By.xpath((or_getproperty("firsrapptest")))).getText();
				result="true";
				Assert.assertTrue(app.contains("Your first app"));		
			}
			else
				result = "false";
			
		    Thread.sleep(9000L);
	    
		}
		
		@DataProvider(name="creatOrg")
		public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyCFProductDetails.xls","organization");
			return arrayObject;
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
			TestLog.info("************** Test Case 07  verifyCFProductDetails ENDS :- **************");
		}
		
   }


