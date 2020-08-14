package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class VerifyLoginValidation extends itemVerification {
	
	String desc = " Verify with the Login Validations ";
	int rowNo=46;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	 @Test(priority=1)		
	   public void setUp() throws InterruptedException, IOException
			{
		       TestLog.info("************** Test Case 46 Verify with the Login Validations Starting :- **************");
				openBrowser();
				openURL() ;
			
			}
	 
// Scenario 1 : verify whether user is able to login with correct email & blank pwd 
	 @Test(priority=2)
	 public void verifyUserLoginWithCorrectEmailAndBlankPaswd() throws InterruptedException
	 {
		   verifyUserLogin("adminUsername","","Login-Password");
		   Thread.sleep(500); 
	 }
	 
// Scenario 2 : verify whether user is able to login with blank email & correct pwd 
	 @Test(priority=3)
	 public void verifyLoginWithBlankEmailAndCorrectPaswd() throws InterruptedException
	 {     driver.navigate().refresh();
		   verifyUserLogin("","adminPasswd","Login-Username");
		   Thread.sleep(1000);
	 }
	 
	 @AfterMethod (alwaysRun=true)
		public void screenshotCall(ITestResult res) throws IOException
		 {
			String methodName = res.getMethod().getMethodName();
		    String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName();
		    String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
		    screenshot(failSCPath,passSCPath);   
		     ExtntRep(methodName,res);  
		}
		
		
		@AfterTest //(alwaysRun=true)
		public void generateReportCall()
		{	
			generateReport(rowNo,desc);	 
			TestLog.info("************** Test Case 46  Verify with the Login Validations ENDED :- **************");
		}


	
}
