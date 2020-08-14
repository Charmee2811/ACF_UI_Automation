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

public class VerifyMaxSpaceNameLength extends itemVerification {
	String desc = " To validate max length of the available character in text boxes while creating an space ";
	int  rowNo = 18;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());	
	}
	
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 18 :  VerifyMaxSpaceNameLength Starting :- **************");
		openBrowser();
		openURL() ;
		login();
	}
	
	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyMaxSpaceNameLength.xls","organization");
			return arrayObject;
	}
	@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
	public void orgCreation(String orgName) throws InterruptedException
	{
		creatOrg(orgName);	
	}
	
	@DataProvider(name="creatSpace")
	public Object[][] creatSpace() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyMaxSpaceNameLength.xls","Space");
			return arrayObject;
	}
	
	@Test(priority=3,dependsOnMethods ="orgCreation",dataProvider = "creatSpace")
	public void spaceNameValidation(String ValidspaceName,String InvalidspaceName) throws Exception
	{
	   result = "false";
		validateMaxNameLen(ValidspaceName,"createSpaceLink" , "spaceName" , "spaceCancleCross"); 
		inValidateMaxNameLen(InvalidspaceName,"createSpaceLink" , "spaceName"); 
		Thread.sleep(2000);
	}
	@AfterMethod //(alwaysRun=true)
	public void screenshotCall(ITestResult res ) throws IOException
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
		TestLog.info("************** Test Case 18  ENDS  VerifyMaxSpaceNameLength:- **************");
		
	}	
}
