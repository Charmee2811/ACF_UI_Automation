/*
 **************************************************************
 Author : ACF Test Automation Team
 Purpose : Base class for verification of all CF test Cases.
 Date : 21/10/2016
 Test Case Name : itemVerification
 **************************************************************
 */

package cf.Base;

import java.io.IOException;

import jxl.read.biff.BiffException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.relevantcodes.extentreports.LogStatus;

import cf_Util.TestLog;
import cf_Util.xls_reader;


public class itemVerification extends itemAction 
{
	public static String faqWindow;
	public  String mainWindow;
	
	
	/*protected itemVerification() 
	{
		DateFormat dateFormat = new SimpleDateFormat(config_getproperty("dateFormat"));
		Calendar cal = Calendar.getInstance();
		systemTime = ""+ dateFormat.format(cal.getTime());
	}*/
	   
	
	   
	public void verifyLogin() throws InterruptedException, IOException
	  {
		  String v_db;
		  Thread.sleep(5000); 
			  
		  if (driver.findElement(By.xpath(or_getproperty("dashboard"))).isDisplayed())
		  {
			 result="true";
			  v_db="Dashboard is present";
		  }
		  else
		  {
			  result="false";
			  v_db="Dashboard is not present";
		  }
			 
		  
		  Assert.assertEquals(v_db, "Dashboard is present");	  
	  }
	
	/*public void verifyNonAdminLogin()
	{
		if (driver.findElement(By.xpath(or_getproperty("nonAdminUsrOrgLbl"))).isDisplayed())
		{
			 result="true";
			  Assert.assertTrue(true,"Organization Title is Present hence NonAdmin Dashboard Opened");
		}
		  else
		  {
			  result="false";
			  Assert.assertTrue(false,"Organization Title is NOt Present hence Admin Dashboard Opened");
		  }
	}*/
	
	//Verification of logedIn user
	@DataProvider(name="creatUser")
	public Object[][] creatUser() throws BiffException 
	{
	   Object[][] arrayObject = xls_reader.getExcelData("C:\\Users\\A645348\\workspace\\CF_UI_Automation\\TestData\\cf_testData.xls","user");
	   return arrayObject;
	}
	
	 @Test(dataProvider = "creatUser", enabled = false)
	public void verifyLogInUser_SettingsLink(String email,String Password)
	{
		String logedInUserActual = email;
		String logedInUserLbl;
		if (driver.findElement(By.xpath(or_getproperty("logedInUsrDropDwn"))).isDisplayed())
		 {
			
			driver.findElement(By.xpath(or_getproperty("logedInUsrDropDwn"))).click();
			driver.findElement(By.xpath(or_getproperty("settings"))).click();
			logedInUserLbl = driver.findElement(By.xpath(or_getproperty("AuthPageLogInUser"))).getText().toString();
					
			TestLog.info("HellO" + logedInUserLbl);
			 if (logedInUserLbl.contains(logedInUserActual))
			 {
				 result = "true";
				 Assert.assertTrue(true,"New user created has only loged IN");
			 }
				
			  else
			  {
				  result = "false";
				  Assert.assertTrue(false,"LogedIn user and Actual aaa User did not match");
			  }
		 }
		else
		{
			 result = "false";
			 Assert.assertTrue(false,"Logout user dropdown not found");
		}
	}
	
