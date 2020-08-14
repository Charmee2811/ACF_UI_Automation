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

public class VerifyText extends itemVerification{

	String desc = "To verify footer text/CopyrightMessage/cfProduct Details/Column On Test BuildPacks/disclaimer text ";
	int rowNo = 31 ;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=1)
		public void setUp() throws Exception
		{
		TestLog.info("************** Test Case 31  VerifyText Starting :- **************");
			openBrowser();
			openURL() ;
			login();
					
		}
		
	@Test(priority=2,dependsOnMethods = "setUp")
		public void VerifyfotterText() throws InterruptedException
		{
		TestLog.info("In fotter text");
			verifyFotterText();			
		}
	
	@Test(priority=3,dependsOnMethods = "setUp")
	public void VerifyCopyRightMessage() throws InterruptedException
	{
		TestLog.info("In copyright message");
		String 	copyRightMsg=config_getproperty("copyRightMsg")+ "\n" + config_getproperty("copyRightMsg2");   //Added "\n" as required in release-13 charmee -14 Dec
		//"\n" removed this from between as support and note together 7th nov-2017 charmee
		System.out.println("required copyright msg is ::"+copyRightMsg);
		TestLog.info("required copyright msg is ::"+copyRightMsg);
		Thread.sleep(1000);
		scroll();
		TestLog.info("Scrolled the web to get to locate the copyright text");
		verifyMessage("fotterLinks", copyRightMsg);
	}	
	
	@Test(priority=4,dependsOnMethods ="setUp")
	public void VerifycfProductDeatils() throws InterruptedException
	{
		TestLog.info("In verifyProduct Details");
		int i =driver.findElements(By.xpath((or_getproperty("gettingstarted")))).size();
		if(i>0)
		{
			driver.findElement(By.xpath((or_getproperty("gettingstarted")))).click();
			String getting=driver.getCurrentUrl();
			TestLog.info(getting +" contains getting_started");
			Assert.assertTrue(getting.contains("getting_started"));
			Thread.sleep(1200);
			String app=driver.findElement(By.xpath((or_getproperty("firsrapptest")))).getText();
			TestLog.info(app + " this text is present in your first app ");
			result="true";
			Assert.assertTrue(app.contains("Your first app"));		
		}
		else
		{
			result = "false";
			TestLog.info("getting started link not present ");
			Assert.assertFalse(false, "Link not present ");
		}
		
	    Thread.sleep(9000L);
    
	}
	
	@Test(priority=5,dependsOnMethods = "setUp")
	public void verifyColumnOnTestBuildPacks() throws InterruptedException
	{
		TestLog.info("In columnBuldpacks method");
		driver.navigate().back();
		scroll();
		TestLog.info("scrolled to locate the coloumnON BuildPack element");
		Thread.sleep(1000);
		verifySrNo();
		
	}
	
	

	   @Test(priority=6,dependsOnMethods = "setUp")
		public void disclaimertext() throws InterruptedException
		{
		   TestLog.info("In disclaimer text verification");
		   driver.navigate().back();
			Thread.sleep(1000);
			scroll();
			String disclaimerMsg= config_getproperty("disclaimerMsg");
			verifyMessage("disclaimer",disclaimerMsg);;	
		}
		
	@AfterMethod //(alwaysRun=true)
	public void screenshotCall(ITestResult res) throws IOException
	 {
		String methodName = res.getMethod().getMethodName();
	    String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName();
	    String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
	    screenshot(failSCPath,passSCPath);   
	    ExtntRep(methodName,res);   
	}
	
	
	@AfterTest(alwaysRun=true)
	public void generateReportCall()
	{	
		generateReport(rowNo,desc);	   
		TestLog.info("************** Test Case 31 VerifyText ENDS :- **************");
	}

}
