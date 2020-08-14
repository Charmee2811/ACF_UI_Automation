/*
 **************************************************************
 Author : IaaS Test Automation Team
 Purpose : Declaration of constants/reusable variables.
 Date : 26/02/2016
 **************************************************************
 */

package cf_Util;



public class Constants {
	
	public static xls_reader excel = new xls_reader(cf.Base.TestBase.config_getproperty("testDataPath") + "\\" + "Suite.xls");
	
	//public static xls_reader excel = new xls_reader(System.getProperty("user.dir")+"\\src\\tca\\com\\canopy\\xls\\Suite.xls");
	//public static xls_reader vmDeatils = new xls_reader(System.getProperty("user.dir")+"\\src\\tca\\com\\canopy\\xls\\TestData.xls");

	
}
