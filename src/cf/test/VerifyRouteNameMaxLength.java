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

public class VerifyRouteNameMaxLength extends itemVerification{
 
	String desc = " To validate max length of the available character in text boxes while creating ROUTE using selenium Script";
	int rowNo=24;
	
	
	
	@BeforeClass
	public void isTestExecutableCall()
	{	
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());	
	}
	
	
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 24 :  VerifyRouteNameMaxLength Starting :- **************");
		openBrowser();
		openURL() ;
		login();		
	}
	
	
   //compatibility validation for new chrome version 
 	@DataProvider(name="traversTillApp")
	public Object[][] traversTillApp() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyRouteNameMaxLength.xls","route");
			return arrayObject;
	}
	
		
	@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "traversTillApp")
	public void validateRouteNameMaxLength(String orgName, String space, String app, String hostName,String preprodDominName,String devDoaminName) throws Exception
	{
		invalidRouteNameLength(orgName,space,app, hostName, devDoaminName,preprodDominName);
		TestLog.info("Invalid routeNameMaxLength is verified");
			
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
		TestLog.info("************** Test Case 24  ENDS  VerifyRouteNameMaxLength:- **************");
		
	}	


	
}
