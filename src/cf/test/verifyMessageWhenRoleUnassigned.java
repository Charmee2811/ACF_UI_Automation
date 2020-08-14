
package cf.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
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
import cf_Util.xls_reader;

// To verify whether correct message is displayed when a role is unassigned to a user
public class verifyMessageWhenRoleUnassigned extends itemVerification
{
	String desc = "To verify whether correct message is displayed when a role is unassigned to a user";
	int rowNo = 8;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		isTestExecutableBaseClass("verifyMessageWhenRoleUnassigned");
	}

		@Test(priority=1)
		public void setUp() throws Exception
		{
			TestLog.info("************** Test Case 06 Starting verifyMessageWhenRoleUnassigned :- **************");
			openBrowser();
			openURL() ;
			login();	
		}
		//Creation of Organation for non admin login
				@DataProvider(name="creatOrg")
				public Object[][] creatOrg() throws BiffException  {
					Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyMessageWhenRoleUnassigned.xls","organization");
					return arrayObject;
				}
				
				@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
				public void orgCreation(String orgName) throws InterruptedException
				{
					creatOrg(orgName);
				}
				
				
				//Creation of USER for non admin login and logout
				@DataProvider(name="creatUser")
				public Object[][] creatUser() throws BiffException  {
					Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyMessageWhenRoleUnassigned.xls","user");
					return arrayObject;
				}
					
				
			@Test(priority=3 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
			public void usrRoleUnassignedMsg(String email,String password,String UserRole) throws InterruptedException
			{
				creatUser(email,password);
				Thread.sleep(10000);
				assignUserRole(email,UserRole);
				Thread.sleep(1500);
				removeRole(email);
			}
			
			@AfterMethod //(alwaysRun=true)
			public void screenshotCall(ITestResult res) throws IOException
			 {
			   String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
			   String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
			   screenshot(failSCPath,passSCPath);   
			}
			
			
			@AfterTest(alwaysRun=true)
			public void generateReport()
			{
				generateReport(rowNo,desc);	
				TestLog.info("************** Test Case 06  ENDS  verifyMessageWhenRoleUnassigned:- **************");
			}	
		}


