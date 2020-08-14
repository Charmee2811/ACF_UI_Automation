package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class VerfifyAssignedRoleAsAuditor extends itemVerification{
	String desc = "To Verify if user can access CF according to Auditor as a role";
	int rowNo=26;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
		  
	}
	
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 26 :  VerfifyAssignedRoleAsAuditor Starting :- **************");
		openBrowser();
		openURL() ;
		
		login();		
		
	}
	//Creation of Organation for non admin login
		@DataProvider(name="creatOrg")
		public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyUserCanAssignesAsAuditor.xls","organization");
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
		 				Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyUserCanAssignesAsAuditor.xls","user");
		 				return arrayObject;
		 		}
				
				
				@Test(priority=3 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
				public void usrRoleAssign(String email,String password,String UserRole) throws InterruptedException
				{	Thread.sleep(1000);
					creatUser(email,password);
					Thread.sleep(1000);
					assignRole(email,UserRole);
					Thread.sleep(1500);
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
					TestLog.info("************** Test Case 26  ENDS  VerfifyAssignedRoleAsAuditor:- **************");
				}	

}
