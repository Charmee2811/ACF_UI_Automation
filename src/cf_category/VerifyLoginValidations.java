package cf_category;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;

public class VerifyLoginValidations extends itemVerification{
	String desc = " To verify whether user is able o login with blank email and pwd/Correct email and Blank Password/Blank Email and Correct password/Correct crendentials  using selenium Script";
	int rowNo=28;
	
	
	@BeforeClass
	public void isTestExecutableCall()
	{

		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}

//Scenario 1 : verify whether user is able to login with blank email & blank pwd
	@Test(priority= 1)	
	public void verifyLoginWithNoCreds() throws InterruptedException, IOException
		{
	       TestLog.info("************** Test Case 28 VerifyLoginValidations Starting  :- **************");
			openBrowser();
			openURL() ;
			Thread.sleep(3000L);
			VerifyLoginWithNoCreds();	
		}
  
//Scenario 2 : verify whether user is able to login with correct email & blank pwd 
	@Test(priority=2)
	public void verifyUserLoginWithCorrectEmailAndBlankPaswd() throws InterruptedException
	 {
		   verifyUserLogin("adminUsername","","Login-Password");
		   Thread.sleep(500); 
	 }
	 
//Scenario 3 : verify whether user is able to login with blank email & correct pwd 
	 @Test(priority=3)
	 public void verifyLoginWithBlankEmailAndCorrectPaswd() throws InterruptedException
	 {     driver.navigate().refresh();
		   verifyUserLogin("","adminIncorrectPwd","Login-Username");
		   Thread.sleep(1000);
	 }
//Scenario 4 : verify whether user is able to login with correct email & Incorrect pwd
     
  @Test(priority=4)
  public void verifyLoginWithCorrectEmailAndIncorrectPaswd() throws InterruptedException
  {     driver.navigate().refresh();
        verifyUserLogin("adminUsername","adminIncorrectPwd","Incorrect-Password");
        Thread.sleep(1000);
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
 	public void generateReport()
 	{
 		generateReport(rowNo,desc);	
 		TestLog.info("************** Test Case 28  VerifyLoginWithNoCredentials  ENDS  :- **************");
 	}	

}
