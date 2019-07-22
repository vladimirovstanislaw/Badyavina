package ru.badyavina.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Parser {
//	private String filenameFrom;
//
//	private Map<String, EntityAsIs> asismap;
//	private static Parser parser = new Parser();
//
//	private Parser() {
//		super();
//		asismap = new HashMap<String, EntityAsIs>();
//	}
//
//	public static Parser getParserInstance() {
//		return parser;
//	}
//
//	public Parser(String from) {
//		this.filenameFrom = from;
//	}
//
//	public void setFilenameFrom(String from) {
//		this.filenameFrom = from;
//	}
//
//	public Map<String, EntityAsIs> Parse() throws IOException {
//
//		File myFile = new File(filenameFrom);
//		FileInputStream fis = new FileInputStream(myFile);
//
//		// Finds the workbook instance for XLSX file
//		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
//
//		// Return first sheet from the XLSX workbook
//		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
//
//		// Get iterator to all the rows in current sheet
//		Iterator<Row> rowIterator = mySheet.iterator();
//		int i = 0;
//		// Traversing over each row of XLSX file
//		int three_or_four = 0;
//		while (rowIterator.hasNext()) {
//			Row row = rowIterator.next();
//			System.out.println("Row id - "+row.getRowNum());
//			
//			if (row.getCell(0).toString().equals("")) {
//				continue;
//			}
//			if (row.getCell(0).toString().equals("3") || row.getCell(0).toString().equals("4")) {
//				three_or_four++;
//			}
//			i++;// counter
//
//			EntityAsIs asis = new EntityAsIs();
//			asis.setSize(row.getCell(0).toString());
//			asis.setName(row.getCell(1).toString());
//			asis.setLeftovers(row.getCell(2).toString());
//			asis.setWholesalePrice(row.getCell(3).toString());
//			asis.setPrice(row.getCell(4).toString());
//			asismap.put(asis.getName(), asis);
//
//		}
//		System.out.println("AsIsMap.count = " + asismap.size());// delete
//		System.out.println("Not parsed rows = " + three_or_four);
//		return asismap;
//	}
}