	public void verifyFotterText()
	{
		String fotterTextLbl,fotterTextExpected;
		
		 int timeoutInSeconds = 2000;
		  WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(or_getproperty("createOrgLink"))));
		  getObject("createOrgLink");
		  scroll();
		if (driver.findElement(By.xpath(or_getproperty("fotterTextLink"))).isDisplayed())
		{
			fotterTextLbl=driver.findElement(By.xpath(or_getproperty("fotterTextLink"))).getText();
			fotterTextExpected = config_getproperty("fotterText"); 
			
			if (fotterTextLbl.equals(fotterTextExpected))
			{
				 result = "true";
				Assert.assertTrue(true,"Fotter text matched");
			}
			else
			{
				 result = "false";
				 Assert.assertTrue(false,"Fotter text NOT matched");
			}
		}
		else
		{
			 result = "false";
			Assert.assertTrue(false,"fotterText is not Present");
		}
	}
	
	/*public void verifyTwiterLink()
	{
		String title,url;
		title = driver.getTitle();
		url = driver.getCurrentUrl();
		TestLog.info(title);
		 if (title.contains(config_getproperty("twiterPageTitle")))
		 { 
			 result = "true";
		    Assert.assertTrue(true,"Title matched hence twiter page opeend ");
		    
		      if ((url).contains(config_getproperty("twiterPageUrl")))
		      {
		    	  result = "true";
		    	  Assert.assertTrue(true,"Twitter URL  found hence Twitter page opened");
		      }
		     else
		     {
		    	 result = "false";
		    	  Assert.assertTrue(false,"Twitter URL NOT found Twitter page NOT opened");
		     }
		    
		    
		  //  if (driver.findElement(By.xpath(or_getproperty("twiterLogo"))).isDisplayed())
		    //	Assert.assertTrue(true,"Twiter logo found hence twiter page opened");
			//else
				// Assert.assertTrue(false,"Twiter logo NOT found twiter page NOT opened");
		    	
		 }
		 else
		 {
			 result = "false";
			 Assert.assertTrue(false,"Title did not matched hence twiter page was  not opeend");
		 }
	}*/
	//function winLocation(term) {
	   // return window.location.href.contains(term);}
	
	public void verifyLink()
	{
		String curURl = driver.getCurrentUrl();
		TestLog.info("$$$$$$$$$  "+curURl+ "$$$$$$$$$$$");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + curURl);
		 
		  String[] links = new String[] { "twitter", "linkedin", "facebook", "youtube" };
		   
		 // String[] cases = {"someSubString", "anotherSubString", "yetanotherSubString"};

		  int i;
		  for(i = 0; i < 4; i++)
		      if(curURl.contains(links[i])) break;

		  switch(i) {
		      case 0: //twitter
		          //TestLog.info("twitter");
		          result="true";
		          TestLog.info("TwitterLink  is verified");
		          Assert.assertTrue(true,"Link Contains hence TWITTER page opened ");
		          
		      break;
		      case 1: //linkedIn
		          result="true";
		          TestLog.info("linkedInLink  is verified ");
		          Assert.assertTrue(true,"URL Contains link hence LinkedIn page opened ");
		      break;
		      case 2: //facebook
		          result="true";
		          TestLog.info("FacebookLink  is verified ");
		          Assert.assertTrue(true,"URL Contains link hence FACEBOOK page opened ");
		          break;
		      
		      case 3: //youTube
		    	  //TestLog.info("youTube");
		          result="true";
		          TestLog.info("YoutubeLink  is verified ");
		          Assert.assertTrue(true,"URL Contains link hence YOUTUBE page opened ");
		          break;
		         
		      default:
		    	  //TestLog.info("Not  a valid Linke");
		    	  result="false";
		    	  TestLog.info("no any valid link is present");
		          Assert.assertTrue(false,"URL  DID not any link Contain any of the links hence twiter page opened ");
		          break;
		  }
	}
	
	
	public void verifyCFProductsDetails()
	{
		if (driver.findElement(By.xpath(or_getproperty("gettingStartedTable"))).isDisplayed())
		{
			TestLog.info("Table Visible");
			String mac = driver.findElement(By.xpath(or_getproperty("gettingStartedTableMAC"))).getText();
			String win = driver.findElement(By.xpath(or_getproperty("gettingStartedTableWin"))).getText();
		    
			if (mac.contains("Mac OS"))
			{
				 result = "true";
				Assert.assertTrue(true,"Mac OS CF Product  Present");
			}
			else
			{
				 result = "false";
				Assert.assertTrue(false,"Mac OS CF Product  NOT Present");
			}
			
			if (win.contains("Windows"))
			{
				 result = "true";
				Assert.assertTrue(true,"Windows OS CF Product Present");
			}
			else
			{
				 result = "false";
				Assert.assertTrue(false,"Windows OS CF Product  NOT Present");
			}
			
			TestLog.info("Windows and Mac OS CF Products Present");
		}
		else
		{
			 result = "false";
			 Assert.assertTrue(false,"Cf Products Table not Visible");
		}
		
	}
	
	public void verifySrNo()
	{
		int col_count = driver.findElements(By.xpath(or_getproperty ("cfBuildPacksTableClmn"))).size();
		TestLog.info(" Coloumn Count ***" + col_count);
		if (col_count == 4)
		{
			 result = "true";
			 Assert.assertTrue(true,"Coloumn Count is  4");
			 String srno = driver.findElement(By.xpath(or_getproperty("srNo"))).getText();
			 TestLog.info(" Sr NO Text  ***" + srno);
			if (srno.trim().equals("Sr. no."))
			{
				 result = "true";
				 TestLog.info("Sr No colloumn Present");
				Assert.assertTrue(true,"Sr No colloumn Present");
			}
			else
			{
				 result = "false";
				 TestLog.info("Sr No colloumn  NOT Present");
				Assert.assertTrue(false,"Sr No colloumn  NOT Present");
			}
		}
		else
		{
			 result = "false";
			 TestLog.info("Coloumn Count is not 4");
			 Assert.assertTrue(false,"Coloumn Count is not 4");
		}
		
	}
	
	
	public void verifyMessage(String xpath,String expMsg)
	{
		if (driver.findElement(By.xpath(or_getproperty(xpath))).isDisplayed())
		{
		String acftualText = driver.findElement(By.xpath(or_getproperty(xpath))).getText(); 
		TestLog.info("actual text is:"+acftualText);
		TestLog.info("expected text  "+ expMsg);
		System.out.println("actual text is ::"+acftualText);
		System.out.println("expected text  "+ expMsg);
		if ((acftualText).trim().contains(expMsg.trim()))
		 {	 
			  result = "true";
			 TestLog.info("Actual text present as expected");
			  Assert.assertTrue(true,xpath + " Text matched");
			 
		  }
		  else 
		  {
			  result = "false";
			  TestLog.info("Actual text is not present as expected");
			  Assert.assertTrue(false,xpath + " Text  DID NOT matched");	  
		  }
		}
		else
		{
			result = "false";
			TestLog.info("Test is not present");
			Assert.assertTrue(false,xpath + "  Text is not Present");	
			
		}
	}
	
	public static void traverse(String name,String controlName) throws InterruptedException
	{
		getObject(controlName);
		 if(driver.findElement(By.xpath(or_getproperty(controlName))).isDisplayed())
		 {
		   TestLog.info(controlName + "** Is Displayed and will be clicked **" );
		   Thread.sleep(3500);
		   WebElement element= driver.findElement(By.xpath(or_getproperty(controlName)));

		   JavascriptExecutor executor = (JavascriptExecutor) driver;
		   executor.executeScript("arguments[0].click();", element);
		   //driver.findElement(By.xpath(or_getproperty(controlName))).click();
		   Thread.sleep(3500);
		   if (driver.findElements(By.xpath(or_getproperty("paginationControl"))).size() != 0)
		   {	
			WebElement Paging_Element = driver.findElement(By.xpath(or_getproperty("paginationControl"))); 
			List<WebElement> sub_links = Paging_Element.findElements(By.tagName("a"));  
			TestLog.info("Total Paging Elements Are -- > " + sub_links.size()); 
			int temp= sub_links.size();
		    
			for (int i = 4;i<=temp;i++)
		    {
		    	TestLog.info("***** In Loop*******");  
		      if(driver.findElements(By.linkText(name)).size() != 0)
		      {		
		        driver.findElement(By.linkText(name)).click();
		        TestLog.info(name + "** Is Displayed and  clicked **" );   
		        Assert.assertTrue(true, " Link Exist");
		        break;
		      }
		      else
		      {
		    	  TestLog.info( "**Else of Looping if ie no link exist **" );  
		    	driver.findElement(By.xpath(TestBase.or_getproperty("pageIndex1")+ i + TestBase.or_getproperty("pageIndex2"))).click(); 
		      }
		      
		   }	
		      //TestLog.info("No Link Exist" + name);
		      //Assert.assertTrue(false, "Does not Exist");    
		 }
		  else  // if no pagination control
		  {
			  TestLog.info( "**Else of Pagination control **" ); 
			  //Thread.sleep(1500);
			  TestLog.info( "space Name:- " + name );
			  Thread.sleep(2500);
		   if(driver.findElements(By.linkText(name)).size()!=0)
		   {	
			   Thread.sleep(2500);
			   click2(name);
		     //driver.findElement(By.linkText(name)).click();
		     TestLog.info(name + "** Is Displayed and  clicked **" );   
		     Assert.assertTrue(true, " Link Exist");
		     Thread.sleep(2500);
		   }
		   else
		   {
			   result= "false";
		     TestLog.info("No Link Exist" + name);
			 Assert.assertTrue(false, "Does not Exist");
			
		   }
		  }
		 }
	     else//Control If
	     {
		   TestLog.info("No Control Exist" + controlName);
		   Assert.assertTrue(false, "Does not Exist");
	     } 
	 }
	 
		
		public void traverse_Demo(String orgName, String spaceName, String appname) throws InterruptedException
		{
		  String orgList,spaceList,appList;
		  orgList= "listvieworg";
		  spaceList ="listviewspace";
		  appList = "listviewapp";
		  
		     Thread.sleep(3000); 
			traverse(orgName,orgList);
			TestLog.info("inside org");
			  Thread.sleep(3000); 
			traverse(spaceName,spaceList);
			TestLog.info("inside space");
			 Thread.sleep(10000); 
			traverse(appname,appList);
			TestLog.info("inside application");
		}
		
	


	public void traverseApp(String orgName, String spaceName, String appname) throws InterruptedException
	{
		Thread.sleep(2000);
	    
		if (driver.findElement(By.xpath(or_getproperty("listvieworg"))).isDisplayed())
		   driver.findElement(By.xpath(or_getproperty("listvieworg"))).click();
		else
			TestLog.info("Already in List view hence not clicked");
			
	    int rowCount=driver.findElements(By.xpath((or_getproperty("orgcount")))).size();
	    TestLog.info ("Rowcount: "+rowCount);
	    Thread.sleep(3000L);
	   outer:{
		inner:for(int i=1;i<=rowCount;i++)
		{
			String org = driver.findElement(By.xpath(TestBase.or_getproperty("orglist1")+ i + TestBase.or_getproperty("orglist2"))).getText();
			TestLog.info("org is:"+org);
			TestLog.info("**********org is:"+ orgName);
			if(org.equals(orgName)) 
			{   
				TestLog.info("click on org");
				driver.findElement(By.xpath(TestBase.or_getproperty("orglist1")+ i + TestBase.or_getproperty("orglist2"))).click();
				Thread.sleep(3000L);
				
					if(driver.findElements(By.xpath(or_getproperty("listviewspace"))).size() != 0)
					{   
						if(driver.findElement(By.xpath(or_getproperty("listviewspace"))).isDisplayed())
						{
						  Thread.sleep(3000L);
						  WebElement element= driver.findElement(By.xpath(or_getproperty("listviewspace")));

						  JavascriptExecutor executor = (JavascriptExecutor) driver;
						  executor.executeScript("arguments[0].click();", element);
					     // driver.findElement(By.xpath(or_getproperty("listviewspace"))).click();
					    }
					}
					else
					{	
						TestLog.info("No Spaces Exist");
						Assert.assertTrue(false, "No Spaces Exist");
						break inner;
					}
				 Thread.sleep(3000L);
				int rowCountSpace=driver.findElements(By.xpath((or_getproperty("spacecount")))).size();
			    TestLog.info ("Rowcount space: "+rowCountSpace);
			    Thread.sleep(3000L);
			    innerspace:for(int j=1;j<=rowCountSpace;j++)
				{
			    	String space;
			    	if(rowCountSpace == 1){space = driver.findElement(By.xpath(TestBase.or_getproperty("spacelist11"))).getText();}
			    	else{  space =  driver.findElement(By.xpath(TestBase.or_getproperty("spacelist1")+ j + TestBase.or_getproperty("spacelist2"))).getText();}
			    	TestLog.info("space"+ space);
			    	if(space.equals(spaceName)) 
					{
			    		TestLog.info("spaceName"+ spaceName);
			    		if(rowCountSpace == 1){
			    			TestLog.info("11111"+rowCountSpace);
			    			WebElement element= driver.findElement(By.xpath(TestBase.or_getproperty("spacelist11")));

			    			JavascriptExecutor executor = (JavascriptExecutor) driver;
			    			executor.executeScript("arguments[0].click();", element);
			    			//driver.findElement(By.xpath(TestBase.or_getproperty("spacelist11"))).click();
			    		}
			    		else
			    		{
			    			TestLog.info("22222"+rowCountSpace);
			    			TestLog.info("click on space");
			    		    driver.findElement(By.xpath(TestBase.or_getproperty("spacelist1")+ j + TestBase.or_getproperty("spacelist2"))).click();
			    		}
			    		    Thread.sleep(3000L);
			    		    if(driver.findElements(By.xpath(or_getproperty("listviewapp"))).size() != 0)
			    		    { 
			    		    	 if(driver.findElement(By.xpath(or_getproperty("listviewapp"))).isDisplayed())
			    		    	 { 
			    		    		 WebElement element= driver.findElement(By.xpath(or_getproperty("listviewapp")));

									  JavascriptExecutor executor = (JavascriptExecutor) driver;
									  executor.executeScript("arguments[0].click();", element);
			    		    		 //driver.findElement(By.xpath(or_getproperty("listviewapp"))).click();
			    		    	 }
			    		    }
			    		    else{
			    		    	TestLog.info("No Apps");
			    		    	Assert.assertTrue(false, "No apps");
			    		    	break inner;
			    		    	
			    		    	}
			    		    Thread.sleep(3000L);
							int rowCountApp=driver.findElements(By.xpath((or_getproperty("appcount")))).size();
						    TestLog.info ("Rowcount app: "+rowCountApp);
						    Thread.sleep(3000L);
						    innerapp:for(int k=1;k<=rowCountApp;k++)
							{
						    	String app = driver.findElement(By.xpath(TestBase.or_getproperty("applist1")+ k + TestBase.or_getproperty("applist2"))).getText();
						    	TestLog.info(app);
						    	if(app.equalsIgnoreCase(appname)){
						    		TestLog.info("App Found");
						    		WebElement element= driver.findElement(By.xpath(TestBase.or_getproperty("applist1")+ k + TestBase.or_getproperty("applist2")));

									  JavascriptExecutor executor = (JavascriptExecutor) driver;
									  executor.executeScript("arguments[0].click();", element);
						    	//driver.findElement(By.xpath(TestBase.or_getproperty("applist1")+ k + TestBase.or_getproperty("applist2"))).click();
						    	break outer;
						    	}		
							}
						    TestLog.info("********No app found");
						    result =  "false";
					    	Assert.assertTrue(false,"App Not Found");
			    		}		
					}
			    TestLog.info("Space Not found");
			    result =  "false";
		    	Assert.assertTrue(false,"Space Not Found");
		    	
				}			
			}
	    TestLog.info("Org Not found");
	    result =  "false";
    	Assert.assertTrue(false,"Org Not Found");
    	
	    }
	}

	
	public void verifyOrgName(String orgName) throws InterruptedException
	{
		outer:{
		driver.findElement(By.xpath(or_getproperty("listvieworg"))).click();
	    int rowCount=driver.findElements(By.xpath((or_getproperty("orgcount")))).size();
	    TestLog.info ("Rowcount: "+rowCount);
	    Thread.sleep(3000L);
		inner:for(int i=1;i<=rowCount;i++)
		{
		
			String org = driver.findElement(By.xpath(TestBase.or_getproperty("orglist1")+ i + TestBase.or_getproperty("orglist2"))).getText();
			TestLog.info("created Org Name is:"+org);
			TestLog.info("**********org is:"+ orgName);
			if(org.equals(orgName)) 
			{   
				TestLog.info("Orgname name with Blank space in between was created");
				result= "true";
				Assert.assertTrue(true, "Org with blank space created");
				break outer;
			}
		}
	    TestLog.info("org with blank space was not created");
	    Assert.assertTrue(false, "Org with blank space was NOT created");
		}//outer ends here
	    
	 }

	
	public void verifyLoginUser(String email)
	{
		String loginEmail = driver.findElement(By.xpath(TestBase.or_getproperty("logedInUsr"))).getText();
		if (loginEmail.equalsIgnoreCase(email))
		{   
			 result= "true";
			TestLog.info("Login User Email is :- "+loginEmail + " It is similar to the user we created" );
			Assert.assertTrue(true, "Logged In user is same as we created");
		}
		else
		{
			result= "false";
			TestLog.info("Login User Email is :- "+loginEmail + " It is  NOT similar to the user we created" );
			Assert.assertTrue(false, "Logged in user is different");	
		}
	}
	
	 public static void verifyRole(String username, String userRole)
	  {
		 getObject ("assignRoleFlashMsg");
		  String assignUsrRoleFlashMsg =  driver.findElement(By.xpath(or_getproperty("assignRoleFlashMsg"))).getText();
		  TestLog.info("Flash message after role assigned to User is :- " + assignUsrRoleFlashMsg);
			if (assignUsrRoleFlashMsg.contains(userRole))
			{
				result="true";
				TestLog.info(userRole + "Role was successfully assigned  to user"+ username);
				Assert.assertTrue(true,"Role Was Assigned successcully as expected");
			}
			else
			{
				result="false";
				TestLog.info(userRole + "Role was  not successfully assigned  to user"+ username);
				Assert.assertTrue(false,"Role Was not assigned successcully as expected");
				
			}
			
	   }
	 
	 
	 public void verifySettingPage() throws InterruptedException
	  {
		Thread.sleep(1000);
		driver.findElement(By.xpath(or_getproperty("settingLink"))).click();
	    checkSettingLinks("changePasswordLink");
	    TestLog.info("checked  changepassword link ");
	    checkSettingLinks("logoutLink");
	    TestLog.info("checked logout link");
	  }
	  
	public void checkSettingLinks(String xpathname){
		
		if(driver.findElement(By.xpath(or_getproperty(xpathname))).isEnabled())
		{
			result="true";
			Assert.assertTrue(true, xpathname + " is Present");
			TestLog.info(xpathname + " is Present" );
		}
		else
		{
			result="false";
			Assert.assertTrue(true, xpathname + "  is Not Present");
			TestLog.info(xpathname + " is Not Present" );
		}
	}
	
	  
	 public void verifySocialIcons() throws InterruptedException
	  {
		  
		//verify twitter link  
		  Thread.sleep(3000);
		  checkSocialLink("twitterLink");
		  TestLog.info("checked  twitter link");
		  checkSocialLink("linkedInLink");
		  TestLog.info("checked  linkedIn link");
		  checkSocialLink("youtubeLink");
		  TestLog.info("checked youtube link");
	  }
	 
	 public void checkSocialLink(String xpathname)
	 {
			
			if(driver.findElement(By.xpath(or_getproperty(xpathname))).isEnabled())
			{
				result="true";
				Assert.assertTrue(true, xpathname + " is Present");
				TestLog.info(xpathname + " is Present" );
			}
			else
			{
				result="false";
				TestLog.info(xpathname + " is Not Present" );
				Assert.assertTrue(false, xpathname + "  is Not Present");
				
			}
	} 
	 
	 public void createRoute(String orgName, String spaceName, String appname, String hostName ,String domainNamePreprod, String mapRouteNamePreprod, String domainNameDev, String mapRouteNameDev  ) throws InterruptedException, IOException
	 {
		 traverse_Demo(orgName, spaceName, appname);
		 Thread.sleep(6000L);
		 clickPoint("createRoutelink");
		 Thread.sleep(4000);
		 driver.findElement(By.xpath(or_getproperty("createRouteHostName"))).sendKeys(hostName);
		 Thread.sleep(5000);
		 Select dropdown1 = new Select(driver.findElement(By.xpath(or_getproperty("createRouteDomain"))));
		 if((config_getproperty("cfURL").contains("preprod")))
		 {
			 dropdown1.selectByVisibleText(domainNamePreprod);
		 }
		 else{ dropdown1.selectByVisibleText(domainNameDev);}
		 Thread.sleep(5000);
		 driver.findElement(By.xpath(or_getproperty("createRouteButton"))).click();
		 Thread.sleep(4000L);
		 String createRouteText =  driver.findElement(By.xpath(or_getproperty("createRouteFlashMsg"))).getText();
		 TestLog.info("**" + createRouteText);
		 if(createRouteText.contains("succesfully created") )
		 {
			 TestLog.info("Route has been succesfully created. ");
			 Assert.assertTrue(createRouteText.contains("succesfully created"));	
		 }
		 else
		 {
			 result="false";test.log(LogStatus.FAIL, "Route is not created");
			 Assert.assertTrue(false,"Route is not created");
		 }
	 }	
	 public void VerifyPaginationOrg() throws InterruptedException
        {
			String description ="Pagination Of an Organization";
			Thread.sleep(2000);
			int rowCount=driver.findElements(By.xpath((or_getproperty("firstOrg")))).size();
			TestLog.info("No.Of Organizations = " + rowCount);
               
			if(driver.findElement(By.xpath(or_getproperty("pagination"))).isDisplayed())
			{
				if (rowCount == 48)
				{
					trueCondition(description);
				}            
				else
				{
					falseCondition(description);   
				}
			}
           else
           {
        	   if (rowCount <= 48)
        	   {
        		   trueCondition(description);
        	   }            
        	   else
        	   {
        		   falseCondition(description);   
        	   	}
           }
                       
 }
		
		
		
		public void VerifyPagination( String firstXpath, String objName, String paginationName ) throws InterruptedException
		  {
			  String PassDescription ="Pagination Of " + objName + " is Present";
			  String failDesciption ="Pagination Of " + objName + " is NOT Present";
			  TestLog.info("First space Xpath "+ firstXpath);
			  	int rowCount=driver.findElements(By.xpath((or_getproperty(firstXpath)))).size();
				TestLog.info(" No.Of " + objName + " = " + rowCount);
			  
			  if(driver.findElements(By.xpath(or_getproperty(paginationName))).size() == 0)
			  {
	              if (rowCount == 48)
	              {
	            	  TestLog.info("In the true section of pagination having rowcount: "+ rowCount);
	            	  trueCondition(PassDescription);
	             	  scrollTillPagination();
	              }            
	              else
	              {
	            	  TestLog.info("In the false section of pagination having rowcount: "+ rowCount);
	            	  falseCondition(failDesciption);   
	              }
		     }
		     else
		     {
		     	 if (rowCount <= 48)
		          {
		     		 TestLog.info("In the true section of pagination having rowcount: "+ rowCount);
		     		 trueCondition(failDesciption);
		          }            
		          else
		          {
		        	 TestLog.info("In the false section of pagination having rowcount: "+ rowCount);
		         	 falseCondition(failDesciption);   
		          }
		     }
				  
		  } 
		
  		public void invalidRouteNameLength(String Org,String Space,String app,String HostName, String domainNameDev,String domainNamePreprod) throws Exception
  		{
  			traverse_Demo(Org, Space, app);
  			Thread.sleep(10000); 
  			clickPoint("createRoutelink");//modified by harshali 
  	  	  	Thread.sleep(5000L); 
  		    driver.findElement(By.xpath(or_getproperty("createRouteHostName"))).sendKeys(HostName);
  		    Thread.sleep(3000);
  		    String NewHostName=  driver.findElement(By.xpath(or_getproperty("createRouteHostName"))).getAttribute("value");
  		    driver.findElement(By.xpath(or_getproperty("createRouteDomain"))).click();
  		    Select dropdown1 = new Select(driver.findElement(By.xpath(or_getproperty("createRouteDomain"))));
  		    
		    if((config_getproperty("cfURL").contains("preprod")))
		    	
	
		    	dropdown1.selectByIndex(0);
		    else
		    	dropdown1.selectByIndex(0);
		    Thread.sleep(5000);
		   
			driver.findElement(By.xpath(or_getproperty("createRouteButton"))).click();
			Thread.sleep(4000L);
			 
			verifyLength(NewHostName, "Route");	   
  	  	
  		}
  		
  		
  		public void invalidEnvNameLength (String Org,String Space,String app,String VariableName,String Vlaue) throws Exception
		{
			traverse_Demo(Org, Space, app);
			Thread.sleep(1000);
			clickPoint("createEnvVar");//modified by harshali 10th oct
  	  	    Thread.sleep(5000L); 
  		    driver.findElement(By.xpath(or_getproperty("variableNameTxt"))).sendKeys(VariableName);
  		    Thread.sleep(3000);
  		    driver.findElement(By.xpath(or_getproperty("ValueTxt"))).sendKeys(Vlaue);
  		    Thread.sleep(5000);
			driver.findElement(By.xpath(or_getproperty("setVarBtn"))).click();
			TestLog.info("Set Environment variable button clocked ");
			Thread.sleep(4000L);	
  		    
		}




