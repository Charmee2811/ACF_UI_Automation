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

//To verify whether the selected App is renamed
public class verifyAppRename extends itemVerification
{
		
	public String orgurl;
	public String spaceurl;
	public String marketurl;
	int rowNo=35;
	public String failSCPath="";
	
	public String desc="To verify whether the selected App is rename";
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}

   @Test(priority=1)
   public void setUp() throws Exception
   {
	   TestLog.info("************** Test Case 35  verifyAppRename Starting :- **************");
	   openBrowser();
	   openURL() ;
	   login();		
	}	
	
   @DataProvider(name="creatOrg")
   public Object[][] creatOrg() throws BiffException  {
	   Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAppRenamed.xls","orgSpace");
	   return arrayObject;
	}
		
	@Test(priority=2,dependsOnMethods ="setUp" ,dataProvider = "creatOrg")
	public void appRestaged(String orgName, String spaceName, String appname, String appRenameName  ) throws InterruptedException
	{
		traverse_Demo(orgName, spaceName, appname);
		Thread.sleep(5000);
		TestLog.info(driver.getCurrentUrl());
		//scrollToElement("appstate");
		//getObject("appstate");
		//String appState=driver.findElement(By.xpath((or_getproperty("appstate")))).getText();
		//TestLog.info("appState"+appState);  & appState.contains("running")
		
		//if((!driver.findElement(By.xpath((or_getproperty("btnaction")))).isEnabled())  )
		//{
			TestLog.info("App rename action");
			//Thread.sleep(1000);
			System.out.println("reached");
			driver.findElement(By.xpath((or_getproperty("btnaction")))).click();
			Thread.sleep(5000L);
			getObject("btnrename");
			driver.findElement(By.xpath((or_getproperty("btnrename")))).click();
			Thread.sleep(500);
			driver.findElement(By.xpath(or_getproperty("inputname"))).clear();
			driver.findElement(By.xpath(or_getproperty("inputname"))).sendKeys(appRenameName);
			Thread.sleep(3000);
			driver.findElement(By.xpath((or_getproperty("btnupdate")))).click(); 
			Thread.sleep(4000L);
			String appRenameFlshMsgText =  driver.findElement(By.xpath(or_getproperty("appRenameFlshMsg"))).getText();
			TestLog.info("**" + appRenameFlshMsgText); 
			if(appRenameFlshMsgText.contains("Successfully updated App name") )
			{
				TestLog.info(" App is renamed");
				result="true";
				Assert.assertTrue(appRenameFlshMsgText.contains("Successfully updated App name"));
			}
			else
			{
				result="false";
				TestLog.info("App is not renamed ");
				Assert.assertTrue(false,"App is not renamed");	
			}
		//}
				
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
		TestLog.info("************** Test Case 35 verifyAppRename  ENDS :- **************");
	}
   }


