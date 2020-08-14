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

//"To verify whether the selected App is Deleted"
public class verifyAppDeleted extends itemVerification
{
		
	public String orgurl;
	public String spaceurl;
	public String marketurl;
	int rowNo = 12;
	public String desc="To verify whether the selected App is Deleted";
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}

	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 12  verifyAppDeleted  Starting :- **************");
		openBrowser();
		openURL() ;
		login();	
	}
			
	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAppDeleted.xls","orgSpace");
		return arrayObject;
	}
		
	@Test(priority=2,dependsOnMethods ="setUp" ,dataProvider = "creatOrg")
	public void appDelete(String orgName, String spaceName, String appname ) throws InterruptedException
	{
		traverse_Demo(orgName, spaceName, appname);
		getObject("appdelete");
		driver.findElement(By.xpath((or_getproperty("appdelete")))).click();
		Thread.sleep(500);
		driver.findElement(By.xpath((or_getproperty("confirmdeletebutton")))).click(); 
		Thread.sleep(4000L);
		String appDeleteFlshMsgText =  driver.findElement(By.xpath(or_getproperty("delAppflashMsg"))).getText();
		TestLog.info("**" + appDeleteFlshMsgText); 
		if(appDeleteFlshMsgText.contains("Successfully deleted app") )
		{
			TestLog.info(" App is Deleted");
			result="true";
			Assert.assertTrue(appDeleteFlshMsgText.contains("Successfully deleted app"));
		}
		else
		{
			result="false";
			TestLog.info("App is not deleted ");
			Assert.assertTrue(false,"App is not Deleted");	
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
		TestLog.info("************** Test Case 12  verifyAppDeleted ENDS :- **************");
	}
   }


