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
public class verifyAccessRole extends itemVerification {
	
	String desc = "To verify if user is able to acess the organisation and market place link and also if user is able to see service list  if user is  assigned a Manager role";
	boolean skip=false;
	int rowNo = 5;
		
	@BeforeClass
	public void isTestExecutableCall()
	{
		//test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());	
	}

	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 5 verifyAccessRole Starting :- **************");
		openBrowser();
		openURL() ;
		login();		
	}

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
	
	@DataProvider(name="creatUser")
	public Object[][] creatUser() throws BiffException  {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAccessManagerRole.xls","user");
		return arrayObject;
	}
		
	@Test(priority=4 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
	public void userCreationAssignRole(String email,String password,String roles) throws InterruptedException
	{
		Thread.sleep(500);
		creatUser(email,password);	
		Thread.sleep(2000);	
		assignUserRole(email,roles);
	}
		
	@DataProvider(name="creatSpace")
	public Object[][] creatSpace() throws BiffException  {
		Object[][] arrayObject = cf_Util.xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAccessManagerRole.xls","space");
		return arrayObject;
	}
	@Test(priority=5,dependsOnMethods ="userCreationAssignRole",dataProvider = "creatSpace")
	public void spaceCreation(String name) throws InterruptedException
	{
		Thread.sleep(1000);
		TestLog.info("******sInside space createion");
		creatSpace ("spaceName");
	}
		
	@Test(priority=6,dependsOnMethods ="spaceCreation",dataProvider = "creatUser")
	public void userLogin(String email,String password,String Roles) throws InterruptedException, IOException
	{
		Thread.sleep(1000);	
		logout();	
		Thread.sleep(2000);	
		nonAdminLogin(email,password);
		Thread.sleep(2000);	
	}
		
	@Test(priority=7,dependsOnMethods ="userLogin",dataProvider = "creatUser")
	public void verifyLogin_MarketPlaceLinks(String email,String Password,String Roles) throws InterruptedException, IOException
	{	
		verifyLoginUser(email);
		Thread.sleep(2000);
		String orgXPath = "userRoleOrg";
		String spaceXpath = "userRoleSpace";
		click_OrgSpace(orgXPath);
		Thread.sleep(2000);
		click_OrgSpace(spaceXpath);
		Thread.sleep(5000);
		click_OrgSpace("marketplace");
		Thread.sleep(8000);
		if (driver.findElement(By.xpath(TestBase.or_getproperty("servicetitle"))).isDisplayed())
		{
			TestLog.info("User has assignede Org Manager Role and able to Access MarketPlace link");
			Assert.assertTrue(true, "User has assignede Org Manager Role and able to Access MarketPlace link");
		}
		else
		{
			TestLog.info("Logged In user has  Manager Role  Still NOT  able to Access MarketPlace link");
			Assert.assertTrue(false, "Logged In user has  Manager Role  Still NOT  able to Access MarketPlace link");
		}
			
		//Code to Verify Services Instance rows
		int rowCount=driver.findElements(By.xpath(TestBase.or_getproperty("serviceInstanceTableRows"))).size();
		int columnCount=driver.findElements(By.xpath(TestBase.or_getproperty("serviceInstanceTableColls"))).size();
		if (rowCount >= 0 && columnCount >=0)
		{
			TestLog.info("User has assignede Org Manager Role and able to see Service Insatcne List as Table Contents were Rows:-"+ rowCount);
			Assert.assertTrue(true, "User has assignede Org Manager Role and able to see Service Insatcne List as Table Contents were were Rows:-"+ rowCount);
        }
		else
		{
			TestLog.info("Logged In user has  Manager Role  Still NOT  able to see Service List as Table Contents were were Rows:-"+ rowCount);
			Assert.assertTrue(false, "Logged In user has  Manager Role  Still NOT  able to see Service List as Table Contents were were Rows:-"+ rowCount);
        }
			
	}
		
	@AfterMethod //(alwaysRun=true)
	public void screenshotCall(ITestResult res) throws IOException
	{
		String methodName = res.getMethod().getMethodName();
		String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName();
		String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
		screenshot(failSCPath,passSCPath);   
		//ExtntRep(methodName,res);   
	 }

	@AfterTest(alwaysRun=true)
	public void generateReportCall()
	{	
		generateReport(rowNo,desc);	 
		TestLog.info("************** Test Case 05  verifyAccessRole ENDS :- **************");
	}
		
}
