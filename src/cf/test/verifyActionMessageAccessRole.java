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

import cf.Base.TestBase;
import cf.Base.itemAction;
import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

//To verify if user is able to acess the organisation and market place link and also if user is able to see service list  if user is  assigned a role
public class verifyActionMessageAccessRole extends itemVerification {
	
	String desc = "verify whether  action message displays correctly user role assigned successfully";
	boolean skip=false;
	int rowNo = 21;
		
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
		
		
	}

	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 21 verifyActionMessageAccessRole Starting :- **************");
		openBrowser();
		openURL() ;
		login();		
	}
	
		//Creation of Organation for non admin login
		@DataProvider(name="creatOrg")
		public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAccessManagerRole.xls","organization");
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
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAccessManagerRole.xls","user");
			return arrayObject;
		}
		
		@Test(priority=4 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
		public void verifyUserAssignRoleMsg(String email,String password,String roles) throws InterruptedException
		{
			creatUser(email,password);	
			Thread.sleep(4000);	
			assignUserRole(email,roles);
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
			TestLog.info("************** Test Case 21  verifyActionMessageAccessRole ENDS :- **************");
		}
		
}
