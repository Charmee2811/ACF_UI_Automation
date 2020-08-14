package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class VerifyBrowserCompatiblility extends itemVerification{
 
	String desc = " To verify for the browsers compatibility for chrome for latest version   using selenium Script";
	int rowNo=9;
	
	@BeforeClass
	public void isTestExecutableCall()
	{	
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());	
	}
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 09 :  VerifyBrowserCompatiblility Starting :- **************");
		openBrowser();
		openURL() ;
		login();	
	}
	
	//compatibility validation for new chrome version 
	@DataProvider(name="travrseData")
	public Object[][] travrseData() throws BiffException  
	{
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyChromeCompatiblility.xls","compatibility");
		return arrayObject;		
	}
	@Test(priority=2, dependsOnMethods= "setUp", dataProvider="travrseData")
	public void openApp(String orgName, String spaceName, String appname, String instance,String memory, String disk, String marketplace1, String marketplace2 ) throws InterruptedException
	{
		traverse_Demo(orgName, spaceName, appname);
	}
	@Test(priority=3, dependsOnMethods= "openApp", dataProvider="travrseData" )
	public void validateInstanceValue(String orgName, String spaceName, String appname, String instance,String memory, String disk, String marketplace1, String marketplace2) throws InterruptedException	
	{
		appInstancevalidate(instance,memory,disk);
	}
	
	@Test(priority=4, dependsOnMethods= "validateInstanceValue", dataProvider="travrseData" )
	public void validateMemoryValue(String orgName, String spaceName, String appname, String instance,String memory, String disk, String marketplace1, String marketplace2) throws Exception	
	{
		
		appvaluevalidate(memory ,"memory","txtAppMemoryTbl");
	}
	@Test(priority=5, dependsOnMethods= "validateMemoryValue", dataProvider="travrseData" )
	public void validateDiskValue(String orgName, String spaceName, String appname, String instance,String memory, String disk, String marketplace1, String marketplace2) throws Exception	
	{
		appvaluevalidate(disk ,"disk", "txtAppDiskTbl");
	}
	
	@Test(priority=6, dependsOnMethods= "validateDiskValue", dataProvider="travrseData" )
	public void BrowseMarketPlace(String orgName, String spaceName, String appname, String instance,String memory, String disk, String marketplace1, String marketplace2) throws Exception	
	{
		checkMarketplaceFavicon();	
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
		TestLog.info("************** Test Case 09  ENDS  VerifyBrowserCompatiblility:- **************");
		
	}	
	
}