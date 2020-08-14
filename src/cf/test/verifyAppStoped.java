package cf.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import cf.Base.TestBase;
import cf.Base.itemAction;
import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class verifyAppStoped extends itemVerification
{
		
	public String orgurl;
	public String spaceurl;
	public String marketurl;
	int rowNo=11;
	public String failSCPath="";
	
	public String desc="To verify whether the selected App is Stopped";
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}

	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 11  verifyAppStoped Starting :- **************");
		openBrowser();
		openURL() ;
		login();
	}	
		
	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAppStoped.xls","orgSpace");
		return arrayObject;
	}
		
	@Test(priority=2,dependsOnMethods ="setUp" ,dataProvider = "creatOrg")
	public void appStoped(String orgName, String spaceName, String appname ) throws InterruptedException
	{
		
		traverse_Demo(orgName, spaceName, appname);
		Thread.sleep(2000);
		Thread.sleep(6000L);
		getObject("appStartButton");
		//TestLog.info(driver.getCurrentUrl());
		if((!driver.findElement(By.xpath((or_getproperty("appStartButton")))).isEnabled()))
		{
			TestLog.info("App is running so stoppin it");
		
			driver.findElement(By.xpath((or_getproperty("appstopbutton")))).click();
			Thread.sleep(5000);
			
			scrollToElement("appStateInstances");
			getObject("appstate");
			String appState=driver.findElement(By.xpath((or_getproperty("appstate")))).getText();
			if(appState.contains("stopped") )
			{
				result="true";
				TestLog.info("app stopped");
				Assert.assertTrue(appState.contains("stopped"));
				
			}
			else
			{
				result="false";
				TestLog.info("app is not stopped");
				Assert.assertTrue(false,"App is not Stopped");				
			}
		}
		
		
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
		TestLog.info("************** Test Case 11 verifyAppStoped ENDS :- **************");
	}
   }


