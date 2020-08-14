package cf.test;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;

public class CreateQuotaPlanForOrg extends itemVerification {
	
	String desc = " Verify the Functionality of Creating a New Quota Plan for an Org on Console";
	int rowNo=37;
/*	
	@BeforeClass
	public void isTestExecutableCall()
	{
	//	test=extent.startTest(this.getClass().getSimpleName());			
		isTestExecutableBaseClass(this.getClass().getSimpleName());
	}
*/
	@Test(priority=1)
	public void setUp() throws Exception 
	{
		TestLog.info("*********Test Case 36 Verify the Updation of User provided Service instances on chrome browser for UI through selenium :- **********");
		openBrowser();
		openURL();
		login();
	}

	//Creation of Organization
	@DataProvider(name="createQuotaPlanForOrg")
	 public Object[][] creatOrg() throws BiffException
	 {
		Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "CreateQuotaPlanForOrg.xls","createNewQuotaPlan");
		return arrayObject;
	 }
	
	@Test(priority=2, dependsOnMethods="setUp", dataProvider="createQuotaPlanForOrg")
	public void createQuotaPlanForOrg(String QuotaName, String TotalServices, String TotalRoutes, String MemoryLimit, String InstanceMemoryLimit) throws InterruptedException
	{
		getObject("manageOrgQuotalink");
		verifycreateQuotaPlanForOrg(QuotaName,TotalServices,TotalRoutes,MemoryLimit,InstanceMemoryLimit);
	}
}
