package ru.badyavina.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Runable {
	public static void main(String[] args) throws IOException {
		
		if(args.length!=0) {
			System.out.println("vas'ka");
		}
		
		Parser parser = new Parser();

		File myFile = new File("C:\\vianor_stock\\Остатки 18.06.19.xlsx");
		FileInputStream fis = new FileInputStream(myFile);

		// Finds the workbook instance for XLSX file
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();

		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			
			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {

				
				
				
				Cell cell = cellIterator.next();

//				if(cell.getCellType() == cell.CELL_TYPE_BLANK)
//				{
//					break;
//				}
				
				
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue() + "\t");
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue() + "\t");
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue() + "\t");
					break;
				default:

				}
			}
			System.out.println("");
		}

	}

}

/*
 * Примерный план: 1)Выгрузить номенклатуру 1.1) Нужно заменить все пробелы на
 * "_" 1.2) Выгружать номенклатуру дефолту каждый раз при выгрузке 2)распарсить
 * файл,записать его, отправить 2.1) Важно только читать файл, не изменяя его.
 * Так же нужно следить за дублями. 2.2) Просто записать в C:\. Приджоинить
 * поток записи, чтобы не допустить отправки перед записью. 2.3) Желательно
 * сделать нативно через java
 * 
 * 
 */
/*
cd C:/Users/svladimirov/eclipse-workspace/ru.badyavina.www

git add .
git commit -m "first commit"
git push -u origin master
 
 */
