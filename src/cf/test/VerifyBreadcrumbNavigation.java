package cf.test;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;

public class VerifyBreadcrumbNavigation extends itemVerification {
	String desc = " To verify the breadcrumb navigation for console ";
	int rowNo=26;
	
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());		
		isTestExecutableBaseClass(this.getClass().getSimpleName());
		 
	}
	
	@Test(priority=1)
	public void setUp() throws Exception 
	{
		TestLog.info("*********Test Case 26 VerifyBreadcrumbNavigation Starting :- **********");
		openBrowser();
		openURL();
		login();
	}

	
	@Test(priority=2)
	public void VerifyGettingStartedNavigation() throws InterruptedException
	{
		CheckBreadcrumbNavigation("gettingstarted","gettingStartedPageLink");
		
	}
	
	@Test(priority=3)
	public void VerifyDashboardNavigation() throws InterruptedException
	{
		CheckBreadcrumbNavigation("dashboardLink","organizationDashboard");
		
	}
	
	
	@Test(priority=4)
	public void VerifyEmailNavigation() throws InterruptedException
	{
		CheckEmailLink("emailLink");
		
	}
	
	@Test(priority=5)
	public void VerifyOrganizationNavigation() throws InterruptedException
	{
		CheckBreadcrumbNavigation("organizationLink","organizationDropdownlist");		
	}
	
	
	@Test(priority=6)
	public void VerifyLanguageChangeNavigation() throws InterruptedException
	{
		CheckLanguageChange("languageChangeLink","frenchLink","frenchLanguage");
		
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
		TestLog.info("************** Test Case 26 VerifyBreadcrumbNavigation ENDS :- **************");
	}

	

}

