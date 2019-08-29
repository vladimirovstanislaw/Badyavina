package ru.badyavina.www.configure.files;

import java.io.FileOutputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;

import ru.badyavina.www.rows.AllDataRow;
import ru.badyavina.www.rows.RowUpload;

public class Upload {
	private static Upload upload = new Upload();
	private static final String n = "\r\n";
	private static final String colon = ";";
	Map<String, AllDataRow> allDataMap = null;
	Map<String, RowUpload> mapUpload = null;
	String finalData;

	private Upload() {
		super();
		mapUpload = new HashMap<String, RowUpload>();
		finalData = "";
	}

	public static Upload getInstanceUpload() {
		return upload;
	}

	public void setMapAsIs(Map<String, AllDataRow> map) {
		this.allDataMap = map;
	}

	public void configureUploadMap() {
		if (allDataMap != null) {
			allDataMap.forEach((k, v) -> {
				RowUpload rowUpload = new RowUpload();
				rowUpload.setCode(k.toString());
				rowUpload.setPrice(v.getRetailPrice());
				rowUpload.setLeftovers(v.getLeftOver());
				mapUpload.put(k, rowUpload);

			});
			System.out.println("UploadMap.count = " + mapUpload.size());
		}
	}

	public void writeFile(String fileName) throws IOException {
		if (mapUpload != null) {

			mapUpload.forEach((k, v) -> {
				finalData += v.getCode() + colon + v.getPrice() + colon + v.getLeftovers() + n;
			});
			FileOutputStream outputStream = new FileOutputStream(fileName);

			byte[] strToBytes = finalData.getBytes();

			outputStream.write(strToBytes);

			outputStream.close();
		}
	}

}
