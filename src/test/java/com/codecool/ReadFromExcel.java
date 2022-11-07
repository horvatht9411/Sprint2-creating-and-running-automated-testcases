package com.codecool;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadFromExcel {

    public static String[][] get(String sheetName) {
        String[][] dataTable = null;
        File filename = new File("src/test/resources/data.xlsx");
        try {
            FileInputStream file = new FileInputStream(filename);
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet xlSheet = wb.getSheet(sheetName);
            int numRows = xlSheet.getLastRowNum() + 1;
            int numCols = xlSheet.getRow(0).getLastCellNum();
            dataTable = new String[numRows][numCols];
            for (int i = 0; i < numRows; i++) {
                Row xlRow = xlSheet.getRow(i);
                for (int j = 0; j < numCols; j++) {
                    Cell xlCell = xlRow.getCell(j);
                    dataTable[i][j] = xlCell.toString();
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR FILE HANDLING " + e);
        }
        return dataTable;
    }
}