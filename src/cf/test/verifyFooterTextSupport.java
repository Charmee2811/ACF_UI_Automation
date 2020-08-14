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
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemAction;
import cf.Base.itemVerification;
import cf_Util.TestLog;

// To verify whether  "Canopy Care" text is replaced with "Support" from the footer
public class verifyFooterTextSupport extends itemVerification
{ 
	String desc = "To verify whether  Canopy Care text is replaced with Support from the footer";
	int rowNo = 3 ;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=1)
		public void setUp() throws Exception
		{
		TestLog.info("************** Test Case 03  verifyFooterTextSupport Starting :- **************");
			openBrowser();
			openURL() ;
			login();		
		}
		
	@Test(priority=2,dependsOnMethods = "setUp")
		public void fotterText() throws InterruptedException
		{
			Thread.sleep(1000);
			scroll();
			verifyFotterText();
		}
			
	@AfterMethod//(alwaysRun=true)
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
		TestLog.info("************** Test Case 02 verifyFooterTextSupport ENDS :- **************");
	}
   }


