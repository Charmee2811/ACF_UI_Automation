package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemAction;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class VerifyDeleteSpace  extends itemAction	{
	
	String desc = " To verify whether user is able to  Create and delete space";
	int rowNo=34;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	
	 @Test(priority=1)		
	   public void setUp() throws Exception
			{
		       TestLog.info("************** Test Case  34 Delete Space Starting :- **************");
				openBrowser();
				openURL() ;
				login();	
			}
	//Creation of Organization
	@DataProvider(name="creatOrg")
	   public Object[][] creatOrg() throws BiffException
	       {
			 Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "createOrg.xls","organization");
			 return arrayObject;
			}
			
	@Test(priority=2, dependsOnMethods ="setUp",dataProvider = "creatOrg")
	 	public void orgCreation(String orgName) throws InterruptedException
			{
		System.out.println("call create func");
				creatOrg(orgName);
				Thread.sleep(1500);
			}

	@DataProvider(name="createSpace")
	   public Object[][] createSpace() throws BiffException
	       {
			 Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "createSpace.xls","space");
			 return arrayObject;
			}
			
	@Test(priority=3, dependsOnMethods ="setUp",dataProvider = "createSpace")
	 	public void createSpace(String spaceName) throws InterruptedException
			{
				creatSpace(spaceName);
				Thread.sleep(8000);
			}
	

	@Test(priority=4, dependsOnMethods = "setUp",dataProvider = "createSpace")
	public void spaceDeletetion(String spaceName) throws InterruptedException
	    {
		  deleteOrgSpace(spaceName, "btnDeleteSpace");	 	 		
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
		TestLog.info("************** Test Case 34 Delete Space  ENDS :- **************");
	}
}
