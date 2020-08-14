package cf.test;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.apache.bcel.verifier.statics.Pass1Verifier;
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
//import cf_Util.ExcelReadAndWrite;

public class ChangeNonAdminUserPass extends itemVerification{
	String desc = "To verify if Non Admin user password is changed successfully and able to open dashboard page of Admin user with newly changed password";
	int rowNo=14;
	@BeforeClass
	public void isTestExecutableCall()
	{
		test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}

	@Test(priority=1)
	public void setUp() throws Exception
	{
		TestLog.info("************** Test Case 14 :  ChangeNonAdminUserPass Starting :- **************");
		openBrowser();
		openURL() ;
		login();
		
	}

	@DataProvider(name="creatOrg")
	public Object[][] creatOrg() throws BiffException  {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "ChangepassworOfNonAdminUser.xls","organization");
		return arrayObject;
	}
	
	@Test(priority=2,dependsOnMethods ="setUp",dataProvider = "creatOrg")
	public void orgCreation(String orgName) throws InterruptedException
	{
		creatOrg(orgName);
	}
	
	@DataProvider(name="creatUser")
	public Object[][] creatUser() throws BiffException  {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "ChangepassworOfNonAdminUser.xls","user");
		return arrayObject;
	}
			
	@Test(priority=3 ,dependsOnMethods ="orgCreation",dataProvider = "creatUser")
	public void userCreation(String email,String passWord ,String newPass) throws InterruptedException
	{
		creatUser(email,passWord);	
	}
	
	@Test(priority=4,dependsOnMethods ="userCreation",dataProvider = "creatUser")
	public void userLogin(String email,String passWord ,String newPass) throws InterruptedException, IOException
	{
		logout();	
		nonAdminLogin(email,passWord);
		TestLog.info("Email is :- " + email + "   Pwd is :- " + passWord);
		Thread.sleep(4000);
		verifyLoginUser(email);	
		Thread.sleep(2000);
	}
					
	@Test(priority=5,dependsOnMethods ="userLogin", dataProvider= "creatUser")
	public void NonAdminChangePassword(String email,String passWord ,String newPass)throws InterruptedException, IOException
	{
		nonAdminChangePass(passWord, newPass);
	}
	
	@Test(priority=6 ,dependsOnMethods= "NonAdminChangePassword" ,dataProvider= "creatUser")
	public void loginWithNewCreds(String email,String passWord ,String newPass) throws InterruptedException, IOException
	{
		logout();
		nonAdminLogin(email,newPass);
		Thread.sleep(5000);
		TestLog.info("Login with new credentials Email is :- " + email + "   Pwd is :- " + passWord);
		verifyLoginUser(email);	
		Thread.sleep(2000);
	}	
	
	@Test(priority=7 ,dependsOnMethods= "NonAdminChangePassword" ,dataProvider= "creatUser")
	public void WritePassIntoExcel(String email,String passWord ,String newPass) throws Exception
	{
		cf_Util.ExcelReadAndWrite.SWAPPass(passWord, newPass);
		TestLog.info("Password written into excel sheet successfully ");//modified by harshali 
		test.log(LogStatus.PASS, "Password written successfully into Excel sheet");
	}
			
	@AfterMethod
	//(alwaysRun=true)
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
		TestLog.info("************** Test Case 14 ChangeNonAdminUserPass ENDS  :- **************");
	}
			

}
