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
public class verifyAccessManagerRole extends itemVerification {
	
	String desc = "To verify if user is able to acess the organisation and market place link and also if user is able to see service list  if user is  assigned a Manager role";
	boolean skip=false;
	int rowNo = 14;
		
	@BeforeClass
	public void isTestExecutableCall()
	{
		isTestExecutableBaseClass("verifyAccessManagerRole");
	}

	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 12 verifyAccessManagerRole Starting :- **************");
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
			String objCreateLink = "createOrgLink";
			String objPath1 ="orgName";
			String objPath2 ="";
			String pwd = "";
			String btnPath = "createOrgBtn";
			String objFlashMsg = "flashMsg";
			
			//create_Org_User_Space(objCreateLink,objPath1,objPath2,btnPath,objFlashMsg,name, pwd);
		    	//create_Org_User_Space("createOrgLink","orgName","","createOrgBtn","flashMsg","", "");
			   creatOrg(orgName);
			
		}
		
	
		//Creation of USER for non admin login and logout
		@DataProvider(name="creatUser")
		public Object[][] creatUser() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyAccessManagerRole.xls","user");
			return arrayObject;
		}
		
		@Test(priority=4 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
		public void userCreationAssignRole(String email,String password,String roles) throws InterruptedException
		{
			String objCreateLink = "createUserLink";
			String objPath1 = "userEmail";
			String objPath2 ="usrPwd";
			String objFlashMsg = "flashMsg";
			String btnPath = "createUserBtn";
			
	        //create_Org_User_Space(objCreateLink,objPath1,objPath2,btnPath,objFlashMsg,email, password);
			    //create_Org_User_Space("createUserLink","userEmail","usrPwd","createUserBtn","flashMsg",email, password);
			    creatUser(email,password);	
			Thread.sleep(4000);	
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
			Thread.sleep(4000);	
			String objCreateLink = "createSpaceLink";
			String objPath1 = "spaceName";
			String objPath2 ="";
			String pwd = "";
			String objFlashMsg = "flashMsgSpace";
			String btnPath = "createSpaceBtn";
			
			//create_Org_User_Space("createSpaceLink","spaceName","","createSpaceBtn","flashMsgSpace",name, "");
			//create_Org_User_Space(objCreateLink,objPath1,objPath2,btnPath,objFlashMsg,name, pwd);	
		      creatSpace ("spaceName");
		}
		
		//Login to normal user
		
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
			Thread.sleep(7000);
			click_OrgSpace("marketplace");
			Thread.sleep(8000);
			//Verifyng Services Title
			if (driver.findElement(By.xpath(TestBase.or_getproperty("servicetitle"))).isDisplayed())
			{
				Assert.assertTrue(true, "User has assignede Org Manager Role and able to Access MarketPlace link");
				 TestLog.info("User has assignede Org Manager Role and able to Access MarketPlace link");
			}
			
			else
			{
				Assert.assertTrue(false, "Logged In user has  Manager Role  Still NOT  able to Access MarketPlace link");
				TestLog.info("Logged In user has  Manager Role  Still NOT  able to Access MarketPlace link");
				
			}
			
			//Code to Verify Services Instance rows
			int rowCount=driver.findElements(By.xpath(TestBase.or_getproperty("serviceInstanceTableRows"))).size();
        	int columnCount=driver.findElements(By.xpath(TestBase.or_getproperty("serviceInstanceTableColls"))).size();
        	if (rowCount >= 0 && columnCount >=0)
        	{
        		Assert.assertTrue(true, "User has assignede Org Manager Role and able to see Service Insatcne List as Table Contents were were Rows:-"+ rowCount);
				 TestLog.info("User has assignede Org Manager Role and able to see Service Insatcne List as Table Contents were Rows:-"+ rowCount);
        	}
        	else
        	{
        		Assert.assertTrue(false, "Logged In user has  Manager Role  Still NOT  able to see Service List as Table Contents were were Rows:-"+ rowCount);
				 TestLog.info("Logged In user has  Manager Role  Still NOT  able to see Service List as Table Contents were were Rows:-"+ rowCount);
        	}
			
		}
		
		
		@AfterMethod(alwaysRun=true)
		public void screenshotCall(ITestResult res) throws IOException
		 {
			   String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
			   String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
			   screenshot(failSCPath,passSCPath);   
		 }
			
		
		@AfterTest(alwaysRun=true)
		public void generateReportCall()
		{	
			generateReport(rowNo,desc);	 
			TestLog.info("************** Test Case 12  verifyAccessManagerRole ENDS :- **************");
		}
		

}
