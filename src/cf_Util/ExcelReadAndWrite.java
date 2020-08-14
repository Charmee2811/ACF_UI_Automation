

package cf_Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

//import com.sun.javafx.collections.MappingChange.Map;

//import cf.Base.Row;
//import cf.Base.Sheet;

public class ExcelReadAndWrite {


    private static final String String = null;
    static String excelPath = cf.Base.TestBase.config_getproperty("customReports") + "TestResultsData.xls";
	public static void backupReport(String fileName) throws IOException
	{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
        HSSFWorkbook workbook = new HSSFWorkbook(bis);
        HSSFWorkbook myWorkBook = new HSSFWorkbook();
        HSSFSheet sheet = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFSheet mySheet = null;
        HSSFRow myRow = null;
        HSSFCell myCell = null;
        int sheets = workbook.getNumberOfSheets();
        int fCell = 0;
        int lCell = 0;
        int fRow = 0;
        int lRow = 0;
        for (int iSheet = 0; iSheet < sheets; iSheet++) {
            sheet = workbook.getSheetAt(iSheet);
            if (sheet != null) {
                mySheet = myWorkBook.createSheet(sheet.getSheetName());
                fRow = sheet.getFirstRowNum();
                lRow = sheet.getLastRowNum();
                for (int iRow = fRow; iRow <= lRow; iRow++) {
                    row = sheet.getRow(iRow);
                    myRow = mySheet.createRow(iRow);
                    if (row != null) {
                        fCell = row.getFirstCellNum();
                        lCell = row.getLastCellNum();
                        for (int iCell = fCell; iCell < lCell; iCell++) {
                            cell = row.getCell(iCell);
                            myCell = myRow.createCell(iCell);
                            if (cell != null) {
                                myCell.setCellType(cell.getCellType());
                                switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_BLANK:
                                    myCell.setCellValue("");
                                    break;

                                case Cell.CELL_TYPE_BOOLEAN:
                                    myCell.setCellValue(cell.getBooleanCellValue());
                                    break;

                                case Cell.CELL_TYPE_ERROR:
                                    myCell.setCellErrorValue(cell.getErrorCellValue());
                                    break;

                                case Cell.CELL_TYPE_FORMULA:
                                    myCell.setCellFormula(cell.getCellFormula());
                                    break;

                                case Cell.CELL_TYPE_NUMERIC:
                                    myCell.setCellValue(cell.getNumericCellValue());
                                    break;

                                case Cell.CELL_TYPE_STRING:
                                    myCell.setCellValue(cell.getStringCellValue());
                                    break;
                                default:
                                    myCell.setCellFormula(cell.getCellFormula());
                                }
                            }
                        }
                    }
                }
            }
        }
        bis.close();
        DateFormat dateFormat = new SimpleDateFormat(cf.Base.TestBase.config_getproperty("dateFormat"));
		Calendar cal = Calendar.getInstance();
		//systemTime = ""+ dateFormat.format(cal.getTime());

    	String time = "" + dateFormat.format(cal.getTime());
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream (cf.Base.TestBase.config_getproperty("backupReports")+ "bckReport_" + time + ".xls"));
                //(System.getProperty("user.dir")+"\\Reports\\"+time+".xls", true));
        myWorkBook.write(bos);
        bos.close();
	}
    
    
   /* public static void removeRows() throws IOException
    {
    	String excelPath = cf.Base.TestBase.config_getproperty("customReports") + "TestResultsData.xls";
    	String sheetName = "Reports";
    	HSSFWorkbook workbook=null;
    	HSSFSheet sheet =null;
    	 FileInputStream file = new FileInputStream(new File(excelPath));
    	 file = new FileInputStream(new File(excelPath));
         workbook= new HSSFWorkbook(file);
         //sheet = workbook.getSheet(sheetName);
         
        // int lastRowNum=sheet.getLastRowNum();
         
        // for (Row i; i=lastRowNum)
        	// sheet.removeRow(i);
        
        		/* sheet = workbook.getSheet(sheetName);
        		 Iterator<Row> rowIte =  sheet.iterator();
        		 while(rowIte.hasNext())
        		 {
        		     rowIte.next();              
        		     rowIte.remove();
        		 }
        		 System.out.println("Rows deleted");
         
         
          sheet = workbook.getSheetAt(0);
         for (Row row : sheet) {
            sheet.removeRow(row);
         }
         System.out.println("Rows deleted");
     }*/
    
    public static void clearOldFile(){
    	
        FileOutputStream out = null;
        try{
        	HSSFWorkbook oldFile = new HSSFWorkbook();    
        	HSSFSheet sheet = oldFile.createSheet("TestResult");

            out = new FileOutputStream(excelPath);
            oldFile.write(out);
            out.close();

        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Rows deleted");
        TestLog.info("Rows deleted");
        writeInExcel();
    }
    
    public static void writeInExcel()
    {
    	HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("TestResult");
		
		HashMap<java.lang.String, Object[]> data = new HashMap<String, Object[]>();
		data.put("1", new Object[] {"TestCase_Number",	"Description",	"Result","Executed_Date", "BrowserType", "Env_URL","ScreeonShot_Path"});
			
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if(obj instanceof Date) 
					cell.setCellValue((Date)obj);
				else if(obj instanceof Boolean)
					cell.setCellValue((Boolean)obj);
				else if(obj instanceof String)
					cell.setCellValue((String)obj);
				else if(obj instanceof Double)
					cell.setCellValue((Double)obj);
			}
		}
		
		try {
			FileOutputStream out = 
					new FileOutputStream(new File(excelPath));
			workbook.write(out);
			out.close();
			TestLog.info("Excel written successfully..");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
public static void SWAPPass(String oldpass, String newPass)throws Exception{
        //written by harshali 
	String filePath=System.getProperty("user.dir") +"\\TestData\\ChangepassworOfNonAdminUser.xls";
        FileInputStream fsIP= new FileInputStream(new File(filePath)); //Read the spreadsheet that needs to be updated
          
        HSSFWorkbook wb = new HSSFWorkbook(fsIP); //Access the workbook
          
        HSSFSheet worksheet = wb.getSheetAt(1); //Access the worksheet, so that we can update / modify it.
          
        Cell cell = null; // declare a Cell object
        
        cell = worksheet.getRow(1).getCell(1);   // Access the second cell in second row to update the value
        String var=oldpass;
          
        cell.setCellValue(newPass);  // Get current cell value value and overwrite the value
        cell =worksheet.getRow(1).getCell(2);
        cell.setCellValue(var);
          
        fsIP.close(); //Close the InputStream
         
        FileOutputStream output_file =new FileOutputStream(new File(filePath));  //Open FileOutputStream to write updates
          
        wb.write(output_file); //write changes
          
        output_file.close();  //close the stream    
       
        
}


  }