public void verifyLength(String NewName, String Name)
  		{
  			 if (NewName.length()==40)
		       {
	    		  result="true";
	    	      Assert.assertTrue(true, Name + "with" + NewName +"name with valid length was  created");
	    	      TestLog.info("New valid " + NewName +" was  created ");
	    	      
	           }
	  	    else
	  	    {
	  	    	    result="false";
	  	    		TestLog.info("New invalid "+ NewName +"  was created "); 
	  	    		Assert.assertTrue(false, Name + "With" + NewName + "invalid length more than 40 characters was created");
	  	    }
  		}
public void validateMaxNameLen(String validName, String createLinkPath , String objNamepath , String canclebtnPath) throws Exception
{
	if (driver.findElements(By.xpath(or_getproperty(createLinkPath))).size()!=0)
	{
		result= "True";
	  Thread.sleep(3000);
	 driver.findElement(By.xpath(or_getproperty(createLinkPath))).click();
	  Thread.sleep(500);
	  driver.findElement(By.xpath(or_getproperty(objNamepath))).sendKeys(validName);
	  Thread.sleep(200);
	  String newname =driver.findElement(By.xpath(or_getproperty(objNamepath))).getAttribute("value");
	  driver.findElement(By.xpath(or_getproperty(canclebtnPath))).click();
	  TestLog.info("New  " + objNamepath + "name is "+ newname );
	  System.out.println("New  " + objNamepath + "name is "+ newname);
	  verifyOSLength(validName, newname);
	}
	else
	{
			result="false";
			TestLog.info("Create Link not present" );
			Assert.assertTrue(false, " Create Link not present");
		
	}
		
}


