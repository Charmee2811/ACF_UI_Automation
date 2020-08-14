package cf.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
//import org.apache.poi.hssf.record.formula.functions.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemAction;
import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

//  To Delete org and Space
public class Delete_Org_Space extends itemVerification
{
	String desc = "To verify whether Facebook Link is navigated to Facebook Page";
	int rowNo=24;
	
	/*@BeforeClass
	public void isTestExecutableCall()
	{
		isTestExecutableBaseClass("TestCase21");
	}*/
	
  @Test		
   public void setUp() throws Exception
		{
	       TestLog.info("************** Test Case Delete Starting :- **************");
			openBrowser();
			openURL() ;
			login();	
		}
  @DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "cf_testData_TCDelete.xls","orgSpace");
		return arrayObject;
	}
			@Test(dependsOnMethods = "setUp",dataProvider = "creatOrg")
			public void deleteOrg(String orgName,String spaceName,String appname) throws InterruptedException
			{
				String orgList,spaceList,appList;
				  orgList= "listvieworg";
				  spaceList ="listviewspace";
				  appList = "listviewapp";
				  
				traverse(orgName,orgList);	
				Thread.sleep(7000);
				if (driver.findElements(By.xpath(or_getproperty("listviewspace"))).size()!=0)
				{
					 //TestLog.info("************** Inside IF list view:- **************");
					traverse(spaceName,spaceList);
					Thread.sleep(15000);
					driver.findElement(By.xpath(or_getproperty("btnDeletSpace"))).click();
					Thread.sleep(1000);
					driver.switchTo();
					driver.findElement(By.xpath(or_getproperty("txtDeletetName"))).sendKeys(spaceName);
					Thread.sleep(1000);
					driver.findElement(By.xpath(or_getproperty("btnDelConfirm"))).click();
					Thread.sleep(2000);
					Assert.assertTrue(true,"Space deleted ");
					TestLog.info("Space deleted");
					Thread.sleep(8500);
					//org deletion
					 driver.findElement(By.xpath(or_getproperty("btnDeleteOrg"))).click();
					Thread.sleep(1000);
					 driver.switchTo();
					 driver.findElement(By.xpath(or_getproperty("txtDeletetName"))).sendKeys(orgName);
					Thread.sleep(1000);
					 driver.findElement(By.xpath(or_getproperty("btnDelConfirm"))).click();
					Thread.sleep(2000);
					 Assert.assertTrue(true,"Org deleted ");
					 TestLog.info("Org deleted");
					Thread.sleep(3000);
				}
				else 
					//if (driver.findElement(By.xpath(or_getproperty("noneSpace"))).isDisplayed())
				{
					Thread.sleep(5000);
					TestLog.info("************** Inside IF non space view:- **************");
					  driver.findElement(By.xpath(or_getproperty("btnDeleteOrg"))).click();
					Thread.sleep(1000);
					  driver.switchTo();
					  driver.findElement(By.xpath(or_getproperty("txtDeletetName"))).sendKeys(orgName);
					Thread.sleep(1000);
					  driver.findElement(By.xpath(or_getproperty("btnDelConfirm"))).click();
					Thread.sleep(2000);
					  Assert.assertTrue(true,"Org deleted ");
					  TestLog.info("Org deleted");
					Thread.sleep(3000);
				}
				//driver.close();	
			}
			
			
		}


