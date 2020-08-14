	/*
	 **************************************************************
	 Author : ACF Test Automation Team
	 Purpose : TestBase Class
	 Date : 26/02/2016
	 Test Case Name : NA
	 **************************************************************
	 */
	package cf.Base;
	
	import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;

import cf_Util.TestLog;
import cf_Util.TestUtil;
import cf_Util.xls_reader;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
	
	public class TestBase {
		
		protected final static String vaultIp = "https://10.4.2.18:8200";//modified by charmee 18 dec
	    protected final static String  vaultRootToken = "4c833720-7dd9-b0fa-e19f-08bcd28c0732";//chnaged on 12 th Dec
	  
		public int excelRowCount = 1;
		public static WebDriver driver = null;
		public static Properties OR = null;
		public static boolean isInitalized = false;
		public static xls_reader test_data_xls = new xls_reader(cf.Base.TestBase.config_getproperty("customReports") + "TestResultsData.xls");
	    protected static String zipPath = System.getProperty("user.dir") + "\\ZipScreenShots\\" ;
		protected static final String SRC_FOLDER_Screenshots = System.getProperty("user.dir") + "\\ScreenShots";
		protected static final String SRC_FOLDER_backupReports = System.getProperty("user.dir") + "\\CustomReports\\backup";
		protected static final String SRC_FOLDER_ZipScreenshots = System.getProperty("user.dir") + "\\ZipScreenShots";
	    public static boolean isBrowserOpened = false;
	    public static boolean isURLOpened = false;
	    public static int machine_id = 0;
	    public static int row = 0;
	    public static int columns = 0;
	    public String time;
	    public String screenshotPath;
	    public static String fileName;

	    protected static ExtentReports extent;
	    protected static ExtentTest test;
	   public static String Path= System.getProperty("user.dir")+ "\\test-output\\AutomationReport.html";
	   static String ImagesPath = System.getProperty("user.dir")+ "\\test-output\\";
	   public static String  result;
	   public static String systemTime;
	
	protected TestBase() {
		DateFormat dateFormat = new SimpleDateFormat(config_getproperty("dateFormat"));
		Calendar cal = Calendar.getInstance();
		systemTime = "" + dateFormat.format(cal.getTime());	
	}
	
	public void openBrowser() {
		if (!isBrowserOpened) {
			if (config_getproperty("browserType").equals("MOZILLA")){
				DesiredCapabilities caps = new FirefoxOptions().setProfile(new FirefoxProfile()).addTo(DesiredCapabilities.firefox());
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\geckodriver.exe");
				
				driver = new FirefoxDriver(caps);
				TestLog.info("MOZZILA Browser opened");
				}
			else if (config_getproperty("browserType").equals("IE")) {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				TestLog.info("IE Browser opened");
			} else if (config_getproperty("browserType").equals("CHROME")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments(config_getproperty("LanguageType"));
				driver = new ChromeDriver();
				TestLog.info("CHROME Browser opened");
			}
	
			isBrowserOpened = true;
			// String waitTime=config_getproperty("default_implicitWait");
			// driver.manage().timeouts().implicitlyWait(Long.parseLong(waitTime),
			// TimeUnit.SECONDS);
			driver.manage().window().maximize();
			TestLog.info("window Maximised");
		}
	
	}
	
	// Code to close Browser
	public void closeBrowser() {
		driver.quit();
		isBrowserOpened = false;
	}
	
	// Code to load configuration properity file
	public static String config_getproperty(String propertyname) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "//src//configuration//config.properties");
			// load a properties file
			prop.load(input);
		}
	
		catch (IOException ex) {
			ex.printStackTrace();
		}
	
		finally {
			if (input != null) {
				try {
					input.close();
				}
	
				catch (IOException e) {
					e.printStackTrace();
				}
	
			}
		}
	
		// Return the property value
		return prop.getProperty(propertyname);
	}
	
	public static String or_getproperty(String propertyname) {
		Properties prop = new Properties();
		InputStream input = null;
	
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "//src//configuration//OR_new.properties");
			// load a properties file
			prop.load(input);
		}
	
		catch (IOException ex) {
			ex.printStackTrace();
		}
	
		finally {
			if (input != null) {
				try {
					input.close();
				}
	
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
		// Return the property value
		return prop.getProperty(propertyname);
	}
	
	public void switchWin() {
	
		// Switch to newly opened window -RSS and get the page title
		// String winHandle = driver.getWindowHandles().toString();
		for (String winHandle1 : driver.getWindowHandles())
			driver.switchTo().window(winHandle1);
	}
	
	public String login() throws Exception {
		Thread.sleep(2000);
		//To get encrypted value of password
		//String StrDataToEncrypt= config_getproperty("password");
		//String encryptedpassword = cf_Util.encryptDecrypt.encrypt(StrDataToEncrypt);
		//System.out.println("encrypted passswrod is  ::"+encryptedpassword );
		

		String strDataToDecrypt = config_getproperty("EncryptedStringPass");
		  System.out.println("start data to decrypt : "+ strDataToDecrypt);
		String decrpteddata=cf_Util.encryptDecrypt.decrypt(strDataToDecrypt);
		
    	System.out.println("decrypted password : "+ decrpteddata);
    		
		if (config_getproperty("cfTitle").equals(driver.getTitle())) {
			driver.findElement(By.xpath(or_getproperty("loginName"))).sendKeys(config_getproperty("adminUsername"));
			driver.findElement(By.xpath(or_getproperty("loginPassword"))).sendKeys(decrpteddata);
			driver.findElement(By.xpath(or_getproperty("loginBtn"))).click();
			Thread.sleep(6000);
	
			if (driver.findElement(By.xpath(or_getproperty("createOrgLink"))).isDisplayed())
	  		{
	  			result= "true";
	  			 TestLog.info("User Logged In Successfully");
	  			Assert.assertTrue(true,"User Logged In Successfully");
				
	  		}
	  		else
	  		{
	  			result= "false";
	  			 TestLog.info("Non admin user  not able to  Logged In ");
				 Assert.assertTrue(false,"Non admin user not  able to Logged In"); 
	  		}
			
			TestLog.info("Login sucessfull");
			
		}
		
		return result;
		
	}
	
	public String loginFrench() throws Exception 
	{
		Thread.sleep(2000);
		String strDataToDecrypt = config_getproperty("EncryptedStringPass");
		String decrpteddata=cf_Util.encryptDecrypt.decrypt(strDataToDecrypt);
		
		if (config_getproperty("cfTitle").equals(driver.getTitle())) {
			driver.findElement(By.xpath(or_getproperty("loginName"))).sendKeys(config_getproperty("adminUsername"));
			driver.findElement(By.xpath(or_getproperty("loginPassword"))).sendKeys(decrpteddata);
			driver.findElement(By.xpath(or_getproperty("loginBtn"))).click();
			Thread.sleep(6000L);
	
			if (driver.findElement(By.xpath(or_getproperty("createOrgLinkFrench"))).isDisplayed())
	  		{
	  			result= "true";
	  			 TestLog.info("User Logged In Successfully");
	  			Assert.assertTrue(true,"User Logged In Successfully");		
	  		}
	  		else
	  		{
	  			result= "false";
	  			 TestLog.info("Non admin user  not able to  Logged In ");
				 Assert.assertTrue(false,"Non admin user not  able to Logged In"); 
	  		}	
			TestLog.info("Login sucessfull");		
		}
		
		return result;
		
	}
	
	public String loginForFrenchLang() throws InterruptedException, IOException {
		Thread.sleep(2000);
		
		if (config_getproperty("cfTitle").equals(driver.getTitle())) {
			driver.findElement(By.xpath(or_getproperty("loginName"))).sendKeys(config_getproperty("adminUsername"));
			driver.findElement(By.xpath(or_getproperty("loginPassword"))).sendKeys(config_getproperty("adminPasswd"));
			driver.findElement(By.xpath(or_getproperty("loginBtn"))).click();
			Thread.sleep(5000);
	
			if (driver.findElement(By.xpath(or_getproperty("createOrgLink"))).isDisplayed())
	  		{
	  			result= "true";
	  			 TestLog.info(" user Logged In Successfully");
				 Assert.assertTrue(true," user Logged In Successfully"); 
	  		}
	  		else
	  		{
	  			result= "false";
	  			 TestLog.info(" user  not able to Log In ");
				 Assert.assertTrue(false," user not  able to Log In"); 
	  		}
			
		}
		
		return result;	
	}
	
	/*
	 * //Extra code for Login if
	 * (config_getproperty("cfURL").contains("preprod")) {
	 * TestLog.info("PreProd Link hence Click Dashbord Enabled"); if
	 * (driver.findElement(By.xpath(or_getproperty("dashboard"))).isDisplayed())
	 * { result = true;
	 * driver.findElement(By.xpath(or_getproperty("dashboard"))).click();
	 * Assert.assertTrue(true, "Dashboard is present"); } else { result = false;
	 * TestLog.info("Test Enviroment  hence Click Dashbord NOT Enabled");
	 * Assert.assertTrue(false, "Dashboard is not present"); } }
	 * 
	 * if (driver.getCurrentUrl().contains("console")) { result = true;
	 * TestLog.info("Test Enviroment  hence Click Dashbord NOT Enabled"); } else
	 * result = false; } else { result = false;
	 * Assert.assertTrue(false,"Login Page not avaiable"); }
	 */
	
	public void logout() {
		if (driver.findElement(By.xpath(or_getproperty("logedInUsr"))).isDisplayed()) {
			result = "true";
			driver.findElement(By.xpath(or_getproperty("logedInUsr"))).click();
			driver.findElement(By.xpath(or_getproperty("logoutLink"))).click();
			Assert.assertEquals((config_getproperty("cfTitle")), driver.getTitle());
			TestLog.info("User LoggedOut");
		} else {
			result = "false";
			TestLog.info("LogOut btn link not visible");
			Assert.assertTrue(false, "LogOut btn link not visible");
		}
	}
	
	public static WebElement getObject(String xpathKey) {
		try {
			new FluentWait<WebDriver>(driver).withTimeout(25000, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(or_getproperty(xpathKey))));
			WebElement x = driver.findElement(By.xpath(or_getproperty(xpathKey)));
			return x;
		}
	
		catch (Throwable t) {
			// ErrorUtil.addVerificationFailure(t);
			return null;
		}
	}
	
	public static boolean compareText(String expectedVal, String actualValue) {
		try {
			Assert.assertEquals(actualValue, expectedVal);
		}
	
		catch (Throwable t) {
			// ErrorUtil.addVerificationFailure(t);
			return false;
		}
	
		return true;
	}
	
	public boolean checkElementPresence(String string) {
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + string + "')]"));
	
		try {
			Assert.assertTrue(list.size() > 0, "Text not found!");
		}
	
		catch (Throwable t) {
			// ErrorUtil.addVerificationFailure(t);
			return false;
		}
	
		return true;
	}
	
	public boolean isClickable(WebElement webE) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(webE));
			return true;
		}
	
		catch (Exception e) {
			return false;
		}
	}
	
	public static void click2(String xpathKey)
	{
	WebElement element= driver.findElement(By.linkText(xpathKey));
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	executor.executeScript("arguments[0].click();", element);
	}
	
	
	public WebElement Dropdown(String idkey) {
		WebElement DropDown = driver.findElement(By.id(idkey));
		return DropDown;
	}
	
	public boolean openURL() {
		driver.get(config_getproperty("cfURL"));
		try {
			TestLog.info("URL Opened");
		}
		catch (Throwable t) {
			 TestLog.info("URL NOt Opening");
			return false;
		}
	
		isURLOpened = true;
		return isURLOpened;
	}
	
	
	public String CaptureScreenshot(String Error) throws IOException
	
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_");
		Calendar cal = Calendar.getInstance();
		time = "" + dateFormat.format(cal.getTime());
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		screenshotPath = System.getProperty("user.dir") + "\\src\\Screenshots\\" + Error + "_" + time + ".png";
		FileUtils.copyFile(scrFile, new File(screenshotPath));
		return screenshotPath;
	}
	
	public static String CaptureScreen(WebDriver driver, String ImagesPath)
	   {
	       TakesScreenshot oScn = (TakesScreenshot) driver;
	       File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
	    File oDest = new File(ImagesPath+".jpg");
	    try {
	         FileUtils.copyFile(oScnShot, oDest);
	    } catch (IOException e) {System.out.println(e.getMessage());}
	    return ImagesPath+".jpg";
	           }
	
	/**
	 * This function will take screenshot
	 * @param webdriver
	 * @param fileWithPath
	 * @throws Exception
	 */
	public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{
		//Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);
		//Call getScreenshotAs method to create image file
				File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			//Move image file to new destination 
				File DestFile=new File(fileWithPath);
				//Copy file at destination
				FileUtils.copyFile(SrcFile, DestFile);
			
	}
	
	public void scroll() {
		/*
		 * Actions action = new Actions(driver); //Using PageUP/Down Keys
		 * action.sendKeys(Keys.PAGE_DOWN); waitSeconds(2);
		 * action.click(driver.findElement(By.partialLinkText("Google.com.ph")))
		 * .perform();
		 */
		JavascriptExecutor jse = (JavascriptExecutor) driver;
	
		// jse.executeScript("window.scrollBy(0,250)", ""); //Scroll Down
		// jse.executeScript("window.scrollBy(0,-250)", ""); //Scroll UP
	
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // Bottom // of
																			
	}
	
	
	
	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}
	
	protected boolean skip = false;
	
	public void screenshot(String failSCPath, String passSCPath) throws IOException {
		// TestLog.info ("*******Base Class**** " + res);
		
		//String screenshots = config_getproperty("screenshots") + this.getClass().getSimpleName() + "\\" + res.getMethod().getMethodName() ;
		 
		CaptureScreen(driver, passSCPath);
	/*if (driver != null) commented as facing NPE issue :-6-11-2017
	{
		
		try {
			
		switch(result)
		{
			case "true":
				//TestLog.info("In true section of screenshot");
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(passSCPath  + ".jpg"));//removed systemtime :charmee-18Sept
				
			break;
			case "false" :
				//TestLog.info("In FALSE section of screenshot");
				File scrFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile1, new File(failSCPath + ".jpg")); //removed systemtime :charmee-18Sept
				
			break;
			case "skip":
			  System.out.println("Test Case was Skip so no Screen shot taken");
			  test.log(LogStatus.SKIP, "Method skipped");
			 break;
			default :
			  System.out.print("IN Default section NullPointerException caught due to Run screen shot method always but it was skip");
			 test.log(LogStatus.INFO, "method not valid");
		} 
		}catch (NullPointerException e) {
			System.out.print(" In Exception :- NullPointerException caught due to Run screen shot method always but it was skip");
			driver.close();
		}
		}
   else
	 System.out.print("NullPointerException caught due to Run screen shot method always but it was skip");
	  driver.close();*/	
	}
	
	
	
	
	public void isTestExecutableBaseClass(String TestCaseName) {
		if (!TestUtil.isExecutable(TestCaseName)) {
			skip = true;
			TestLog.info("This test case was set to No Run hence Skiped " + TestCaseName);
			result = "skip";
			test.log(LogStatus.SKIP, "Test was skipped");
			throw new SkipException("Skipping execution of test case :- " + TestCaseName + " as Run mode is set to No");
		}
		
	}
	
	public void generateReport(int rowNo, String desc) {
		if (driver != null) {
		closeBrowser();
		TestLog.info("closed browser");
		}
	
		String failSCPath = config_getproperty("failScreenShotPath") + this.getClass().getSimpleName();
		String passSCPath = config_getproperty("passScreenShotPath") + this.getClass().getSimpleName();
	
		String url =  config_getproperty("cfURL");
		String browser = config_getproperty("browserType");
		//test_data_xls.setCellData("EnvDetails", "Enviroment", 1,url);
		//test_data_xls.setCellData("EnvDetails", "Browser Type", 2, browser);
		
		excelRowCount++;
		if (!skip) {
			if (result.equalsIgnoreCase("false")) {
				test_data_xls.setCellData("TestResult", "TestCase_Number", rowNo, this.getClass().getSimpleName());
				test_data_xls.setCellData("TestResult", "Description", rowNo, desc);
				test_data_xls.setCellData("TestResult", "Result", rowNo, "FAIL");
				test_data_xls.setCellData("TestResult", "Executed_Date", rowNo, systemTime);
				test_data_xls.setCellData("TestResult", "BrowserType", rowNo, browser);
				test_data_xls.setCellData("TestResult", "Env_URL", rowNo, url);
				test_data_xls.setCellData("TestResult", "ScreeonShot_Path", rowNo, "ScreenShots uploaded on S3 bucket acf_ui_automation");
	
			} else {
				test_data_xls.setCellData("TestResult", "TestCase_Number", rowNo, this.getClass().getSimpleName());
				test_data_xls.setCellData("TestResult", "Description", rowNo, desc);
				test_data_xls.setCellData("TestResult", "Result", rowNo, "PASS");
				test_data_xls.setCellData("TestResult", "Executed_Date", rowNo, systemTime);
				test_data_xls.setCellData("TestResult", "BrowserType", rowNo, browser);
				test_data_xls.setCellData("TestResult", "Env_URL", rowNo, url);
				test_data_xls.setCellData("TestResult", "ScreeonShot_Path", rowNo, "ScreenShots uploaded on S3 bucket acf_ui_automation");
			}
		} else {
			test_data_xls.setCellData("TestResult", "TestCase_Number", rowNo, this.getClass().getSimpleName());
			test_data_xls.setCellData("TestResult", "Description", rowNo, desc);
			test_data_xls.setCellData("TestResult", "Result", rowNo, "SKIP");
			test_data_xls.setCellData("TestResult", "Executed_Date", rowNo, systemTime);
			test_data_xls.setCellData("TestResult", "BrowserType", rowNo, browser);
			test_data_xls.setCellData("TestResult", "Env_URL", rowNo, url);
			test_data_xls.setCellData("TestResult", "ScreeonShot_Path", rowNo, "Test Skip So No Screenshot taken");
			}
		}
	
	
	 public static void addToZip(String sourceDirPath) throws IOException 
	  {	 
		   String zipFilePath = zipPath + "ScreenShots"+ systemTime;
		    java.nio.file.Path p = Files.createFile(Paths.get(zipFilePath));
		    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
		        java.nio.file.Path pp = Paths.get(sourceDirPath);
		        Files.walk(pp)
		          .filter(path -> !Files.isDirectory(path))
		          .forEach(path -> {
		              ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
		              try {
		                  zs.putNextEntry(zipEntry);
		                  zs.write(Files.readAllBytes(path));
		                  zs.closeEntry();
		            } catch (Exception e) {
		                System.err.println(e);
		            }
		          });
		    }
		}
		
	public static void delete(File file, String wantedfolder , String otherfolder)
	    	throws IOException{

	    	System.out.println("############### Value for Folder is :-    " + otherfolder );
	    	if(file.isDirectory()){
	    		
	    		//directory is empty, then delete it
	    		if((file.list().length==0)){
	    			 if(((file.getName().equals(wantedfolder)))){
	    				System.out.println("not deleting empty main directory"+ file.getAbsolutePath() );
	    			 }
	    			 else if(!(file.getName().equals(otherfolder))){
	    		
	    		        file.delete();
	    		       System.out.println("empty subDirectory is deleted : "+ file.getAbsolutePath());
	    			 }
	    			 
	    		}
	    		else{

	    		   //list all the directory contents
	        	   String files[] = file.list();
	        	   for (String temp : files) {
	        	      File fileDelete = new File(file, temp);
	        	      //recursive delete
	        	     delete(fileDelete,wantedfolder,otherfolder);
	        	     }

	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	        		   if (((file.getName().equals(otherfolder))))   
	        		   {
	        			   System.out.println("not deleting main dir "+ file);
	        		   }
	        		   else if(!((file.getName().equals(otherfolder))))
	        		   {
	        			 // file.delete();
	        	     //System.out.println("SubDirectory  of main Directory is deleted : "+ file.getAbsolutePath());
	        		   }
	        	   }
	    		}

	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("File in the subDirectory is deleted : " + file.getAbsolutePath());
	    	}
	    	
	    }
	
	public void scrollTillPagination() {
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, 1300)"); // Bottom // of
																			
	}
	public void trueCondition(String description)
	{
        result="true";
        TestLog.info("In true section description is present :- " +description );
        Assert.assertTrue(true,"In True Section Desc is Present:- "+ description);
     }
	public void falseCondition(String description)
	{
		result="false";
		TestLog.info("In False Section description not present:- " +description );
        Assert.assertTrue(false, "In False Section description not present:- " +description);
     }
	public void ExtntRep(String methodName,ITestResult result1) throws IOException
	{
		//TestLog.info("Result Value :- " + result);
	   if (driver != null) 
	  {	
		try
		{		
			if (result1.getStatus()==ITestResult.FAILURE)
			{
				test.log(LogStatus.FAIL, methodName+ " Method failed");
				TestLog.info(methodName+ " method failed");
			}	
		else
			{
			    test.log(LogStatus.PASS, methodName +" Method passed");
				TestLog.info(methodName+ " method passed");
			}  
		}  
		catch (NullPointerException e) 
		{
			System.out.print("In ExtntRep method NullPointerException caught due to Run screen shot method always but it was skip");
			TestLog.info("In ExtntRep method NullPointerException caught due to Run screen shot method always but it was skip");
		}
	 }
   else
	 System.out.print("driver is null");
	   TestLog.info("In ExtntRep method driver is null");
			
	}
	
	public static String getFileName(String filePathDetail)
    {
    	String filePathName;
        File filePath = new File(filePathDetail);
  	
         for (File files : filePath.listFiles()) 
         {
              filePathName = files.getPath();
             if (files.isFile())
             {
           	    System.out.println("FullPath is   " + filePathName);
           	    fileName = filePathName.substring(filePathName.lastIndexOf("\\")+1);	    
           	 }
         }
         System.out.println("File Name is   " + fileName);
		return fileName;				
    }
	