public void inValidateMaxNameLen(String inValidName, String createLinkPath , String objNamepath) throws Exception
{
	if (driver.findElements(By.xpath(or_getproperty(createLinkPath))).size()!=0)
	{
		Thread.sleep(3000);
		driver.findElement(By.xpath(or_getproperty(createLinkPath))).click();
		Thread.sleep(500);
		driver.findElement(By.xpath(or_getproperty(objNamepath))).sendKeys(inValidName);
		Thread.sleep(200);
		String newname =driver.findElement(By.xpath(or_getproperty(objNamepath))).getAttribute("value");  
		TestLog.info("New  " + objNamepath + "name is "+ newname );
		System.out.println("New  " + objNamepath + "name is "+ newname);
		verifyOSLength(inValidName, newname);
	}
	else
	{
		result="false";
		TestLog.info("Create Link not present" );
		Assert.assertTrue(false, " Create Link not present");
		
		
	}
		
}

		public void appscalevalidate(String instance,String memory, String disk) throws Exception
		{
			getObject("cmbAppInstance");
			Select dropdown = new Select(driver.findElement(By.xpath(or_getproperty("cmbAppInstance"))));
			  dropdown.selectByVisibleText(instance);
			  Thread.sleep(1500);
			 Select dropdown1 = new Select(driver.findElement(By.xpath(or_getproperty("cmbAppMemory"))));
			  dropdown1.selectByVisibleText(memory);
			  Thread.sleep(1500);
			  Select dropdown2 = new Select(driver.findElement(By.xpath(or_getproperty("cmbAppDisk"))));
			  dropdown2.selectByVisibleText(disk);
			  Thread.sleep(1500);
			  driver.findElement(By.xpath(or_getproperty("btnAppScale"))).click();
			  Thread.sleep(17000);
			  String memoryValue= driver.findElement(By.xpath(or_getproperty("txtAppMemoryTbl"))).getText();
			  //String instanceValue = driver.findElement(By.xpath(or_getproperty( "txtAppInstanceTbl"))).getText();
			  String diskValue= driver.findElement(By.xpath(or_getproperty("txtAppDiskTbl"))).getText();
			  if ((memoryValue.contains(memory))&& (diskValue.contains(disk)))
			  {
				  result= "true";
				  Assert.assertTrue(true,"App scaled at Correct memory value" + memoryValue + "at correct disk value "+ diskValue );
				  TestLog.info("App scaled and memory and disk value was changed as we passed");
				  
			  }
		      else
		      {
		    	 
			      result= "false";
			      TestLog.info("App scaled at InCorrect memory value  " + memoryValue  + " or disk value "+ diskValue);
		    	  Assert.assertTrue(false,"App scaled at InCorrect memory value " + memoryValue  + "or disk value "+ diskValue);
			  }
			  
		    	  
		}
		
