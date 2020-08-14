package cf_category;

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

public class VerifyPagination extends itemVerification {
	String desc = " To verify Pagination of an Organization, delete org, and pagination od space  on Dashboard ";
	int rowNo=33;
	
	@BeforeClass
	public void isTestExecutableCall()
	{
		//test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	@Test(priority=1)
	public void setUp() throws Exception 
	{
		TestLog.info("*********Test Case 33  VerifyPagination started :- **********");
		openBrowser();
		openURL();
		login();
	}

//	Scenario 1:-To verify Pagination of an Organization on Dashboard 
	@Test(priority=2)
	public void PaginationofOrg() throws InterruptedException, IOException
	{
		Thread.sleep(4000);
		scrollTillPagination();
		VerifyPagination("firstOrg", "Organization",  "pagination" );
		TestLog.info("pagination org methode end here");
	}
//Scenario 2:- To verify Pagination of an Space inside an Organization
	//Creation of Organization
	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException
    {
				 Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyPaginationOfSpace.xls","organization");
				 return arrayObject;
	}
				
	@Test(priority=3, dependsOnMethods ="setUp",dataProvider = "creatOrg")
	public void OrgcreationTocreateSpaceINIT(String orgName) throws InterruptedException
	{ 
			driver.navigate().refresh();
			Thread.sleep(1000);
			creatOrg(orgName);
			Thread.sleep(500);
	}
		 
	@DataProvider(name="createSpace")
	 public Object[][] createSpace() throws BiffException
	{
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyPaginationOfSpace.xls","space");
					 return arrayObject;
	}
					
	@Test(priority=4, dependsOnMethods ="setUp",dataProvider = "createSpace")
	public void SpaceCreationForpagination(String spaceName) throws InterruptedException
	{
					creatSpace(spaceName);
					Thread.sleep(1000);
					driver.findElement(By.xpath((or_getproperty("NavigateToOrg")))).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath(or_getproperty("abc"))).click();
					int rowCount=driver.findElements(By.xpath((or_getproperty("firstspace")))).size();
					TestLog.info("Count of No.Of Space = " + rowCount);
					System.out.println(" Count of No of Spaces "+ rowCount );
					Thread.sleep(1000); 			
	}
	@Test(priority=5)
	public void PaginationOFSpace() throws InterruptedException, IOException
	{
		Thread.sleep(4000);
		VerifyPagination("firstspace","space","spacePagination");	
		driver.navigate().back();
		Thread.sleep(3000);
		driver.navigate().back();
		Thread.sleep(2000);
		TestLog.info("pagination of space ends here ");
	}
		
	
//Scenario 3:-To verify whether user is able to delete an org
	
			
	@Test(priority=6, dependsOnMethods ="setUp",dataProvider = "creatOrg")
	 	public void orgCreation(String orgName) throws InterruptedException
			{  
		       driver.navigate().back();
			    Thread.sleep(1000);
				creatOrg(orgName);
				Thread.sleep(500);
			}
	

	@Test(priority=7, dependsOnMethods = "setUp",dataProvider = "creatOrg")
	public void orgDeletetion(String orgName) throws InterruptedException
	    {
	TestLog.info("In delete org");
	
	Thread.sleep(1000);
	      deleteOrgSpace(orgName, "btnDeleteOrg");
	      TestLog.info("delete org method  ends here ");
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
		TestLog.info("************** Test Case 33 VerifyPagination Ends :- **************");
	}
	
}


