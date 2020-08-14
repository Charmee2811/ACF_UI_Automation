package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
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

public class VerfiyDeleteOrg extends itemVerification {
	
	String desc = " To verify whether user is able to delete an org";
	int rowNo=22;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		//test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	 @Test(priority=1)		
	   public void setUp() throws Exception
			{
		       TestLog.info("************** Test Case 22 Test Case Delete Starting :- **************");
				openBrowser();
				openURL() ;
				login();	
			}
	//Creation of Organisation for admin login
	@DataProvider(name="creatOrg")
	   public Object[][] creatOrg() throws BiffException
	       {
			 Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "createOrg.xls","organization");
			 return arrayObject;
			}
			
	@Test(priority=2, dependsOnMethods ="setUp",dataProvider = "creatOrg")
	 	public void orgCreation(String orgName) throws InterruptedException
			{
				creatOrg(orgName);
				Thread.sleep(1500);
			}
	

	@Test(priority=3, dependsOnMethods = "setUp",dataProvider = "creatOrg")
		public void orgDeletetion(String orgName) throws InterruptedException
		    {
		      deleteOrgSpace(orgName, "btnDeleteOrg");	
			}
		
	
	@AfterMethod(alwaysRun=true)
	public void screenshotCall(ITestResult res) throws IOException
	 {
		String methodName = res.getMethod().getMethodName();
	    String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName();
	    String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
	    screenshot(failSCPath,passSCPath);   
	  //  ExtntRep(methodName,res);  
	}
	
	
	@AfterTest(alwaysRun=true)
	public void generateReportCall()
	{	
		generateReport(rowNo,desc);	 
		TestLog.info("************** Test Case 22  VerifyDeleteOrg ENDS :- **************");
	}

	 

}