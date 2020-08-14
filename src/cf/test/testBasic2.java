package cf.test;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

import cf.Base.itemVerification;
import cf_Util.ExcelReadAndWrite;
import cf_Util.TestLog;
import cf_Util.TestUtil;
import cf_Util.awsReportsUplod;
import cf_Util.sendMail;

public class testBasic2 extends itemVerification {
	 public static String env;
	
	@SuppressWarnings("deprecation")
	@BeforeSuite
	public static ExtentReports remove() throws IOException
	{
		
    extent = new ExtentReports(Path,true,DisplayOrder.NEWEST_FIRST);
     
		String url = config_getproperty("cfURL");
			switch (url) 
			  {
				
				case "https://console.sys.preprod.cfdev.canopy-cloud.com":
					env = "PreProd Console Environment";
					break;
				case "https://angular_app.apps.eu.cfdev.canopy-cloud.com":
					env = "CSB  Environment";
					break;
				case "https://console.sys.eu.cfdev.canopy-cloud.com/":
					env = "dev-Console Environment";
					break;
				case "https://test-console.sys.eu.cfdev.canopy-cloud.com/":
					env = "Test Console  Environment";
					break;
				case "https://console.sys.eu.cfdev.canopy-cloud.com/#/login":
					env = "Dev Console  Environment";
					break;	
				case "https://console.sys.eu01.cf.canopy-cloud.com":
					env= "Production Environment eu01";
					break;
				case "https://console.sys.tai-nl.cfdev.canopy-cloud.com/#/login":
					env="TAI Dev environmtent";
				default:
					TestLog.info("Invalid URL");
					
					break;
				}
     
     extent
	     .addSystemInfo("Environment", env )
	     .addSystemInfo("Browser", config_getproperty("browserType"))
	     .addSystemInfo("Release", config_getproperty("release"));
     
		TestLog.info("######################### Starting on New Suite:-  " + systemTime + "#################################");
		cf_Util.ExcelReadAndWrite.clearOldFile();
		
		DeleteScreenShots(SRC_FOLDER_Screenshots);
		TestLog.info("Deleted files inside Screenshot Folder");
		
		DeleteReportsAndZipScreenshot(SRC_FOLDER_backupReports,null);
		TestLog.info("Deleted files inside backup Report Folder");
		
		//DeleteReportsAndZipScreenshot(SRC_FOLDER_CustomReports, "TestResultsData.xls");
		//TestLog.info("Deleted Customreports folder leaving TestResultsData.xls file remain in folder");
		
		DeleteReportsAndZipScreenshot(SRC_FOLDER_ZipScreenshots, null);
		TestLog.info("Deleted Screenshots insise ZipScreentshots folder");
	    	return extent;
	 }
}
