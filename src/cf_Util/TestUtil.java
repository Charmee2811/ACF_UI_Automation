/*
 **************************************************************
 Author : ACF Test Automation Team
 Purpose : Common utility for checking run mode at suite level and test case level.
 Date : 26/12/2016
 **************************************************************
 */

package cf_Util;


import cf_Util.Constants;


public class TestUtil extends Constants 
{
	public static xls_reader tcDeatils = new xls_reader(cf.Base.TestBase.config_getproperty("testDataPath")  + "Suite.xls");
	
	public static boolean isExecutable(String tcid)
	{
		//System.out.println("*************PATH************" + path);
		//System.out.println("*************In TestExecutable Actual Method Util Class*************" + tcid);
	//try{	
		for( int rowNum=2; rowNum<=tcDeatils.getRowCount("Suite"); rowNum++)
		{
			//System.out.println("*************In  For Loop***********");
			
			//System.out.println("***Curent Value in IF TCID:- " + vmDeatils.getCellData("CF_TestCase_Selection", "TCID", rowNum));
			
			if(tcDeatils.getCellData("Suite", "TCID", rowNum).equals(tcid))
			{
				//System.out.println("***Inside Big IF*******");
				
				if(tcDeatils.getCellData("Suite", "RunMode", rowNum).equalsIgnoreCase("Y"))
				{
					//System.out.println("***Curent Value:- " + vmDeatils.getCellData("CF_TestCase_Selection", "RunMode", rowNum));
					return true;		
				}
				else
				{	
					return false;
				}
			}		
		}
	//}
	/*catch(Exception e)
	{
	      System.out.println("Exception " + e);
	}*/
		return false;
	}



	public static boolean isSuiteExecutable(String suiteName)
	{
		System.out.println ("In isSuiteExecutable");
		boolean suiteResult=false;
	
		for(int rowNum=2; rowNum<=excel.getRowCount("Suite"); rowNum++)
		{
			if(excel.getCellData("Suite", "TSID", rowNum).equals(suiteName))
			{
				if(excel.getCellData("Suite", "RunMode", rowNum).equalsIgnoreCase("Y"))
				{
					suiteResult =  true;
				}
				
				else
				{
					suiteResult =  false;
				}
		
			}
			System.out.println("*********SUIT TO RUN**********" + suiteResult);
		}
	
		return suiteResult;

	}
	
	/*public static void setPassResult(String sheetName,String colName, int rowNum, String screenshot)
	{
		vmDeatils.setCellData(sheetName, colName, rowNum, screenshot);
		//vmDeatils.setCellData("Red_Hat_Enterprise_Linux", "Destroy_Screenshot", 2, excelScreenshotPath);
	}
	
	public static void setFailureResult()
	{
		vmDeatils.setCellData("Red_Hat_Enterprise_Linux", "Destroy_Result", 2, "PASS");
		//vmDeatils.setCellData("Red_Hat_Enterprise_Linux", "Destroy_Screenshot", 2, excelScreenshotPath);
	}*/


}
