package cf.test;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;

public class VerifySettingPageAndSocial_Icons extends itemVerification {
	String desc = " To verify setting page and social icons";
	int rowNo=29;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		//test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	
	@Test(priority=1)
	public void setUp() throws Exception 
	{
		TestLog.info("*********Test Case 15  VerifySettingPageAndSocial_Icons Starting :- **********");
		openBrowser();                                                                                                                                                                                                        
		openURL();
		login();		
	}
	
	@Test(priority=2)
	public void settingPage() throws InterruptedException
	{
		Thread.sleep(2000);
		verifySettingPage();
	}
	
	@Test(priority=3)
	public void socialIcons() throws InterruptedException
	{
		driver.findElement(By.xpath(or_getproperty("settingLink"))).click();
		scroll();
		verifySocialIcons();
	}
	
	@AfterMethod //(alwaysRun=true)
	public void screenshotCall(ITestResult res) throws IOException
	 {
		String methodName = res.getMethod().getMethodName();
	      String failSCPath= config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
	     String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
	     screenshot(failSCPath,passSCPath); 
	   
	    
	    //ExtntRep(methodName,res);      
	}
	
	
	@AfterTest(alwaysRun=true)
	public void generateReportCall()
	{	
		generateReport(rowNo,desc);	 
		TestLog.info("************** Test Case 15  VerifySettingPageAndSocial_Icons ENDS :- **************");
	}


}