public void validateChromecompatitbility(String orgName, String spaceName, String appname, String instance ,String memory, String disk, String marketplace) throws Exception
		{	
			traverse_Demo(orgName, spaceName, appname);
			appscalevalidate(instance,memory,disk);
			driver.findElement(By.xpath(or_getproperty("browseMarketplace"))).click();			
			driver.findElement(By.xpath(or_getproperty("mongodbMP"))).click();
			if(driver.findElement(By.xpath(or_getproperty("mongodbFavicon"))).isDisplayed())
			{
				result= "true";
				String MarketPlace= driver.findElement(By.xpath(or_getproperty("mongodbFavicon"))).getText();
				Assert.assertTrue(true,"marketplace has browsed with correct name as" + MarketPlace);
			}
			else
			{
				result= "false";
				Assert.assertTrue(false,"marketplace has browsed with incorrect name");
			}
			
		}	



public void inValidDomainName (String domainName) throws Exception
  		{
  			Thread.sleep(10000L);
  			clickPoint("createDomain");//modified by harshali 10th oct 
  	  		Thread.sleep(5000L); 
	  		driver.findElement(By.xpath(or_getproperty("domainTxtBox"))).sendKeys(domainName);
	  		Thread.sleep(3000);
	  	    String newDomainNam=driver.findElement(By.xpath(or_getproperty("createDomainBtn"))).getAttribute("value");
	   	  	Thread.sleep(3000);
	   	  	driver.findElement(By.xpath(or_getproperty("createDomainBtn"))).click();
	   	  	TestLog.info("domain is created ");
	   	  	Thread.sleep(3000);
	   	  	verifyLength(newDomainNam, "domain");
	   	  	Thread.sleep(3000);
	   	  	clickPoint("deleteDomainBtn");
	   	  	TestLog.info("domain is deleted for clean up purpose  ");
	   	  		 	  	
  		}	


