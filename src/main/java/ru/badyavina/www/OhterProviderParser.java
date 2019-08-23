package ru.badyavina.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.badyavina.www.rows.OtherProviderRow;

public class OhterProviderParser {
	private String filenameFrom = null;
	private Map<String, OtherProviderRow> asIsOtherProviderMap;

	private static OhterProviderParser otherParser = new OhterProviderParser();

	private OhterProviderParser() {
		super();
		asIsOtherProviderMap = new HashMap<String, OtherProviderRow>();
	}

	public void setFilenameFrom(String fileName) {
		this.filenameFrom = fileName;
	}

	public static OhterProviderParser getInstance() {
		return otherParser;
	}

	public Map<String, OtherProviderRow> Parse() throws IOException {
		File myFile = new File(filenameFrom);

		FileInputStream fis = new FileInputStream(myFile);

		// Finds the workbook instance for XLSX file
		HSSFWorkbook myWorkBook = new HSSFWorkbook(fis);

		// Return first sheet from the XLSX workbook
		HSSFSheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();
		int countAllRows = 0;
		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {

				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:

					if (cell.getColumnIndex() == 0 && cell.getNumericCellValue() == 3) {

						OtherProviderRow tmpRow = new OtherProviderRow();
						if (row.getCell(1) == null || row.getCell(1).toString().equals("")) {
							break;
						} else {
							tmpRow.setCode(row.getCell(1).toString());
						}

						if (row.getCell(8) == null || row.getCell(8).toString().equals("")) {
							break;
						} else {
							tmpRow.setLeftOver(row.getCell(8).toString());
						}

						if (row.getCell(6) == null || row.getCell(6).toString().equals("")) {
							break;
						} else {
							tmpRow.setName(row.getCell(6).toString());
						}
						asIsOtherProviderMap.put(tmpRow.getCode(), tmpRow);
						countAllRows++;

					}
					break;
				default:

				}
			}
		}

		System.out.println("The number of OTHER rows = " + countAllRows);
		myWorkBook.close();
		return asIsOtherProviderMap;
	}
}
