package cf.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cf.Base.itemAction;
import cf.Base.itemVerification;
import cf_Util.TestLog;
import cf_Util.xls_reader;
public class springApp extends itemVerification
	{ 
		String url = "http://spring-music.apps.eu.cfdev.canopy-cloud.com/";
		@Test(priority=1)
			public void addData() throws Exception
			{
			    openBrowser();
			    driver.get(url);	
			    Thread.sleep(1000);    
			 
			}
			
		
		@DataProvider(name="addAblumData")
		public Object[][] creatOrg() throws BiffException  {
			Object[][] arrayObject = xls_reader.getExcelData(config_getproperty("testDataPath") + "addSpringMusicData.xls","sheet1");
			return arrayObject;
		}
		
	@Test(priority=2,dataProvider = "addAblumData")
		public void orgCreation(String albumTitle,String artist,String year,String genere) throws InterruptedException
		{
		getObject("addAlbum");
	    driver.findElement(By.xpath(or_getproperty("addAlbum"))).click();
	    driver.switchTo().activeElement();
	    Thread.sleep(1000);
			driver.findElement(By.xpath(or_getproperty("ablumTitle"))).sendKeys(albumTitle);
		     driver.findElement(By.xpath(or_getproperty("artist"))).sendKeys(artist);
		     driver.findElement(By.xpath(or_getproperty("year"))).sendKeys(year);
		     driver.findElement(By.xpath(or_getproperty("genere"))).sendKeys(genere);
		     driver.findElement(By.xpath(or_getproperty("okBtn"))).click();
		     Thread.sleep(500);
		}
				
		
		
		
		
	 }