public static void DeleteScreenShots (String FolderName){
	

	File directory = new File(FolderName);
	
	if(!directory.exists())
       System.out.println("Directory does not exist.");
    else
    {
       try
       {
    	   delete(directory, "FailTestCaseScreenShot","PassTestCaseScreenShot");
           delete(directory, "PassTestCaseScreenShot", "FailTestCaseScreenShot");
       }catch(IOException e)
       {
           e.printStackTrace();
           System.exit(0);
       }
  }
	
}
public static void DeleteReportsAndZipScreenshot (String FolderName,String RequireFile){
	File directory = new File (FolderName);
	   String[] myFiles;    
	       if(directory.isDirectory()){
	           myFiles = directory.list();
	           
	           for (int i=0; i<myFiles.length; i++) {
	        	   File myFile = new File(directory, myFiles[i]); 
	        	   if (!(myFile.getName().equals(RequireFile)))
	        	   {
	               System.out.println("deleting file"+ myFile );
	               myFile.delete();
	        	   }
	           }
	    }
	       
}
	

public static void  clickPoint(String controlName)//New methid added by charmee :- 29Sept for element not clickable issue
{
   WebElement element= driver.findElement(By.xpath(or_getproperty(controlName)));
   JavascriptExecutor executor = (JavascriptExecutor) driver;
   executor.executeScript("arguments[0].click();", element);
}

public static void  scrollToElement(String controlName) throws InterruptedException
{
   WebElement element= driver.findElement(By.xpath(or_getproperty(controlName)));
   JavascriptExecutor executor = (JavascriptExecutor) driver; 
    executor.executeScript("arguments[0].scrollIntoView(true);", element);
    Thread.sleep(700);
}

}


	
	
