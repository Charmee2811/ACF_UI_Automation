package cf.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
//import org.apache.poi.hssf.record.formula.functions.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import cf.Base.itemAction;
import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

//  To verify whether the selected value gets added to drop down when user scale app memory
public class verifyAppScale extends itemVerification
{
	String desc = "To verify whether the selected value gets added to drop down when user scale app memory";
	int rowNo=10;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());	
	 }
	
	@Test	(priority=1)
	public void setUp() throws Exception
	{
    	 TestLog.info("************** Test Case 10  verifyAppScale Starting :- **************");
    	 openBrowser();
    	 openURL() ;
    	 login();	
	}
		
   @DataProvider(name="creatOrg")
   public Object[][] creatOrg() throws BiffException  
   {
	   Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAppScale.xls","orgSpace");
	   return arrayObject;
   }
	  
    @Test(priority=2, dependsOnMethods= "setUp", dataProvider = "creatOrg")
  	public void openApp(String orgName, String spaceName, String appname) throws InterruptedException
  	{
  		traverse_Demo(orgName, spaceName, appname);
  	}
  	
    @Test(priority=3, dependsOnMethods= "openApp", dataProvider = "AppSacleDetails")
  	public void validateInstanceValue(String instance,String memory, String disk) throws InterruptedException	
  	{
  		appInstancevalidate(instance,memory,disk);
  	}
  	
  	@Test(priority=4, dependsOnMethods= "validateInstanceValue", dataProvider = "AppSacleDetails" )
  	public void validateMemoryValue(String instance,String memory, String disk) throws Exception	
  	{
  		appvaluevalidate(memory ,"memory","txtAppMemoryTbl");
  	}
  	
  	@Test(priority=5, dependsOnMethods= "validateMemoryValue",dataProvider = "AppSacleDetails")
  	public void validateDiskValue(String instance,String memory, String disk) throws Exception	
  	{
  		appvaluevalidate(disk ,"disk", "txtAppDiskTbl");
  	}
  	  
    @DataProvider(name="AppSacleDetails")
    public Object[][] AppSacleDetails() throws BiffException  
    {
    	Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAppScale.xls","scaleDetails");
    	return arrayObject;
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
		TestLog.info("************** Test Case 10  verifyAppScale ENDS :- **************");
	}
	}


