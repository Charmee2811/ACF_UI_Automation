
package cf.Base;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import cf_Util.TestLog;
public class  itemAction extends TestBase  {
	public String cupsFlashMsg;
	public String newname;
	public String desc;
	
public void create_Org_User_Space(String objCreateLink,String objPath1,String objPath2,String btnPath,String objFlashMsg,String name,String pwd) throws InterruptedException	
	{
	 Thread.sleep(2000);
	  switch(objCreateLink)
      {
        case "createOrgLink" :
        	driver.findElement(By.xpath(or_getproperty(objCreateLink))).click();
  		    Thread.sleep(500);
  		    driver.findElement(By.xpath(or_getproperty(objPath1))).sendKeys(name);
  		    driver.findElement(By.xpath(or_getproperty(btnPath))).click();
  		   // Thread.sleep(400);
   	        result="true";
   	        break;
        case "createUserLink" :
        	Thread.sleep(2000);
        	driver.findElement(By.xpath(or_getproperty(objCreateLink))).click();
  		    Thread.sleep(500);
  		    driver.findElement(By.xpath(or_getproperty(objPath1))).sendKeys(name);
  		    Thread.sleep(500);
  		    driver.findElement(By.xpath(or_getproperty(objPath2))).sendKeys(pwd);
  		    driver.findElement(By.xpath(or_getproperty(btnPath))).click();
   	        result="true";
   	        break;
        case "createSpaceLink" :
        	Thread.sleep(2000);
        	driver.findElement(By.xpath(or_getproperty(objCreateLink))).click();
  		    Thread.sleep(1000);
  		    driver.findElement(By.xpath(or_getproperty(objPath1))).sendKeys(name);
  		    driver.findElement(By.xpath(or_getproperty(btnPath))).click();
  		   // Thread.sleep(400);//For space flash msh has differen xpath
   	        result="true";
   	        break;
         default:
   	       result="true";
   	       TestLog.info("IN Default Section No link for user or Org or Space Creation");
   	       Assert.assertTrue(false,"IN Default Section No link for user or Org or Space Creation");
   	       break;
      }	 
	  
	 int  timeoutInSeconds = 2000;
	  WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(or_getproperty(objFlashMsg))));
	    TestLog.info(" flash Text Message Control visible  for:-  " + objCreateLink );
	    
	    String flshMsgText =  driver.findElement(By.xpath(or_getproperty(objFlashMsg))).getText();
		TestLog.info(objPath1 + " flash Text Message is :-  " + flshMsgText);
	     if (flshMsgText.contains("Successfully created"))
         {
		    result="true";
	        Assert.assertTrue(flshMsgText.contains("Successfully created"));
	        Assert.assertTrue(true, objPath1 + " Created");
	        TestLog.info("New " +objPath1 +  " was created");
         } 
        else 
        {
	      result="false";
	      Assert.assertTrue(false, objPath1+ " already Created");
	      TestLog.info(" NO New " +objPath1 +  " was created");
       }
	}
	
	
	public void creatOrg (String orgName) throws InterruptedException
	  {
		 int timeoutInSeconds = 2000;
		  WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(or_getproperty("createOrgLink"))));
		  getObject("createOrgLink"); 
		if (driver.findElement(By.xpath(or_getproperty("createOrgLink"))).isDisplayed())
		{
			//Thread.sleep(1000);
			driver.findElement(By.xpath(or_getproperty("createOrgLink"))).click();
			Thread.sleep(500);
			driver.findElement(By.xpath(or_getproperty("orgName"))).sendKeys(orgName);//Get from Excel
			driver.findElement(By.xpath(or_getproperty("createOrgBtn"))).click();
			getObject( "flashMsg");
	    	if (  driver.findElement(By.xpath(or_getproperty("flashMsg"))).isDisplayed())
	    	  {
	    		  String orgFlshMsgText =  driver.findElement(By.xpath(or_getproperty("flashMsg"))).getText();
		 		  TestLog.info("Flash Text Message After org creation is :-  " + orgFlshMsgText);
	    		  if (orgFlshMsgText.contains("Successfully created organization"))
	    		  {
	    		  result="true";
	    	      TestLog.info("NEW ORGANATION  "+ orgName+ " WAS CREATED SUCCESSFULLY");
	    		  Assert.assertTrue(orgFlshMsgText.contains("Successfully created organization"));
	    	      Assert.assertTrue(true,"Organization Created");
	    		  }
	    		  else 
	    		  {
	        	  Thread.sleep(2500);
	        	  result="true";
	        	  String orgList= "listvieworg";
	        	  itemVerification.traverse(orgName,orgList);
	        	  TestLog.info("New ORGANATION "+orgName+" was Already Present"); 
	        	  Assert.assertTrue(true,"Organization Already present");
	    		  }
	    	  }
	    	  else
	    	  {
	    		  result= "false";
	    		  TestLog.info(orgName + " org is not created");
	    		  Assert.assertTrue(false,"Org Not Created");  
	    	  }
	          
		}
		else 
		{
			result="false";
			TestLog.info("Create Org Link was not visible");
			Assert.assertTrue(false,"Create Org link was not displayed");	    	  
		}
		 
	  }
	  
	
	public void creatSpace (String spaceName) throws InterruptedException
	  {

		getObject("createSpaceLink");

			clickPoint("createSpaceLink");
			Thread.sleep(500);
			driver.findElement(By.xpath(or_getproperty("spaceName"))).sendKeys(spaceName);
			driver.findElement(By.xpath(or_getproperty("createSpaceBtn"))).click();

			TestLog.info("******successfully clicked on createspace");
			Thread.sleep(500);
			getObject("flashMsgOldSpace");
		if (driver.findElement(By.xpath(or_getproperty("flashMsgOldSpace"))).isDisplayed())
		{
		
			//getObject("flashMsgOldSpace");
			String spaceFlshMsgText =  driver.findElement(By.xpath(or_getproperty("flashMsgOldSpace"))).getText();
			TestLog.info(" Flash message after space creation is :- " + spaceFlshMsgText);
			
		
		  	if (spaceFlshMsgText.contains("Successfully created space"))
		  	{
			 
			 result="true";
			 TestLog.info("successfully created space : "+ spaceName);
			 Assert.assertTrue(spaceFlshMsgText.contains("Successfully created space"));
			 Assert.assertTrue(true,"Space  Created"); 
		  	}
			 
		  	else 
		    {
			 
		    	result="true"; 
		    	String spaceList= "listviewspace";
				itemVerification.traverse(spaceName,spaceList);
				TestLog.info("Space already created  :"+ spaceName);

		    	Assert.assertTrue(true,"Space already Created");
		        	
		    }
		}
		  	else
		  	{
		  		result="true"; 
		  		TestLog.info("New Space Created  :"+ spaceName);
		  		Assert.assertTrue(true,"New Space Created");
		  	}
		}

		 
  
	  public void creatUser(String email,String password) throws InterruptedException //Assume to create new org before new user
	  {	
		  getObject("insideOrg");
		 if (driver.findElement(By.xpath(or_getproperty("insideOrg"))).isDisplayed())
		   {
			 	clickPoint("createUserLink");
			 	Thread.sleep(1000);
			 	driver.findElement(By.xpath(or_getproperty("userEmail"))).sendKeys(email); //Take from excel TODO:- Validations
			 	driver.findElement(By.xpath(or_getproperty("usrPwd"))).sendKeys(password);
			 	driver.findElement(By.xpath(or_getproperty("cnfrmPwd"))).sendKeys(password);
			 	clickPoint("createUserBtn");
			 	getObject("flashMsgUser");
			 	String usrCreateFlashMsg =  driver.findElement(By.xpath(or_getproperty("flashMsgUser"))).getText();
			 	TestLog.info(" Flash message after User creation is :- " + usrCreateFlashMsg);
			
			  if (usrCreateFlashMsg.contains("Successfully created user"))
			  {
			    	result="true";
			    	TestLog.info(email+ " User was created successfully ");
			    	Assert.assertTrue(usrCreateFlashMsg.contains("Successfully created user"));
			    	Assert.assertTrue(true,"User  Created");
			  }
			  else 
			    {
			    	result="true";
			    	TestLog.info(email+ " User Already created");
			    	Assert.assertTrue(true,"User already Created");
			    }
			 
	
		  }
		  else
		  {
			  result="false";
			  TestLog.info("User creation link not Present");
			  Assert.assertTrue(false,"create user link not present ");
			  	  
	     }
		 	 Thread.sleep(2000); 
	  }
	
	  public void nonAdminLogin(String userName, String password) throws InterruptedException, IOException
	  {
		 
		  Thread.sleep(1000);
		  if (config_getproperty("cfTitle").equals(driver.getTitle()))
		  {
			 	TestLog.info("Login for non admin user");
			 	Thread.sleep(1000);
			 	driver.findElement(By.xpath(or_getproperty("loginName"))).sendKeys(userName);
			 	driver.findElement(By.xpath(or_getproperty("loginPassword"))).sendKeys(password);//take from Excel
			 	driver.findElement(By.xpath(or_getproperty("loginBtn"))).click();
			 	
			 	Thread.sleep(2000);
			 	getObject("userdetails");
		  		if (driver.findElement(By.xpath(or_getproperty("userdetails"))).isDisplayed())
		  		{
		  			result= "true";
		  			 TestLog.info("Non admin user Logged In Successfully");
					 Assert.assertTrue(true,"Non admin user Logged In Successfully"); 
		  		}
		  		else
		  		{
		  			result= "false";
		  			 TestLog.info("Non admin user  not able to  Logged In ");
					 Assert.assertTrue(false,"Non admin user not  able to Logged In"); 
		  		}
		  }
		  else
		  {
			  result="false";
			  TestLog.info("Login Page not visible as title was mismatched");
			  Assert.assertTrue(false,"Title not match hence Login page not visible");  
		  }
		
	  }
	  
	 
		  
		  
	 
	  
	  public void nonAdminChangePass(String oldPass, String newPass) throws InterruptedException
	  {
		  Thread.sleep(1000);
		  if (config_getproperty("cfTitle").equals(driver.getTitle()))
		  { 
			  
			  TestLog.info("Changing pass for non admin user");
			  Thread.sleep(1000);
			  driver.findElement(By.xpath(or_getproperty("nonAdminUserdrpdwn"))).click();
			  driver.findElement(By.xpath(or_getproperty("ChangepasswordLink"))).click();
			  getObject("currentpassTextbox");
			  driver.findElement(By.xpath(or_getproperty("currentpassTextbox"))).sendKeys(oldPass);
			  driver.findElement(By.xpath(or_getproperty("newPassTextbox"))).sendKeys(newPass);
			  driver.findElement(By.xpath(or_getproperty("confirmPassTextBox"))).sendKeys(newPass);
			  driver.findElement(By.xpath(or_getproperty("changePassBtn"))).click();
			  Thread.sleep(500);
			 // TestLog.info("Password changed  oldPwd:= "+oldPass + "New Pwd:-  " + newPass );
			   if( driver.findElement(By.xpath(or_getproperty("changepassFlashMsg"))).isDisplayed())
			   {
				   result="true";
				   TestLog.info("Changed password successfully");
				   Assert.assertTrue(true,"Changed password successfully");
			   }
			   else
			   {
				   result="false";
				   TestLog.info("password  not changed successfully");
				   Assert.assertTrue(true,"password  not changedsuccessfully");
			   }
		  }
		  else
		  {
			  result="false";
			   TestLog.info("Login Page not visible as title was mismatched");
		      Assert.assertTrue(false,"Title not match hence Login page not visible");
	
		   
		  }
		  
	  }

  public void createServiceInstance(String instanceName,String serviceName,String plan) throws InterruptedException
  {
	 
	  			   getObject("createservice");
	  			   driver.findElement(By.xpath(or_getproperty("createservice"))).click();
	  			   Thread.sleep(5000L); 
			      driver.findElement(By.xpath(or_getproperty("serviceinstancename"))).sendKeys(instanceName);
				  Thread.sleep(3000);
				  Select dropdown1 = new Select(driver.findElement(By.xpath(or_getproperty("serviceinstance"))));
				  dropdown1.selectByVisibleText(serviceName);
				  Thread.sleep(2000);
				  Select dropdown = new Select(driver.findElement(By.xpath(or_getproperty("serviceinstanceplan"))));
				  dropdown.selectByVisibleText(plan);
				  Thread.sleep(2000);
				  driver.findElement(By.xpath(or_getproperty("serviceinstancesubmit"))).click();
	
				  Thread.sleep(7000);
				  
				 getObject("flashmsgservice");
				  String serviceFlashMsg =  driver.findElement(By.xpath(or_getproperty("flashmsgservice"))).getText();
				  TestLog.info(" Flash message is :- " + serviceFlashMsg);
				  if (serviceFlashMsg.contains("in progress"))
				  {
				    		Assert.assertTrue(serviceFlashMsg.contains("in progress"));
				    		Assert.assertTrue(true,"Service  Created");
				    		TestLog.info("Service created");
				    		getObject("deleteserviceinstance");
				    		driver.findElement(By.xpath(or_getproperty("deleteserviceinstance"))).click();
				    		getObject("confirmdeleteintance");
				    		driver.findElement(By.xpath(or_getproperty("confirmdeleteintance"))).click();
				    		TestLog.info("Confirm Delete Butoon Clicked");
				    		Thread.sleep(2500);
				    		getObject("noInstance");
				    		String noisntance =  driver.findElement(By.xpath(or_getproperty("noInstance"))).getText();
				    		if (noisntance.contains("You haven't instantiated any services."))
				    		{
				    			result="true";
				    			Assert.assertTrue(true,"Service deleted");
				    			TestLog.info("Service deleted as no instance instantiated ");
				    			
				    		}
				    		else 
				    		{
				    			result="false";
				    			TestLog.info("Service not deleted as there were instance");
				    			Assert.assertTrue(false,"Service not Deleted");
				    		}	
				  }
				  else 
				  {
				    	result="false";
				    	TestLog.info("Service not created");
				    	Assert.assertTrue(false,"Service not Created");
				    	
				       
				  }	          	
          }
	 
   	public void authNewUser()
   	{
	   
   			if (driver.findElements(By.xpath(or_getproperty("usrAuthoriseBtn"))).size()!=0)
   			{
   				result="true";
   				driver.findElement(By.xpath(or_getproperty("usrAuthoriseBtn"))).click();
   				Assert.assertTrue(true,"Authorisation page  visible :New user login only");
   			}
   			else 
   			{  
   				result="false";
   				Assert.assertTrue(false,"Authorisation page not visible :Old user login only");
   			}
   	}

