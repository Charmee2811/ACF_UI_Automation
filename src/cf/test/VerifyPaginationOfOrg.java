package cf.test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;

public class VerifyPaginationOfOrg extends itemVerification {
	String desc = " To verify Pagination of an Organization on Dashboard ";
	int rowNo=31;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=1)
	public void setUp() throws Exception 
	{
		TestLog.info("*********Test Case to Verify Pagination of an Organization on Dashboard :- **********");
		openBrowser();
		openURL();
		login();
	}

	
	@Test(priority=2)
	public void Pagination() throws InterruptedException, IOException
	{
		Thread.sleep(4000);
		scrollTillPagination();
		VerifyPagination("firstOrg", "Organization",  "pagination" );
		
	}
		
	@AfterMethod(alwaysRun=true)
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
		TestLog.info("************** Test Case 31 Verify Pagination of an Organization on Dashboard :- **************");
	}
	
}


