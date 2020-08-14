package cf.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;

public class VerifyFAQandUserGuidLinks extends itemVerification {
	
	String desc = " Verify FAQ and User Guide links are working using Selenium Script ";
	int rowNo=34;

	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());	
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	
  	@Test(priority=1)
	public void setUp() throws Exception
	{
       TestLog.info("************** Test Case 46  Verify User role admin control panel using selenium Script Starting :- **************");
		openBrowser();
		openURL() ;
	    login();
	}
  		
	@Test(priority=2)
	public void verifyFAQLink()  throws InterruptedException
	{
		verifyFaqAndUserGuideLinks( "faqlink", "faqTitle", "FaqPage", "explandlist", "Faq Page");
	}
	
	

	@Test(priority=3)
	public void verifyUserGuideLink()  throws InterruptedException
	{
		driver.close();
        driver.switchTo().window(mainWindow);
 		Thread.sleep(200);
		verifyFaqAndUserGuideLinks("userguidelinkonDashboard", "userGuideTitle", "UserGuidePage", "explandlistUserguide", "User Guide Page");
	}
	
	
	@AfterMethod(alwaysRun=true)
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
		TestLog.info("**************Test Case 44 Verify FAQ and User Guide links are working using Selenium Scrip ENDS :- **************");
	}	
	
	
}