public void appInstancevalidate(String instance,String memory, String disk) throws InterruptedException
{
	getObject("cmbAppInstance");
	Select dropdown = new Select(driver.findElement(By.xpath(or_getproperty("cmbAppInstance"))));
	  dropdown.selectByVisibleText(instance);
	  Thread.sleep(1500);
	 Select dropdown1 = new Select(driver.findElement(By.xpath(or_getproperty("cmbAppMemory"))));
	  dropdown1.selectByVisibleText(memory);
	  Thread.sleep(1500);
	  Select dropdown2 = new Select(driver.findElement(By.xpath(or_getproperty("cmbAppDisk"))));
	  dropdown2.selectByVisibleText(disk);
	  Thread.sleep(1500);
	  driver.findElement(By.xpath(or_getproperty("btnAppScale"))).click();
	  Thread.sleep(45000L);
	  int rowCount=driver.findElements(By.xpath(".//*[@id='overview']/div/table/tbody/tr")).size();
	  System.out.println("******************new***************************  "+ rowCount);
	  String count= String.valueOf(rowCount);
	  
	  if(count.equals(instance))
	  {
		  result= "true";
		  TestLog.info("App scaled at correct instance value and changed  as we passed "+ count);
		  Assert.assertTrue(true,"App scaled at correct Instance value as  " + count );
	  }
	  else
	  {
		  result= "false";
		  TestLog.info("App scaled at Incorrect instance value and changed  as we passed "+ count);
		  Assert.assertTrue(false,"App scaled at Incorrect Instance value as " + count );
		 
	  }
		
	  
}
public void appvaluevalidate(String value, String name,String BtnXpath) throws Exception
{
	
	String newValue= driver.findElement(By.xpath(or_getproperty(BtnXpath))).getText();
	
	if (newValue.contains(value))
	  {
		  result= "true";
		  Assert.assertTrue(true,"App scaled at Correct " + name + "value" + newValue );
		  TestLog.info("App scaled at Correct " + name + "value" + newValue);
	  }
	else
	{
		result= "false";
		TestLog.info("App scaled at Incorrect "  + name +"  and changed  as we passed");
		Assert.assertTrue(false,"App scaled at InCorrect " + name + "value" + newValue );
		  
	}
}
public void checkMarketplaceFavicon() throws Exception
{
	clickPoint("browseMarketplace");
	Thread.sleep(3000);
	String Marketplace=driver.findElement(By.xpath(or_getproperty("FirstMarketPlace"))).getText();
	System.out.println("\n Marketplace is:: "+ Marketplace);
	driver.findElement(By.xpath(or_getproperty("FirstMarketPlace"))).click();
	
	getObject("FirstFavicon");
	String favicon=driver.findElement(By.xpath(or_getproperty("FirstFavicon"))).getText();
	System.out.println("\n Favicon is :: "+ favicon);
	
	
	if(Marketplace.equalsIgnoreCase(favicon))
	{
			result= "true";
			TestLog.info("Marketplace Has browsed correctly  named as " + Marketplace);
		    Assert.assertTrue(true,"marketplace has browsed with incorrect name as" + Marketplace);
		 
	}
	else
	{
		result= "false";
		TestLog.info("Marketplace Has browsed As an incorrect favicon");
		Assert.assertTrue(false,"marketplace has browsed with incorrect favicon");
	}
	
}


public void  CheckBreadcrumbNavigation(String xpathName, String destinationXpath) throws InterruptedException
{
	String PassDescription =xpathName + " is Present and working fine";
	String failDesciption =xpathName + " is Not Present ";
	driver.findElement(By.xpath(or_getproperty(xpathName))).click();
	TestLog.info(xpathName+"  link is clicked ");
	Thread.sleep(2000);
	if(driver.findElement(By.xpath(or_getproperty(destinationXpath))).isDisplayed())
		trueCondition(PassDescription);
	else
    	falseCondition(failDesciption);	
	
}

public void CheckLanguageChange(String xpathName, String frenchxpath, String destinationXpath) throws InterruptedException
{
	String PassDescription =xpathName + " is Present and working fine";
	String failDesciption =xpathName + " is Not Present ";
	driver.findElement(By.xpath(or_getproperty(xpathName))).click();
	Thread.sleep(500);
	driver.findElement(By.xpath(or_getproperty(frenchxpath))).click();
	Thread.sleep(2000);
	if(driver.findElement(By.xpath(or_getproperty(destinationXpath))).isDisplayed())
			trueCondition(PassDescription);
		else
			falseCondition(failDesciption);		
}

public void CheckEmailLink(String xpathName) throws InterruptedException
{
	String PassDescription =xpathName + " is Present and working fine";
	String failDesciption =xpathName + " is Not Present ";
	Thread.sleep(2000);
	if(driver.findElement(By.xpath(or_getproperty(xpathName))).isDisplayed())
			trueCondition(PassDescription);
		else
			falseCondition(failDesciption);	
		
}

