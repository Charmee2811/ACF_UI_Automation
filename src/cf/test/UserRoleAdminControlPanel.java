/*package cf.test;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class UserRoleAdminControlPanel extends itemVerification {
	
	String desc = " Verify User role admin control panel using selenium Script ";
	public static String user1;
	public static String user2;
	public static String user3;
	public static String Pass;
	int rowNo=45;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());	
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	
  	 @Test(priority=1)
		public void setUp() throws Exception
			{
		       TestLog.info("************** Test Case 46  Verify User role admin control panel using selenium Script Starting :- **************");
				openBrowser();
				openURL() ;
			    login();
			}


	//Creation of Organation for non admin login
		@DataProvider(name="creatOrg")
			public Object[][] creatOrg() throws BiffException  {
				Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "UserRoleAdminControlPanel.xls","organization");
				return arrayObject;
			}
			
		@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
			public void orgCreation(String orgName) throws InterruptedException
			{
				creatOrg(orgName);
			}
			
			
	//Creation of USER and assign role 
	 	@DataProvider(name="creatUser")
	 		public Object[][] creatUser() throws BiffException  
 			{
 				Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "UserRoleAdminControlPanel.xls","createNewUser");
 				return arrayObject;
 			}
		
			
		@Test(priority=3 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
			public void usrRoleAssign(String email,String password,String UserRole) throws InterruptedException
			{	
				Thread.sleep(600);
				creatUser(email,password);
				System.out.println("Users Created");
				Thread.sleep(1000);
				assignRole(email,UserRole);
				System.out.println("Roles Assigned");
				Thread.sleep(1500);
		
			    Pass= password;
			}
		
		
			@DataProvider(name="createSpace")
			   public Object[][] createSpace() throws BiffException
			       {
					 Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "UserRoleAdminControlPanel.xls","space");
					 return arrayObject;
					}
					
			@Test(priority=4, dependsOnMethods ="orgCreation",dataProvider = "createSpace")
			 	public void createSpace(String orgName, String spaceName) throws InterruptedException
					{
						creatSpace(spaceName);
						Thread.sleep(15000);
					
					}
	
					
			@DataProvider(name="assignUserSpaceRole")
	 		public Object[][] assignUserSpaceRole() throws BiffException  
 			{
 				Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "UserRoleAdminControlPanel.xls","spaceRoleAssign");
 				return arrayObject;
 			}
			
			@Test(priority=5,dependsOnMethods="createSpace", dataProvider = "assignUserSpaceRole")
				public void usrSpaceRoleAssign(String email,String spaceRole) throws InterruptedException
					{
						Thread.sleep(1000);
						assignSpaceRole(email,spaceRole);
						Thread.sleep(1000);
					}

		
						
		@Test(priority=6,dependsOnMethods="usrSpaceRoleAssign", dataProvider = "createSpace")
		 
			public void adminControlPanel(String orgName, String spaceName ) throws InterruptedException
			{
				adminPanel(orgName,spaceName);
				  
			}
			
			
		@Test(priority=7, dataProvider = "createSpace")
			public void changeRoleFromAdminPanelForOrgManager(String orgName, String spaceName ) throws InterruptedException, IOException
			{

				changeRole("user1TxtName", "chkUser1SpaceDeveloper");
				user1= driver.findElement(By.xpath(or_getproperty("user1TxtName"))).getText();
				System.out.println("1.User Name- "+user1);
				TestLog.info("1.User Name- "+user1);
				
			}
			
					

			@Test(priority=8, dataProvider = "createSpace")
			public void changeRoleFromAdminPanelForBillingManager(String orgName, String spaceName ) throws InterruptedException
			{
				changeRole("user2TxtName", "chkUser2SpaceDeveloper");
			    user2= driver.findElement(By.xpath(or_getproperty("user2TxtName"))).getText();
				System.out.println("2.User Name- "+user2);
				TestLog.info("2.User Name- "+user2);
			}
			
					
			@Test(priority=9, dataProvider = "createSpace")
			public void changeRoleFromAdminPanelForOrgAuditor(String orgName, String spaceName ) throws InterruptedException
			{
					changeRole("user3TxtName", "chkUser3SpaceDeveloper");
					user3= driver.findElement(By.xpath(or_getproperty("user3TxtName"))).getText();
					System.out.println("3.User Name "+user3);
					TestLog.info("3.User Name- "+user3);
			}
			
			
			@Test(priority=10, dataProvider = "createSpace")
			public void verifyChangeRoleFromAdminPanelForBillingManager(String orgName, String spaceName ) throws InterruptedException
			{
				Thread.sleep(500);
				driver.findElement(By.xpath(or_getproperty("dashboardLink"))).click();
				Thread.sleep(1000);
				String orgList= "listvieworg";
		   	  	itemVerification.traverse(orgName,orgList);
		   	  
		   	  	String spaceList= "listviewspace";
				itemVerification.traverse(spaceName,spaceList);
				scrollTillPagination();
				Thread.sleep(1000);
				verifyChangeRole(user2, "Space Developer", "assignedUser2");
				
			}
			
					
		@Test(priority=11, dataProvider = "createSpace")
			public void verifyChangeRoleFromAdminPanelForOrgAuditor(String orgName, String spaceName ) throws InterruptedException
			{
				 verifyChangeRole(user3, "Space Manager", "assignedUser3");
					
			}
			
		@Test(priority=12, dataProvider = "createSpace")
			public void verifyChangeRoleFromAdminPanelForOrgManager(String orgName, String spaceName ) throws InterruptedException, IOException
			{
				verifyChangeRole(user1, "Space Developer", "assignedUser1");
		
			}
			
		@Test(priority=12, dataProvider = "createSpace")
			public void verifyOrgManagerCanChangeRoleFromAdminPanel(String orgName, String spaceName ) throws InterruptedException, IOException
			{
			   logout();
			   userLogin( user1, Pass, "Msg");
			   Thread.sleep(1000);
			   adminPanel(orgName,spaceName);
			   changeRole("user1TxtName", "chkUser4SpaceAuditor");
			   logout();				
			}
	
					
				
		@AfterMethod(alwaysRun=true)
		public void screenshotCall(ITestResult res) throws IOException
		 {
			String methodName = res.getMethod().getMethodName();
		    String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
		    String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
		    screenshot(failSCPath,passSCPath); 
		    ExtntRep(methodName,res);  
		}
		
		
		@AfterTest(alwaysRun=true)
		public void generateReport()
		{
			generateReport(rowNo,desc);	
			TestLog.info("**************Test Case 46  Verify User role admin control panel using selenium Script  ENDS :- **************");
		}	
				
}



*/