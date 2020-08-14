package cf_category;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;
import jxl.read.biff.BiffException;

public class assignRoleFunctionalites extends itemVerification {
	String desc = "To Verify if user can access CF according to Auditor/Billing Manager/Mnager/User as a role";
	int rowNo=32;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
		  
	}
	
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 32 :  assignRoleFunctionalites  Starting :- **************");
		openBrowser();
		openURL() ;	
		login();		
		
	}
	//Creation of Organation for non admin login
		@DataProvider(name="creatOrg")
		public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "assignRoleFunctionalites.xls","organization");
			return arrayObject;
		}
		
		@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
		public void orgCreation(String orgName) throws InterruptedException
		{
			creatOrg(orgName);
		}
		
		
		//Creation of USER and assign role 
		 		@DataProvider(name="creatUser")
		 		public Object[][] creatUser() throws BiffException  {
		 				Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "assignRoleFunctionalites.xls","user");
		 				return arrayObject;
		 		}
				
				@Test(priority=3 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
				public void createUser(String email,String password) throws InterruptedException
				{	Thread.sleep(1000);
					creatUser(email,password);
					Thread.sleep(1000);
				}
				
				@Test(priority=4 )
				public void assignRoleAuditor() throws InterruptedException
				{
					assignRole("auditorAsARole@Googs.com","Auditor");
					Thread.sleep(1000);
				}
				
				@Test(priority=5 )
				public void assignRoleBillingManager() throws InterruptedException
				{
					assignRole("userBillingmanager@gmail.com","Billing Manager");
					Thread.sleep(1000);
				}
				
				@Test(priority=6 )
				public void assignRoleManager() throws InterruptedException
				{
					assignRole("managerRole@google.com","Manager");
					Thread.sleep(1000);
				}
				
				@Test(priority=7)
				public void assignRoleUser() throws InterruptedException
				{
					assignRole("userAsRole@gmail.com","Users");
					Thread.sleep(500);
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
				public void generateReport()
				{
					generateReport(rowNo,desc);	
					TestLog.info("************** Test Case 32  assignRoleFunctionalites  ENDS  :- **************");
				}	

}
