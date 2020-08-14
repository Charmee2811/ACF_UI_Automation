package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class UpdateUserProvidedServiceInstance extends itemVerification {
	
	String desc = " Verify the Updation of User provided Service instances on chrome browser for UI through selenium";
	int rowNo=36;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=1)
	public void setUp() throws Exception 
	{
		TestLog.info("*********Test Case 36 Verify the Updation of User provided Service instances on chrome browser for UI through selenium :- **********");
		openBrowser();
		openURL();
		login();
	}

	//Creation of Organization
	@DataProvider(name="creatOrg")
	   public Object[][] creatOrg() throws BiffException
	       {
			 Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "UpdateUserProvidedServiceInstance.xls","organization");
			 return arrayObject;
			}
			
	@Test(priority=2, dependsOnMethods ="setUp",dataProvider = "creatOrg")
	 	public void orgCreation(String orgName) throws InterruptedException
			{
		
				creatOrg(orgName);
				Thread.sleep(1500);
			}
	 
		@DataProvider(name="createSpace")
		   public Object[][] createSpace() throws BiffException
		       {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "UpdateUserProvidedServiceInstance.xls","space");
				 return arrayObject;
				}
				
		@Test(priority=3, dependsOnMethods ="setUp",dataProvider = "createSpace")
		 	public void createSpace(String spaceName) throws InterruptedException
				{
				creatSpace(spaceName);
				Thread.sleep(5000);
				
				}
		
		@DataProvider(name="createCups")
		   public Object[][] createCups() throws BiffException
		       {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "UpdateUserProvidedServiceInstance.xls","createCups");
				 return arrayObject;
				}
		
		@DataProvider(name="UpdateCups")
		   public Object[][] UpdateCups() throws BiffException
		       {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "UpdateUserProvidedServiceInstance.xls","updateCups");
				 return arrayObject;
				}
		
		
		@Test(priority=4, dependsOnMethods ="createSpace",dataProvider = "createCups")
		public void  createUserProvidedServiceInstance(String Instancename,String Hostname,String Port, String DBname, String Username, String Password ) throws InterruptedException
		{
			getObject("CreateUserProvideServiceInstance"); 
			CreateUserProvidedServiceInstance(Instancename, Hostname, Port, DBname, Username, Password);
			    Thread.sleep(4000); 
		}
		
		@Test(priority=5, dependsOnMethods ="createUserProvidedServiceInstance",dataProvider = "UpdateCups")
		public void  updateOfUserProvidedServiceInstance(String Instancename,String Port, String DBname) throws InterruptedException
		{
			getObject("updateCupsLink"); 
			verifyUpdateOfUserProvidedServiceInstance(Instancename, Port, DBname);
				
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
		public void generateReportCall()
		{	
			generateReport(rowNo,desc);	 
			TestLog.info("************** Test Case 36 Verify the Updation of User provided Service instances on chrome browser for UI through selenium :- **************");
		}


}
