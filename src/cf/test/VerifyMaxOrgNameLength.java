package cf.test;

import java.io.File;
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.LogStatus;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;


public class VerifyMaxOrgNameLength extends itemVerification {
	String desc = " To validate max length of the available character in text boxes while creating an org ";
	int  rowNo = 17;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 17 :  VerifyMaxOrgNameLength Starting :- **************");
		openBrowser();
		openURL() ;	
		login();
		
	}
	
	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyMaxOrgNameLength.xls","organization");
		return arrayObject;
	}
		
	@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
	public void orgNameValidation(String ValidorgName,String InvalidorgName)  throws Exception 
	{
		validateMaxNameLen(ValidorgName,"createOrgLink" , "orgName" , "orgCancleCross"); 
		inValidateMaxNameLen(InvalidorgName,"createOrgLink" , "orgName"); 
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
		TestLog.info("************** Test Case 17  ENDS  VerifyMaxOrgNameLength:- **************");	
	}	
		
}
