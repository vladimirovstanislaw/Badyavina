package ru.badyavina.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.badyavina.www.rows.RowAsIs;

public class Parser {
	private String filenameFrom;
	private Map asismap;
	private static Parser parser = new Parser();

	private Parser() {
		super();
		asismap = new HashMap<String, RowAsIs>();
	}

	public static Parser getParserInstance() {
		return parser;
	}

	public Parser(String from) {
		this.filenameFrom = from;
	}

	public void setFilenameFrom(String from) {
		this.filenameFrom = from;
	}

	public Map Parse() throws IOException {

		File myFile = new File(filenameFrom);
		FileInputStream fis = new FileInputStream(myFile);

		// Finds the workbook instance for XLSX file
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();
		int i = 0;
		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			if (row == null) {
				continue;
			}
			if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			}
			if (row.getCell(0).equals("")) {
				continue;
			}
			i++;// counter

			RowAsIs asis = new RowAsIs();
			asis.setSize(row.getCell(0).toString());
			asis.setName(row.getCell(1).toString());
			asis.setLeftovers(row.getCell(2).toString());
			asis.setWholesalePrice(row.getCell(3).toString());
			asis.setPrice(row.getCell(4).toString());
			asismap.put(asis.getName(), asis);

		}
		System.out.println("AsIsMap.count = "+ asismap.size());// delete
		return asismap;
	}
}
