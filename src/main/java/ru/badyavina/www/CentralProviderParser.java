package ru.badyavina.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.badyavina.www.rows.CentralProviderRow;

public class CentralProviderParser {
	private String filenameFrom = null;
	private Map<String, CentralProviderRow> asIsCentralProviderMap;

	private static CentralProviderParser centralParser = new CentralProviderParser();

	private CentralProviderParser() {
		super();
		asIsCentralProviderMap = new HashMap<String, CentralProviderRow>();
	}

	public void setFilenameFrom(String fileName) {
		this.filenameFrom = fileName;
	}

	public static CentralProviderParser getInstance() {
		return centralParser;
	}

	public Map<String, CentralProviderRow> Parse() throws IOException {
		File myFile = new File(filenameFrom);

		FileInputStream fis = new FileInputStream(myFile);

		// Finds the workbook instance for XLSX file
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();
		int countAllRows = 0;
		// Traversing over each row of XLSX file

		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			if (row == null) {
				continue;
			}
			if (row.getCell(0) == null) {
				continue;
			}
			if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			}
			if (row.getCell(0).toString().equals("")) {
				continue;
			}
			if (row.getCell(7) == null) {
				continue;
			}
			if (row.getCell(7).getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			}
			if (row.getCell(7).toString().equals("")) {
				continue;
			}
			if (row.getCell(13) == null) {
				continue;
			}
			if (row.getCell(13).getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			}
			if (row.getCell(13).toString().equals("")) {
				continue;
			}

			CentralProviderRow tmpRow = new CentralProviderRow();
			tmpRow.setCode(row.getCell(7).toString());
			tmpRow.setRetailPrice(row.getCell(13).toString());

			asIsCentralProviderMap.put(tmpRow.getCode(), tmpRow);
			countAllRows++;

		}

		// For each row, iterate through each columns
//			Iterator<Cell> cellIterator = row.cellIterator();
//			while (cellIterator.hasNext()) {
//
//				Cell cell = cellIterator.next();
//				switch (cell.getCellType()) {
//				case Cell.CELL_TYPE_NUMERIC:
//					System.out.print(
//							"\t" + "cellID = " + cell.getColumnIndex() + "\t" + cell.getNumericCellValue() + "\t");
//					if (cell.getColumnIndex() == 0 && cell.getNumericCellValue() == 3) {
//
//						CentralProviderRow tmpRow = new CentralProviderRow();
//						if (row.getCell(1) == null || row.getCell(1).toString().equals("")) {
//							break;
//						} else {
//							tmpRow.setCode(row.getCell(1).toString());
//						}
//
//						if (row.getCell(8) == null || row.getCell(8).toString().equals("")) {
//							break;
//						} else {
//							tmpRow.setLeftOver(row.getCell(8).toString());
//						}
//
//						if (row.getCell(6) == null || row.getCell(6).toString().equals("")) {
//							break;
//						} else {
//							tmpRow.setName(row.getCell(6).toString());
//						}
//						asIsCentralProviderMap.put();
//
//					}
//					break;
//				default:
//
//				}
//			}

		System.out.println("The number of CENTRAL rows = " + countAllRows);
		myWorkBook.close();
		return asIsCentralProviderMap;
	}

}