public void verifyDomainCreation(String domainName) throws InterruptedException
{
    	String Passdesc= "Successfully created Domain";
		String Faildesc= "Domain was not created";
	    if ( driver.findElement(By.xpath(or_getproperty("domainTitle"))).isDisplayed())
	    {
		    Thread.sleep(2000);
		    Pattern pattern = Pattern.compile("(\\w)+(\\.\\w+)");
		    Matcher matcher = pattern.matcher(domainName);
		    if( matcher.matches())
		    {
		    	TestLog.info("Input String matches regex - "+ matcher.matches());

			    driver.findElement(By.xpath(or_getproperty("createDomain"))).click();
			    Thread.sleep(5000L); 
			    driver.findElement(By.xpath(or_getproperty("domainTxtBox"))).sendKeys(domainName);
				Thread.sleep(3000);
			    driver.findElement(By.xpath(or_getproperty("createDomainBtn"))).click();
			    System.out.println("after created domain waiting for flash msg");
			  	getObject("createDomainFlashMsg");
				String createDomainFlashMsg = driver.findElement(By.xpath(or_getproperty("createDomainFlashMsg"))).getText();
				TestLog.info("Name of domain is "+ domainName);
		    	TestLog.info(" Flash Message  is :- " + domainName + createDomainFlashMsg);
		    	if (createDomainFlashMsg.contains("The domain is created with success"))
					trueCondition(Passdesc);
				 else
					 falseCondition(Faildesc);
					 Thread.sleep(3000);
				
			   }
		       else
			    {
			    	 result="false";
			    	 TestLog.info("Domain Name is Invalid");
			    	 Assert.assertTrue(false,"Domain Name is Invalid");
			    }
		   }
		  
		else
		{
			   result="false";
	   		   Assert.assertTrue(false,"Domain is not present");
		}
    }

	
public String verifyUserLogin( String userName, String password, String objName) throws InterruptedException 
{
    
        Thread.sleep(5000);
        if (config_getproperty("cfTitle").equals(driver.getTitle())) {
            if (userName.isEmpty())
              driver.findElement(By.xpath(or_getproperty("loginName"))).sendKeys();
            else
              driver.findElement(By.xpath(or_getproperty("loginName"))).sendKeys(config_getproperty(userName));
            
            if (password.isEmpty())
              driver.findElement(By.xpath(or_getproperty("loginPassword"))).sendKeys();
            else
                driver.findElement(By.xpath(or_getproperty("loginPassword"))).sendKeys(config_getproperty(password));
            if(driver.findElement(By.xpath(or_getproperty("loginBtn"))).isEnabled())
            {
                if(userName.isEmpty() | password.isEmpty())
                {
                    driver.findElement(By.xpath(or_getproperty("loginBtn"))).click();
                    Thread.sleep(5000);
                      result="false";
                    TestLog.info(objName+ "  is blank still user able to login --> Test should fail");
                    Assert.assertTrue(false,objName + " blank still user able to login");
                }
                else
                {
                    driver.findElement(By.xpath(or_getproperty("loginBtn"))).click();
                    Thread.sleep(5000);
                    getObject("invalidcreds");
                    String invalidcreds = driver.findElement(By.xpath(or_getproperty("invalidcreds"))).getText();
                    if (invalidcreds.contains("Invalid credentials. Please try again!"))
                    {
                        Thread.sleep(5000);
                          result= "true";
                          TestLog.info("User not able to Login because of " + objName);
                        Assert.assertTrue(true,"User not able to Login because of " + objName); 
                    }
                      else
                      {
                          Thread.sleep(5000);
                          result= "false";
                          TestLog.info(objName+ " still user able to login --> Test should fail");
                        Assert.assertTrue(false,objName+ " still user able to login --> Test should fail");

                      }
                }
            
            }
            else
            {
                result= "true";
                TestLog.info("User not able to Login as " + objName+" is Blank");
                   TestLog.info("Login Fail");
                 Assert.assertTrue(true,"User not able to Login as " + objName+" is Blank"); 
            }
                    
        }
        
        return result;
        
    }

public void appStateVerify() throws InterruptedException
{
	scrollToElement("appStateInstances");
	  getObject("appstate");
	  
	  String appState=driver.findElement(By.xpath((or_getproperty("appstate")))).getText();
	  TestLog.info("appState*** :- "+appState);
		  Thread.sleep(1500);
	  if (appState.contains("running") )
	  //(!(driver.findElement(By.xpath((or_getproperty("appStartButton")))).isEnabled()) &  )
	  {
		TestLog.info(" App is running");
		result="true";
		Assert.assertTrue(appState.contains("running"));	
	  }
	 else
	  {
		result="false";
		Assert.assertTrue(false,"App is not running");	
	  }
}
	

public String verifyFaqAndUserGuideLinks(String link1, String link2, String link3, String link4, String link5) throws InterruptedException
{   
	mainWindow=driver.getWindowHandle();
//	System.out.println("Current Window="+mainWindow);
	driver.findElement(By.xpath(or_getproperty(link1))).click();
// get new window handles
    java.util.Set<String> handles = driver.getWindowHandles();
//  System.out.println("1. Windows="+handles);
    for (String handle1 : driver.getWindowHandles())
    {
//         System.out.println("1. List Of windows="+handle1);
           driver.switchTo().window(handle1);
    }

	  if (config_getproperty(link2).equals(driver.getTitle())) 
	  {
		   driver.findElement(By.xpath(or_getproperty(link3))).isDisplayed();
		   driver.findElement(By.xpath(or_getproperty(link4))).click();
		   result= "true";
		   TestLog.info(link5 +" Opened Successfully");
		   Assert.assertTrue(true,link5 +" Opened Successfully"); 
	  }
	  else
	  {
		   result= "false";
		   TestLog.info(link5 +" is Not Available");
		   Assert.assertTrue(false,link5 +" is Not Available");  
	  }
			
	return result;
	
}

