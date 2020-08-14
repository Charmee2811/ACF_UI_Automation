package cf.test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
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

public class VerifyMultiLingualSupport extends itemVerification{

	String desc = " To verify for multi-lingual support on console for dashboard using selenium Script";
	int rowNo=30;
	
	
	@BeforeClass
	public void isTestExecutableCall()
	{

		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
	
	  @Test	(priority= 1)	
	  public void VerifyLangOnLoginPage() throws Exception
	  {	
		  TestLog.info("************** Test Case 30 Starting  VerifyMultiLanglDashbrdSuprt:- **************");
		  openBrowser();
		  openURL() ;
		  Thread.sleep(4000);
		  Select dropdown1 = new Select(driver.findElement(By.xpath(or_getproperty("cmbLangChange"))));
		  dropdown1.selectByVisibleText("French");
		  Thread.sleep(2000);
		  String CurrentLang= dropdown1.getFirstSelectedOption().getText();
		  Thread.sleep(500);
		  verifyChnagedLanguage(CurrentLang, "Francais", "Login");
		    
		  loginFrench();
			
	  }
	
	//Creation of Organation for non admin login
	@DataProvider(name="traveseinfo")
	public Object[][] traveseinfo() throws BiffException 
	{
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "verifyMultiLangSprt.xls","Data");
				return arrayObject;
	}
	@Test(priority= 3 ,dataProvider= "traveseinfo" )
	public void VerifyChangeLangOnDashboarcdPage(String OrgName) throws InterruptedException, IOException
	{	
		TestLog.info("In verifyLangonDashboardPage");
		String lang= driver.findElement(By.xpath(or_getproperty("languageChangeLink"))).getText();
		System.out.println("*********************************"+lang);
		verifyChnagedLanguage(lang, "Francais", "dashboard");
		CheckLanguageChange("languageChangeLink","englishLink","frenchLanguage");
		Thread.sleep(1000);
		creatOrg(OrgName);
	}
	
	@Test(priority= 4 ,dataProvider= "traveseinfo")
	public void VerifyChangeLangOnOrgPage(String OrgName) throws InterruptedException, IOException
	{	
		String lang= driver.findElement(By.xpath(or_getproperty("languageChangeLink"))).getText();
		System.out.println("*********************************"+lang);
		verifyChnagedLanguage(lang, "English", "Org");
		CheckLanguageChange("languageChangeLink","frenchLink","orgPageLangChkText");
		String lang1= driver.findElement(By.xpath(or_getproperty("languageChangeLink"))).getText();
		System.out.println("*********************************"+lang1);
		verifyChnagedLanguage(lang1, "Francais", "Org");
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
			TestLog.info("************** Test Case 30  ENDS  Verifymulti_lingualsupport:- **************");
	}	

}
