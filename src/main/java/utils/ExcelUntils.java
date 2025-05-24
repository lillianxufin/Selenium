package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUntils {
	private static XSSFWorkbook ExcelWB;
	private static XSSFSheet ExcelSheet;
	private static XSSFRow ExcelRow;
	private static XSSFCell ExcelCell;
	
	private static String path = Constants.Test_Result_Path;
	private static String fileName = Constants.Test_File_Name;
	private static String sheetName = Constants.Sheet_Name;
	private static int indexCol = Constants.Col_Index;
	private static int testCaseCol = Constants.Col_TestName;
	private static int resultCol = Constants.Col_Result;
	private static int timeCol = Constants.Col_Time;
	
	public XSSFWorkbook getWorkbook(String path, String fileName) throws Exception {
		XSSFWorkbook wb;
		try {
			FileInputStream file = new FileInputStream(path + fileName);
			wb = new XSSFWorkbook(file);
		} catch (FileNotFoundException e) {
			wb = new XSSFWorkbook();
			ExcelSheet = wb.createSheet(sheetName);
		}
		return wb;
	}
	
	public void setCellContent(XSSFWorkbook wb, int rowNum, int cellNum, String content) {
		XSSFSheet sheet = wb.getSheet(sheetName);
		XSSFRow row;
		if(sheet.getRow(rowNum) == null) {
			row = sheet.createRow(rowNum);
		} else {
			row = sheet.getRow(rowNum);
		}
		
		XSSFCell cell = row.createCell(cellNum);
		cell.setCellValue(content);
	}
	
	public void writeTestResult(String testName, Boolean result) throws Exception {
		XSSFWorkbook wb = getWorkbook(path, fileName);
		String testCase = testName;
		String testResult;
		if(result) {
			testResult = "PASSED";
		} else {
			testResult = "FAILED";
		}
		String timeStamp = LocalTime.now().toString();
		
		XSSFSheet sheet = wb.getSheet(sheetName);
		int row = sheet.getLastRowNum() + 1;
		setCellContent(wb, row, indexCol, (row + 1) + "");
		setCellContent(wb, row, testCaseCol, testCase);
		setCellContent(wb, row, resultCol, testResult);
		setCellContent(wb, row, timeCol, timeStamp);
		
		File dir = new File(path);
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }
		wb.write(new FileOutputStream(new File(path + fileName)));
		readExile();
		wb.close();
	}
	public void readExile() {
	try {
		FileInputStream file = new FileInputStream(path+fileName);
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		while(rows.hasNext()) {
			Row nextRow = rows.next();
			 Iterator<Cell> cells = nextRow.iterator();
			while(cells.hasNext()) {
				Cell nextCell = cells.next();
				
				switch(nextCell.getCellType()) {
				case NUMERIC:
					Double cellNumber = nextCell.getNumericCellValue();
					String cellNumberString = cellNumber.toString();
					System.out.print( cellNumberString + "  ");
					break;
				case STRING:
					String cellContent = nextCell.getStringCellValue();
					System.out.print(cellContent + "  ");
					break;
				default:
					System.out.print("  ");
					break;
				}
			}
			System.out.println();
		}
		workbook.close();
	} catch (IOException e) {
		e.printStackTrace();
		}
	}
}
