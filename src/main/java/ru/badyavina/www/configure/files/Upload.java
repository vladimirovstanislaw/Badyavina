package ru.badyavina.www.configure.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;

import ru.badyavina.www.rows.RowAsIs;
import ru.badyavina.www.rows.RowUpload;

public class Upload {
	private static Upload upload = new Upload();
	private static final String n = "\n";
	private static final String colon = ";";
	private static final String replaceWith = "";
	private static final Pattern pattern = Pattern.compile("( )*$");
	Map<String, RowAsIs> mapAsIs;
	Map<String, RowUpload> mapUpload;
	String finalData;

	private Upload() {
		super();
		mapUpload = new HashMap<String, RowUpload>();
		finalData = "";
	}

	public static Upload getInstanceUpload() {
		return upload;
	}

	public void setMapAsIs(Map<String, RowAsIs> map) {
		this.mapAsIs = map;
	}

	public void configureUploadMap() {
		if (mapAsIs != null) {
			mapAsIs.forEach((k, v) -> {
				RowUpload rowUpload = new RowUpload();
				rowUpload.setName(getLowerCaseStringWithoutEndings(k));
				rowUpload.setPrice(v.getPrice());
				rowUpload.setLeftovers(v.getLeftovers().replace(" ", ""));
				mapUpload.put(k, rowUpload);

			});
			System.out.println("UploadMap.count = " + mapUpload.size());
		}
	}

	public String getLowerCaseStringWithoutEndings(String input) {
		if (input.endsWith(" ")) {
			Matcher m = pattern.matcher(input);
			return m.replaceAll(replaceWith).replace(" ", "_").toLowerCase();
		} else {
			return input.replace(" ", "_").toLowerCase();
		}

	}

	public void writeFile(String fileName) throws IOException {
		if (mapUpload != null) {

			mapUpload.forEach((k, v) -> {
				finalData += v.getName() + colon + v.getPrice() + colon + v.getLeftovers() + n;
			});
			FileOutputStream outputStream = new FileOutputStream(fileName);

			byte[] strToBytes = finalData.getBytes();

			outputStream.write(strToBytes);

			outputStream.close();
		}
	}

}
