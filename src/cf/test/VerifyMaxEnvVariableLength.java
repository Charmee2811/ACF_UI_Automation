package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class VerifyMaxEnvVariableLength extends itemVerification{
 
	String desc = " To verify for the browsers compatibility for chrome for latest version   using selenium Script";
	int rowNo=23;
	
	
	
	@BeforeClass
	public void isTestExecutableCall()
	{	
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());		
	}
		
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 23 :  VerifyMaxEnvVariableLength Starting :- **************");
		openBrowser();
		openURL() ;
		login();	
	}
	
   //compatibility validation for new chrome version 
 	@DataProvider(name="traversTillApp")
	public Object[][] traversTillApp() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyMaxEnvVariableLength.xls","Environment");
			return arrayObject;
	}
		
	@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "traversTillApp")
	public void SetEnvVarNameTOMaxLength(String orgName, String space, String app, String VariableName,String Value) throws Exception
	{
		invalidEnvNameLength(orgName,space,app, VariableName, Value);
		Thread.sleep(1000);	
	}
	
	@Test(priority=3,dependsOnMethods ="SetEnvVarNameTOMaxLength",dataProvider = "traversTillApp")
	public void Validatelength(String orgName, String space, String app, String VariableName,String Value) throws Exception
	{
		scroll();
		String  NewEnvVarName=driver.findElement(By.cssSelector("table tr:last-child td")).getText();
		verifyLength(NewEnvVarName, "Environment var");
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
		TestLog.info("************** Test Case 23  ENDS  VerifyMaxEnvVariableLength:- **************");
		
	}	


	
}
