package cf_category;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;


public class LinksTest extends itemVerification {
	String desc = "To verify whether Twitter/Youtube/linkedIN Link is navigated to Twitter/Youtube/LinkedIN Page";
	int rowNo=29;
	static String oldTab;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
		  
	}

	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 29 : LinkTest  Starting :- **************");
		openBrowser();
		openURL() ;	
		login();		
		
	}
	@Test(priority=2,dependsOnMethods = "setUp")
	public void verifyTwiterLink() throws InterruptedException
	{
		Thread.sleep(2000);
		scroll();
		oldTab = driver.getWindowHandle();
		clickLink("twitter");
		Thread.sleep(5000);
		switchWin();
		Thread.sleep(5000);
		verifyLink();	
		 driver.close();
		driver.switchTo().window(oldTab);
	}
	
	@Test(priority=3,dependsOnMethods = "setUp")
	 public void VerifyYouTubeLink() throws InterruptedException
		{
		scroll();
	       clickLink("youTube");
	       Thread.sleep(5000);
	       switchWin();
		   Thread.sleep(5000);
		   verifyLink();
		   
		   driver.close();
		   driver.switchTo().window(oldTab);
		}
	@Test(priority=4,dependsOnMethods = "setUp")
	public void verifyLinkedInLink() throws InterruptedException
	{
		scroll();
	    clickLink("linkedIn");
	    Thread.sleep(5000);
	    switchWin();
		Thread.sleep(5000);
		verifyLink();		
		
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
		TestLog.info("************** Test Case 29  LinksTest  ENDS  :- **************");
	}	

	
}
