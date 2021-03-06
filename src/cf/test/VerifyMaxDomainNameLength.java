package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class VerifyMaxDomainNameLength extends itemVerification{
	String desc = "To validate max length of the available character in text boxes while creating a Domain Name";
	int rowNo=22;
	
	
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	
	}
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 22 :  VerifyMaxDomainNameLength Starting :- **************");
		openBrowser();
		openURL() ;
		login();
	}
	//Creation of Organisation  for User
	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyMaxDomainNameLength.xls","organization");
			return arrayObject;
	}	
	@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
	public void orgCreation(String orgName) throws InterruptedException
	{
			creatOrg(orgName);		
	}
	//validate max domain name length
	
	@DataProvider(name="creatDomain")
	public Object[][] creatDomain() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyMaxDomainNameLength.xls","domain");
			return arrayObject;
	}
	
	@Test(priority=2,dependsOnMethods ="orgCreation",dataProvider = "creatDomain")
	public void domainNameLengthValidation(String domainName) throws Exception
	{
			inValidDomainName(domainName);
			
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
		TestLog.info("************** Test Case 22  ENDS  VerifyMaxDomainNameLength:- **************");
	
	}	
	
}