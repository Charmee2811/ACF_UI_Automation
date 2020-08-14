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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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

//To verify whether Non Admin user is able to open dashboard page of Admin user
public class verifyNonAdminLogin extends itemVerification {
	String desc = "To verify whether Non Admin user is able to open dashboard page of Admin user";
	int rowNo=2;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=2)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 02  verifyNonAdminLogin Starting :- **************");
		openBrowser();
		openURL() ;
		login();

	}
	
		//Creation of Organation for non admin login
		@DataProvider(name="creatOrg")
		public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyNonAdminLogin.xls","organization");
			return arrayObject;
		}
		
		@Test(priority=3,dependsOnMethods ="setUp",dataProvider = "creatOrg")
		public void orgCreation(String orgName) throws InterruptedException
		{
			creatOrg(orgName);
		}
		
	
		//Creation of USER for non admin login and logout
		@DataProvider(name="creatUser")
		public Object[][] creatUser() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyNonAdminLogin.xls","user");
			return arrayObject;
		}
		
		@Test(priority=4 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
		public void userCreation(String email,String password) throws InterruptedException
		{
			creatUser(email,password);	
		}
		
		
		//Login to normal user
		
		@Test(priority=5,dependsOnMethods ="userCreation",dataProvider = "creatUser")
		public void userLogin(String email,String password) throws InterruptedException, IOException
		{
			logout();	
			nonAdminLogin(email,password);
			Thread.sleep(2000);
			Thread.sleep(2000);
			verifyLoginUser(email);	
			Thread.sleep(2000);
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
		public void generateReportCall()
		{	
			generateReport(rowNo,desc);	  
			TestLog.info("************** Test Case 02  verifyNonAdminLogin Ends :- **************");
		}
	}