public String CreateUserProvidedServiceInstance( String Instancename, String Hostname, String Port, String DBname, String Username, String Password) throws InterruptedException
{
	//driver.findElement(By.xpath((or_getproperty("CreateUserProvideServiceInstance")))).click();
	driver.findElement(By.linkText("Create User Provided Service Instance")).click();
	Thread.sleep(500);
	
	if (Instancename != "AutoScaler")
	  driver.findElement(By.xpath(or_getproperty("InstancenameTextbox"))).sendKeys(Instancename);
	else
	  {
		 result= "false";
		 TestLog.info(Instancename +" Instancename is AutoScaler so failed");
		 Assert.assertTrue(false,Instancename +" Instancename is AutoScaler so failed");  
	  }
	driver.findElement(By.xpath(or_getproperty("HostnameTextbox"))).sendKeys(Hostname);
	
	Pattern pattern = Pattern.compile("^[1-9]\\d*$");
	Matcher matcher = pattern.matcher(Port);
	pattern = Pattern.compile(Port);
	boolean isPortNumber = matcher.matches();
	System.out.println("Input String matches regex - "+ matcher.matches());
	
	  if (isPortNumber)
		{
		  if (Integer.valueOf(Port) >= 0  && Integer.valueOf(Port) <= 65535)
		  {
			System.out.println(Port +" Prt number in the proper range");
			TestLog.info(Port +" Prt number in the proper range");
			 driver.findElement(By.xpath(or_getproperty("PortTextbox"))).sendKeys(Port);
	      }
			
		  else
		   {
			  result= "false";
			  TestLog.info(Port +" Port is number but not in defined range of 0 to 65535");
			  Assert.assertTrue(false,Port +"Port is number but not in defined range of 0 to 65535");  
		   }
		 }	
	  else
	   {
		  result= "false";
		  TestLog.info(Port +" Port is not a  number");
		  Assert.assertTrue(false,Port +" Port is not a  number");  
	   }
	  
	driver.findElement(By.xpath(or_getproperty("DBnameTextbox"))).sendKeys(DBname);
	driver.findElement(By.xpath(or_getproperty("UsernameTextbox"))).sendKeys(Username);
	driver.findElement(By.xpath(or_getproperty("PasswordTextbox"))).sendKeys(Password);
	
	driver.findElement(By.xpath((or_getproperty("createUserProvidedServiceInstanceBtn")))).click();
	  TestLog.info("Clicked btn to create service instance");
	getObject("createCupsFlshMsg");
	String createUserProvidedServiceInstanceFlashMsg = driver.findElement(By.xpath(or_getproperty("createCupsFlshMsg"))).getText();
	  TestLog.info("In create cups mssage");
		  
	if (createUserProvidedServiceInstanceFlashMsg.contains("The creation of service instance \""+ Instancename + "\" is in progress"))
	{
	   result= "true";
	   TestLog.info(Instancename +" service instance created Successfully");
	   Assert.assertTrue(true,Instancename +" service instance created Successfully"); 
	}
	
	/*else if (createUserProvidedServiceInstanceFlashMsg.contains("Service instance already existed:"+Instancename))
	{
	   result= "false";
	   TestLog.info(Instancename +" service instance already present");
	   Assert.assertTrue(false,Instancename +"service instance already");   //Future scope to navigate to instance
	}*/
	else
	{
		   result= "false";
		   TestLog.info(Instancename +" service instance creation Failed");
		   Assert.assertTrue(false,Instancename +"service instance creation Failed");  
		}
  return result;
	
}

public String verifyUpdateOfUserProvidedServiceInstance( String Instancename, String Port, String DBname) throws InterruptedException
{
	
	driver.findElement(By.xpath((or_getproperty("updateCupsLink")))).click();
	Thread.sleep(500);
	if (Instancename != "AutoScaler")
		  driver.findElement(By.xpath(or_getproperty("InstancenameTextbox"))).sendKeys(Instancename);
		else
		  {
			 result= "false";
			 TestLog.info(Instancename +" Instancename is AutoScaler so failed");
			 Assert.assertTrue(false,Instancename +" Instancename is AutoScaler so failed");  
		  }
	
	Pattern pattern = Pattern.compile("^[1-9]\\d*$");
	Matcher matcher = pattern.matcher(Port);
	pattern = Pattern.compile(Port);
	boolean isPortNumber = matcher.matches();
	System.out.println("Input String matches regex - "+ matcher.matches());
	
	  if (isPortNumber)
		{
		  if (Integer.valueOf(Port) >= 0  && Integer.valueOf(Port) <= 65535)
		  {
			TestLog.info(Port +" Port is a  number and in defined range");
			 driver.findElement(By.xpath(or_getproperty("PortTextbox"))).sendKeys(Port);
	      }
			
		  else
		   {
			  result= "false";
			  TestLog.info(Port +" Port is number but not in defined range of 0 to 65535");
			  Assert.assertTrue(false,Port +"Port is number but not in defined range of 0 to 65535");  
		   }
		 }	
	  else
	   {
		  result= "false";
		  TestLog.info(Port +" Port is not a  number");
		  Assert.assertTrue(false,Port +" Port is not a  number");  
	   }
			
	driver.findElement(By.xpath(or_getproperty("DBnameTextbox"))).sendKeys(DBname);
	 Thread.sleep(5000);//added wait so that create instance message is no longer visible
	driver.findElement(By.xpath((or_getproperty("updateCupsBtn")))).click();
	
	getObject("updateCupsFlshMsg");
	String UpdateCupsFlashMsg = driver.findElement(By.xpath(or_getproperty("updateCupsFlshMsg"))).getText();
	  TestLog.info(UpdateCupsFlashMsg+ "*********");
	if (UpdateCupsFlashMsg.contains("Successfully updated the user provided service instance"))
	{
	   result= "true";
	   TestLog.info(Instancename +" service instance Updated Successfully");
	   Assert.assertTrue(true,Instancename +" service instance Updated Successfully"); 
	}
	else
	{
	   result= "false";
	   TestLog.info(Instancename +" service instance Updation Failed");
	   Assert.assertTrue(false,Instancename +" service instance Updation Failed");  
	}
	
  return result;
}


public void verifycreateQuotaPlanForOrg (String QuotaName, String TotalServices, String TotalRoutes, String MemoryLimit, String InstanceMemoryLimit) throws InterruptedException
{
	driver.findElement(By.xpath((or_getproperty("manageOrgQuotalink")))).click();
	TestLog.info("Clicked manageOrgQuotalink");
	getObject("createOrgQuotaLink");
	driver.findElement(By.xpath((or_getproperty("createOrgQuotaLink")))).click();
	TestLog.info("Clicked createOrgQuotaLink ");
	//getObject("createOrgQuotaPage");
	//Thread.sleep(1000);
	//getObject("QuotaNameLable");
	TestLog.info("Enter details ");
	
	Thread.sleep(5000);
	//if( driver.findElement(By.xpath(or_getproperty("createManageOrganizationQuotaPage"))).isDisplayed() )
	//{
	
	int abc = driver.findElements(By.cssSelector("#total_routes")).size(); 
	TestLog.info("element value =  " +abc);
	System.out.println("element value =  " +abc);
	
	
	
	driver.findElement(By.xpath(or_getproperty(("QuotaNameTextbox")))).sendKeys(QuotaName);
	driver.findElement(By.xpath("total_services")).sendKeys(TotalServices); 
	driver.findElement(By.xpath("instance_name")).sendKeys(QuotaName); 
	
		//driver.findElement(By.xpath(or_getproperty("TotalRoutesTextbox"))).sendKeys(TotalServices);
		driver.findElement(By.xpath(or_getproperty("TotalRoutesTextbox"))).sendKeys(TotalRoutes);
		driver.findElement(By.xpath(or_getproperty("MemoryLimitTextbox"))).sendKeys(MemoryLimit);
		driver.findElement(By.xpath(or_getproperty("InstanceMemoryLimitTextbox"))).sendKeys(InstanceMemoryLimit);
		
		driver.findElement(By.xpath((or_getproperty("createOrgQuotaBtn")))).click();
		  TestLog.info("Clicked btn to create service instance");
		
	//}
	//else
	//{
		TestLog.info("Failed to eneter details");
		
	//}
	
	

}

}
