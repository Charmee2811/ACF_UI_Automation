package cf.test;

import java.io.File;
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;






import com.relevantcodes.extentreports.LogStatus;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class VerifyUserCupsCreation  extends itemVerification{
	String desc = "To verify whether a user is able to create a cups services from console using selenium script";
	int rowNo=20;
	
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 20 :  VerifyUserCupsCreation Starting :- **************");
		openBrowser();
		openURL() ;
		login();
	}
	
	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyUserCupsCreation.xls","organization");
			return arrayObject;
	}
	
		
	@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
	public void orgCreation(String orgName) throws InterruptedException
	{
			creatOrg(orgName);		
	}
	
	@DataProvider(name="creatSpace")
	public Object[][] creatSpace() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyUserCupsCreation.xls","Space");
			return arrayObject;
	}
				
	@Test(priority=3,dependsOnMethods ="orgCreation",dataProvider = "creatSpace")
	public void spaceCreation(String spaceName) throws InterruptedException
	{
			creatSpace(spaceName);
			Thread.sleep(1000);
	}
	
	@DataProvider(name="creatInstance")
	public Object[][] creatInstance() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyUserCupsCreation.xls","Cups_data");
			return arrayObject;
	}
	@Test(priority=4,dependsOnMethods ="spaceCreation",dataProvider = "creatInstance")
	public void cupsCreation(String instanceName,String HostName, String Port, String DBname,String username, String password) throws Exception
	{
		createCups(instanceName, HostName, Port, DBname, username,password);		
	}
	
	@Test(priority=5,dependsOnMethods ="cupsCreation")
	public void cleanUP() throws Exception
	{
	    Thread.sleep(3000); //to make intance active before delete	
			deleteCups();
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
		TestLog.info("************** Test Case 20  ENDS  VerifyUserCupsCreation:- **************");
		
	}	
	
	
	
		
}
