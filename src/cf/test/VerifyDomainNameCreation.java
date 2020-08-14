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

public class VerifyDomainNameCreation extends itemVerification {
	
	String desc = " To verify whether a user can create a domain from console ";
	int rowNo=27;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	 @Test(priority=1)		
	   public void setUp() throws Exception
			{
		       TestLog.info("************** Test Case 27 VerifyDomainNameCreation Starting :- **************");
				openBrowser();
				openURL() ;
				login();	
			}
	//Creation of Organisation for admin login
	@DataProvider(name="creatOrg")
	   public Object[][] creatOrg() throws BiffException
	       {
			 Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyDomainNameCreation.xls","organization");
			 return arrayObject;
			}
			
	@Test(priority=2, dependsOnMethods ="setUp",dataProvider = "creatOrg")
	 	public void orgCreation(String orgName) throws InterruptedException
			{
				creatOrg(orgName);
				Thread.sleep(1500);
			}

	@DataProvider(name="createDomain")
	public Object[][] createDomain() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyDomainNameCreation.xls","domain");
			return arrayObject;
	}
	
	@Test(priority=3,dataProvider = "createDomain")
	public void domainNameCreation(String domainName) throws InterruptedException
	{
		scroll();
		verifyDomainCreation(domainName);
			
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
		TestLog.info("************** Test Case 27 VerifyDomainNameCreation ENDS :- **************");
	}


	
	
	
	
	
}