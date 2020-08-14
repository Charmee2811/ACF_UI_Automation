package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class VerifyUserPwrdValidation extends itemVerification{
 
	String desc = "To verify the  invalid characters for password   using selenium Script";
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
		TestLog.info("************** Test Case 25 :  VerifyUserPwrdValidation Starting :- **************");
		openBrowser();
		openURL() ;
		login();
		
	}
	
	
	//Creation of Organisation  for User
	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyUserPwrdValidation.xls","organization");
			return arrayObject;
	}
	
		
	@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
	public void orgCreation(String orgName) throws InterruptedException
	{
			creatOrg(orgName);
			
	}
	
	//Password validation while user creation
	@DataProvider(name="creatUser")
	public Object[][] creatUser() throws BiffException  {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "VerifyUserPwrdValidation.xls","user");
		return arrayObject;
	}
	
	@Test(priority=4 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
	public void passwordvalidation(String email,String pwrd1, String pwrd2, String pwrd3 ,String pwrd4, String pwrd5, String pwrd6 ,String pwrd7 ,String pwrd8, String pwrd9,String pwrd10,String pwrd11,String pwrd12, String pwrd13) throws Exception
	{
		validateUserPwrd(email,pwrd1);// #
		validateUserPwrd(email,pwrd2);// %
		validateUserPwrd(email,pwrd3);// &
		validateUserPwrd(email,pwrd4);// +
		validateUserPwrd(email,pwrd5);// [
		validateUserPwrd(email,pwrd6);// ]
		validateUserPwrd(email,pwrd7);// \
		validateUserPwrd(email,pwrd8);// /
		validateUserPwrd(email,pwrd9);// "
		validateUserPwrd(email,pwrd10);// '
		validateUserPwrd(email,pwrd11);// <
		validateUserPwrd(email,pwrd12);// >
		validateUserPwrd(email,pwrd13);// -
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
		TestLog.info("************** Test Case 25  ENDS  VerifyUserPwrdValidation:- **************");
		
	}	


	
}