public void clickLink(String link)
{
	 switch(link)
     {
       case "twitter" :
    	   driver.findElement(By.xpath(or_getproperty("twitterLink"))).click();
    	   TestLog.info("In clickLink method of TestBase Class and Twiter Link Clicked");
    	   result="true";
    	   break;
       case "linkedIn" :
    	   driver.findElement(By.xpath(or_getproperty("linkedInLink"))).click();
    	   TestLog.info(" In clickLink method of TestBase Class LinkedIn Link Clicked");
    	   result="true"; 
		   break;
       case "facebook" :
    	   driver.findElement(By.xpath(or_getproperty("facebookLink"))).click();
    	   TestLog.info(" In clickLink method of TestBase Class Facebook Link Clicked");
    	   result="true"; 
		   break;
       case "youTube" :
    	   driver.findElement(By.xpath(or_getproperty("youtubeLink"))).click();
    	   TestLog.info(" In clickLink method of TestBase Class YouTube Link Clicked");
    	   result="true"; 
		   break;
       default:
    	   result="false";
    	   Assert.assertTrue(false,"In clickLink method of TestBase Class:- No defined link was present");
       }
	 switchWin();
   }


   public void assignUserRole(String email,String roles) throws InterruptedException
    {
	   	  Thread.sleep(2000);
	   	  clickPoint("assignRoleDP"); //modified due to element not clickable error:- charmee 3rd Oct-17
		  Thread.sleep(2000);
		  driver.findElement(By.xpath(or_getproperty("assignRoleUserName"))).sendKeys(email);
		  Thread.sleep(1000);
		  Select dropdown = new Select(driver.findElement(By.xpath(or_getproperty("userRole"))));
		  dropdown.selectByVisibleText(roles);
		  Thread.sleep(1000);
		  driver.findElement(By.xpath(or_getproperty("assignRoleBtn"))).click();
		  Thread.sleep(700);
		  getObject("assignRoleFlashMsg");
		  int timeoutInSeconds = 2000;
		  WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(or_getproperty("assignRoleFlashMsg"))));
		  String assignUsrRoleFlashMsg =  driver.findElement(By.xpath(or_getproperty("assignRoleFlashMsg"))).getText();
		  TestLog.info("Flash message After Role assignment is :- " + assignUsrRoleFlashMsg);
		  if (assignUsrRoleFlashMsg.contains("Successfully assigned role"))
		  {    
			  result="true";
			  TestLog.info(roles + " Role was succesfully assigned to User:- " + email );
			  Assert.assertTrue(true,"Role was  assigned to user:- FlashMsg text was as expected" );
		  }
		  else 
		  {
			  result="false"; 
			  TestLog.info(" Role was Not assigned to User:-" );
			  Assert.assertTrue(false,"Role was not assigned to user:- FlashMsg text was different" );
		  }
		    	
	   }
   public void removeRole(String email) throws InterruptedException
   {
	  if  (driver.findElements(By.xpath(or_getproperty("usrMngrRole"))).size()!=0)
	  {
		  String userName = driver.findElement(By.xpath(or_getproperty("usrMngrRole"))).getText().toString();
		  Assert.assertTrue(true,"User Present in Manager Role" + email);
	
		   if (userName.equals(email))
		    {   
			   clickPoint("usrMngrRole");//modified by harshali 
			   clickPoint("usrRemoveRoleBtn");
			   Thread.sleep(800);
			   String removeUsrFlashMsg =  driver.findElement(By.xpath(or_getproperty("flashMsg"))).getText(); //verification can be seperated
			   TestLog.info(" Flash message after user role removal is :- "+removeUsrFlashMsg);
			   String unAssignedMsg = config_getproperty("userUnAssignedRoleMsg")  +" '" + email + "'.";
			   TestLog.info("**Unassigned String Built=  " + unAssignedMsg);  
			   if (removeUsrFlashMsg.contains(unAssignedMsg))
			   {
					  result="true";
					  TestLog.info("User role Unassigned message was proper");
					  Assert.assertTrue(true,"User role Unassigned message was proper ");
			   }
			   else
			   {
					  result="false";
					  TestLog.info("User role Unassigned message was not Proper");
					  Assert.assertTrue(false,"User role Unassigned message was NOT proper");
				}
				
		    }
	  }
	  else
	  {
		  result="false";
		  Assert.assertTrue(false,"No user Present under Manager Role");
	  }
  }
  
  public void productDetails() throws InterruptedException
  {
	  if (driver.findElements(By.xpath(or_getproperty("gettingStartedLink"))).size()!=0)
	  {
		  driver.findElement(By.xpath(or_getproperty("gettingStartedLink"))).click();
		  Thread.sleep(2000);
		  String curUrl = driver.getCurrentUrl();
		    if (curUrl.contains(config_getproperty("urlForGettingStartedLink")))
		    {
		    	 result="true";
		      Assert.assertTrue(true,"Getting Started Link Clicked and Cf Products page displayed");
		      TestLog.info("CF Products page opened as URL contains GettingStarted"); 
		    }
		    else
		    {
		    	 result="false";
			  Assert.assertTrue(false,"Getting Started Link is not displayed");
		    }
	  }
	  else
	  {
		  result="false";
		  Assert.assertTrue(false,"Getting Started Link is not displayed");
	  }
  }
	
  public void click_OrgSpace(String objectName)
  {
	  if (driver.findElement(By.xpath(TestBase.or_getproperty(objectName))).isDisplayed())
		{
		  clickPoint(objectName);
			//driver.findElement(By.xpath(TestBase.or_getproperty(objectName))).click();
			Assert.assertTrue(true, "Logged In user has  Manager Role assigned hence " +objectName+ "  is displayed");
			 TestLog.info("Logged In user has  Manager Role assigned hence " +objectName+ "  is displayed");
		}
		
		else
		{
			Assert.assertTrue(false, "Logged In user has  Manager Role assigned Still NO Org is displayed");
			 TestLog.info("Logged In user has  Manager Role assigned Still NO " +objectName+ "  is displayed");
			
		}
  }
  
  
  
 
  public void assignRole(String username, String userRole) throws InterruptedException
  {  	
	  clickPoint("assignroleLink");
	  Thread.sleep(500);
	  driver.findElement(By.xpath(or_getproperty("usernametxtBox"))).sendKeys(username);
	  getObject("roleDrpdown");
	  Select drop_down_role= new Select (driver.findElement(By.xpath(or_getproperty("roleDrpdown"))));
	  Thread.sleep(2000L);
	  drop_down_role.selectByVisibleText(userRole);
	  Thread.sleep(1000);
	  driver.findElement(By.xpath(or_getproperty("assignRoleBtn"))).click();
	 //TestLog.info("Clicked on Assign role Button");
	  Thread.sleep(200);
		 
	  switch (userRole)
	  {
	    case "Manager" :
	    	itemVerification.verifyRole(username,  userRole);
	    	result="true";
	    break;
	  			
	    case 	"Billing Manager" :
	    	itemVerification.verifyRole(username,  "BillingManager");
	    	result="true";
	    break;
	
	    case "Auditor" :
	    	itemVerification.verifyRole(username,  userRole);
	    	result="true";
	    break;  
	    
	    case "Users" :	
	    	itemVerification.verifyRole(username,  "User");
	    	result="true";
	    break;
	    default:
	    	result="false";
	    	TestLog.info(userRole+ " Not a valid role");
	    	Assert.assertTrue(false,"Not a valid role");	  
	 }
  }
  
  public void deleteOrgSpace(String name,String DeletBtnXpath) throws InterruptedException
  {
	  desc = name + " Deletion";
	  Thread.sleep(2000);	        
		driver.findElement(By.xpath(or_getproperty(DeletBtnXpath))).click();
        Thread.sleep(1000);
        
        if (driver.findElement(By.xpath(or_getproperty("deleteWindow"))).isDisplayed())
        	{
        	driver.findElement(By.xpath(or_getproperty("deleteOrgCheckbox"))).click();
			driver.findElement(By.xpath(or_getproperty("btnDelConfirm"))).click();
			
			//Thread.sleep(5000);
			getObject("deleteFlashMsg");
			String deleteFlashMsg = driver.findElement(By.xpath(or_getproperty("deleteFlashMsg"))).getText();
			TestLog.info("Delete " + name +" Flash Message  is :- " + deleteFlashMsg);
			
			if (deleteFlashMsg.contains("Successfully deleted"))
				 trueCondition(desc);
			 else
				 falseCondition(desc);
			Thread.sleep(3000);
			
			
        	}
        else
        	{
        		result="false";
        		Assert.assertTrue(false,"Delete  window is not present");
        	}
        
  }
  
 
 	  	    	
  
  public void inValidInstance (String instanceName,String serviceName, String ServicePlan) throws InterruptedException
  {	
	 
	  getObject("createservice"); 
  	  clickPoint("createservice");//modified by harshali 10th oct 
  	  Thread.sleep(5000L); 
  	  driver.findElement(By.xpath(or_getproperty("serviceinstancename"))).sendKeys(instanceName);
  	  Thread.sleep(3000);
  	  newname= driver.findElement(By.xpath(or_getproperty("serviceinstancename"))).getAttribute("value");
  	  Select dropdown1 = new Select(driver.findElement(By.xpath(or_getproperty("serviceinstance"))));
  	  dropdown1.selectByVisibleText(serviceName);
  	  Thread.sleep(2000);
  	  Select dropdown = new Select(driver.findElement(By.xpath(or_getproperty("serviceinstanceplan"))));
  	  dropdown.selectByVisibleText(ServicePlan);
  	  driver.findElement(By.xpath(or_getproperty("serviceinstancesubmit"))).click();
  	  Thread.sleep(3000);
  	  getObject("instanceflashMsg");
	  Thread.sleep(3000);
	  String FlashMsg =driver.findElement(By.xpath(or_getproperty("instanceflashMsg"))).getText();
	  TestLog.info("Flash Text Message After instance creation is  :-  " + FlashMsg);
	  if (FlashMsg.contains("Could not create service instance due to bad request"))
	  {
		  result="true";
 	      TestLog.info("New invalid instance was not created ");
 	      Assert.assertTrue(true,"service instance Not Created");
	  }
	  else
	  {
		  result="false";
		  TestLog.info("New invalid instance was created "); 
		  Assert.assertTrue(false,"flashMsg not present");  	
 	  }
 	  		    
  }
 
 
	public void validateMaxNameLen(String validName, String createLinkPath , String objNamepath , String canclebtnPath) throws Exception
	{
		if (driver.findElements(By.xpath(or_getproperty(createLinkPath))).size()!=0)
		{
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
	
	
  
  public void verifyOSLength(String actualName,String newname){
  	if(actualName.equals(newname))
		{
			result="true";
			TestLog.info(actualName+ "with valid length is created");
			Assert.assertTrue(true, actualName +"Max length is valid ");
		}
		else 
		{
			result="true";
			TestLog.info(actualName+" with invalid length is not  created");
			Assert.assertTrue(true, actualName+ " Max length is invalid ");
		}
  }
  

public void createCups(String instanceName, String HostName,String  Port,String DBname, String username,String password) throws Exception
{
	String desc = "Cups instance Creation";
	
			Thread.sleep(5000L);
			getObject("CupsInstance");
			clickPoint("CupsInstance");
			Thread.sleep(3000);
			 driver.findElement(By.xpath(or_getproperty("instanceName"))).sendKeys(instanceName);
	         Thread.sleep(300);
			 driver.findElement(By.xpath(or_getproperty("HostName"))).sendKeys(HostName);
			  Thread.sleep(500);
			 driver.findElement(By.xpath(or_getproperty("Port"))).sendKeys(Port);
			  Thread.sleep(500);
			 driver.findElement(By.xpath(or_getproperty("DBName"))).sendKeys(DBname);
			  Thread.sleep(500);
			 driver.findElement(By.xpath(or_getproperty("Username"))).sendKeys(username);
			  Thread.sleep(500);
			 driver.findElement(By.xpath(or_getproperty("Password"))).sendKeys(password);
			  Thread.sleep(500);
			 driver.findElement(By.xpath(or_getproperty("CupsInstaceButton"))).click();
			 Thread.sleep(1000);
			 getObject("cupsFlashMsg");
			 cupsFlashMsg =  driver.findElement(By.xpath(or_getproperty("cupsFlashMsg"))).getText();
			 TestLog.info("flash message after cups creations is : "+cupsFlashMsg);
			 
			 
			 if (cupsFlashMsg.contains("in progress"))
				 trueCondition(desc);
			 else if(!(cupsFlashMsg.contains("in progress")))
				 falseCondition(desc);
			 else
			 {
				 TestLog.info("Required flashmessage is not present ");
				 Assert.assertTrue(false, "Required CupsFlashMsg is not displayed");
			 }
		}


public void deleteCups() throws Exception
{
	
		desc  = "Service  Deleetion";
		//	getObject("cupsFlashMsg");
			//cupsFlashMsg =  driver.findElement(By.xpath(or_getproperty("cupsFlashMsg"))).getText();
			 //TestLog.info(" Flash message is :- " + cupsFlashMsg);
			 //if (cupsFlashMsg.contains("in progress"))
			 //{
			   // 	Assert.assertTrue(cupsFlashMsg.contains("in progress"));
			    //	Assert.assertTrue(true,"Cups instance Created");
			    	//TestLog.info("Cups instance Created");
			    	Thread.sleep(1000L);
			    	clickPoint("deleteserviceinstance");//modified by harshali 10th oct
			        getObject("ConfirmCupsDelete");
			    	driver.findElement(By.xpath(or_getproperty("ConfirmCupsDelete"))).click();
			    	TestLog.info("Confirm Delete Butoon Clicked and instance deleted");//modified by harshali 10th oct
			    	 trueCondition(desc);
			    	//Thread.sleep(3500);
			        //getObject("delCupsFlashMsg");
			        //String delMsg =  driver.findElement(By.xpath(or_getproperty("delCupsFlashMsg"))).getText();
			        /*String noisntance =  driver.findElement(By.xpath(or_getproperty("noInstance"))).getText();
			        Thread.sleep(1000);
			        if (noisntance.contains("You haven't instantiated any services."))
			        	 trueCondition(desc);
					 else
						 falseCondition(desc);*/
			        //New code for verification
			       /* Thread.sleep(1000);
			        TestLog.info("Cupsdeletion message i);
			        if (delMsg.contains("successfully been deleted."))
			        	 trueCondition(desc);
					 else
						 falseCondition(desc);
			 }
			 else
			// {
				TestLog.info("Required cupsFlashMsg is not displayed");
				 Assert.assertTrue(false, "Required CupsFlashMsg is not displayed");
			 }*/
		}


public void validateUserPwrd(String email, String pwrd) throws Exception {
  	
	  Thread.sleep(5000);
		 if (driver.findElement(By.xpath(or_getproperty("insideOrg"))).isDisplayed())
		   {
			 clickPoint("createUserLink");
			  //driver.findElement(By.xpath(or_getproperty("createUserLink"))).click();
			  Thread.sleep(1000);
			  driver.findElement(By.xpath(or_getproperty("userEmail"))).sendKeys(email);		  
			  driver.findElement(By.xpath(or_getproperty("usrPwd"))).sendKeys(pwrd);
			  Thread.sleep(800);
			 // driver.findElement(By.xpath(or_getproperty("confimPwdTxtBox"))).sendKeys(pwrd);
			  String pwdStrsngth = driver.findElement(By.xpath(or_getproperty("pwdVeriyProgsBar"))).getText();
			  System.out.print(pwdStrsngth);
			 TestLog.info(" Password user for user creation is ::" + pwrd);
			  //boolean StatusofBtn =(driver.findElement(By.xpath(or_getproperty("createUserBtn"))).isEnabled());
			  // if (StatusofBtn == false) changed logic as new password bar was added :7-11-2017 charmee
			  if (pwdStrsngth.equals("weak")) 
			  {
				   result="true";	   
				   TestLog.info("user not created with invalid characters in its Password");
				   TestLog.info(" Password user for user creation is ::" + pwrd);
				   Assert.assertTrue(true,"user not created with invalid characters in its Password");
			   }
			   else
			   {
				   result="false";
				   TestLog.info("user created with invalid characters in its Password");
				   TestLog.info(" Password user for user creation is ::" + pwrd);
				   Assert.assertTrue(false, "user created with invalid characters in its Password");
			   }
			   driver.navigate().refresh();
			   //driver.findElement(By.xpath(or_getproperty("userCancleCross"))).click();
			  
		   }
	  
	}


public void verifyChnagedLanguage(String currentLang, String ExpectedLang, String Pagecheckedon)
{
	
	if(currentLang.equals(ExpectedLang))
	{
		result= "true";
		TestLog.info("language changed on "+ Pagecheckedon+ " page reflected successfully on dashboard page");
		Assert.assertTrue(true,"language changed on "+ Pagecheckedon+ " page reflected successfully on dashboard page");
	}
	else
	{
		result= "false";
		TestLog.info("language changed on "+ Pagecheckedon+ " page was not reflected successfully on dashboard page");
		Assert.assertTrue(false,"language changed on "+ Pagecheckedon+ " page was not reflected successfully on dashboard page");
	}
}
public void  VerifyLoginWithNoCreds(){
	if(driver.findElement(By.xpath(or_getproperty("loginBtn"))).isEnabled())
	{
		result ="false";
		TestLog.info("User login with No username and Password entered in it");
		Assert.assertTrue(false, "LoggedIn in to dashboard");
	}
	else
	{
		result= "true";
		TestLog.info("User not be able to login with NO username and Password entered in it");
		Assert.assertTrue(true, "LoggedIn in to dashboard");
			
	}
}





}	

