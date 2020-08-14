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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

//  To verify whether Brooklyn service gives an error message while deleting it
public class verifyBrooklynDeleteMsg extends itemVerification
{
	String desc = "To verify whether Brooklyn service gives an error message while deleting it";
	int rowNo=13;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test	(priority=1)	
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 13 verifyBrooklynDeleteMsg Starting :- **************");
		openBrowser();
		openURL() ;
		login();
	}
		
    @DataProvider(name="creatOrg")
    public Object[][] creatOrg() throws BiffException  
    {
    	Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyBrooklynDeleteMsg.xls","orgSpace");
    	return arrayObject;
	}
	
    @Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
    public void orgSpaceCreation(String orgName,String spaceName) throws InterruptedException
    {
    	creatOrg(orgName);
    	Thread.sleep(2000);
    	creatSpace (spaceName);
    	Thread.sleep(4000);
	}
	      
    @DataProvider(name="createServiceInstance")
    public Object[][] createServiceInstance() throws BiffException  
    {
    	Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyBrooklynDeleteMsg0.xls","serviceInstance");
    	return arrayObject;
	}
      
     
    @Test(priority=3,dependsOnMethods ="orgSpaceCreation" ,dataProvider = "createServiceInstance")
    public void brooklynVerification(String instanceName , String serviceName ,String plan ) throws InterruptedException, IOException
    {
    	createServiceInstance( instanceName, serviceName, plan);
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
    	TestLog.info("************** Test Case 13 verifyBrooklynDeleteMsg ENDS :- **************");
	}
	}


