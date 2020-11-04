package com.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelUtilities {

	String path;
	String sheetname;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;


	@DataProvider(name = "data")
	public Object[][] dataSupplier(String testDataFile) throws IOException {

		int lastRowNum = sheet.getLastRowNum() ;
		int lastCellNum = sheet.getRow(0).getLastCellNum();

		Object[][] obj = new Object[lastRowNum][lastCellNum];

		for (int i = 0; i < lastRowNum; i++) {
			for (int k = 0; k < lastCellNum; k++) {
				obj[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}

		return  obj;
	}


	public  int getRowNumForRowName(String sheetname,String rowName) {
		int rownum=0;
		sheet=workbook.getSheet(sheetname);
		for(int i=1;i<=getLastRowNum(sheetname);i++) {
			if(rowName.equalsIgnoreCase(sheet.getRow(i).getCell(0).getStringCellValue())) {
				rownum=i;
				break;
			}
		}

		return rownum;
	}

	public  int getColumnNumForColumnName(String sheetname, String columnname) {
		int colnum=0;
		sheet=workbook.getSheet(sheetname);
		for(int i=0;i<getLastColumnNum(sheetname, 0);i++) {
			if(columnname.equalsIgnoreCase(sheet.getRow(0).getCell(i).getStringCellValue())) {
				colnum=i;
				break;
			}
		}

		return colnum;

	}


	public  int getLastRowNum(String sheetname) {
		return workbook.getSheet(sheetname).getLastRowNum();
	}


	public  int getLastColumnNum(String sheetname, int rownum) {

		return workbook.getSheet(sheetname).getRow(rownum).getLastCellNum();

	}

	public  String getCellContent(String sheetname,int rownum,int colnum) {
		sheet=workbook.getSheet(sheetname);

		return sheet.getRow(rownum).getCell(colnum).getStringCellValue().concat("").toString();

	}

	public  String getCellContent(String sheetname,int rownum,String columnname) {
		sheet=workbook.getSheet(sheetname);

		return sheet.getRow(rownum).getCell(getColumnNumForColumnName(sheetname, columnname)).getStringCellValue().concat("").toString();

	}


	public  String getCellContent(String sheetname,String rowname,String columnname) {
		sheet=workbook.getSheet(sheetname);
		int rownum=getRowNumForRowName(sheetname, rowname);

		int colnum=getColumnNumForColumnName(sheetname, columnname);

		return sheet.getRow(rownum).getCell(colnum).getStringCellValue().concat("").toString();

	}
}
