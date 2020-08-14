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

public class VerifyPaginationOfSpace extends itemVerification {
	String desc = " To verify Pagination of an Space inside an Organization ";
	int rowNo=25;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=1)
	public void setUp() throws Exception 
	{
		TestLog.info("*********Test Case to Verify Pagination of an Space on Dashboard :- **********");
		openBrowser();
		openURL();
		login();
	}

	//Creation of Organization
	@DataProvider(name="creatOrg")
	   public Object[][] creatOrg() throws BiffException
	       {
			 Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyPaginationOfSpace.xls","organization");
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
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyPaginationOfSpace.xls","space");
				 return arrayObject;
				}
				
		@Test(priority=3, dependsOnMethods ="setUp",dataProvider = "createSpace")
		 	public void createSpace(String spaceName) throws InterruptedException
				{
				creatSpace(spaceName);
				Thread.sleep(15000);
				driver.findElement(By.xpath((or_getproperty("NavigateToOrg")))).click();
				Thread.sleep(5000);
				driver.findElement(By.xpath(or_getproperty("abc"))).click();
				int rowCount=driver.findElements(By.xpath((or_getproperty("firstspace")))).size();
				TestLog.info("Count of No.Of Space = " + rowCount);
				System.out.println(" Count of No of Spaces "+ rowCount );
				Thread.sleep(1000); 
				
					
		}
	
	
	@Test(priority=4)
	public void PaginationSpace() throws InterruptedException, IOException
	{
		Thread.sleep(4000);
		VerifyPagination("firstspace","space","spacePagination");	
	}
		

						
	@Test(priority=4)
	public void PaginationOfSpace() throws InterruptedException
	{
		  
		VerifyPagination("firstspace","space","spacePagination");
		
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
		TestLog.info("************** Test Case 25 Verify Pagination of an Space inside an Organization :- **************");
	}

	
	
}
