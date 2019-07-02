package ru.badyavina.www.configure.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;

import ru.badyavina.www.rows.RowAsIs;

public class Nomenclature {
	private static Nomenclature nomenclature = new Nomenclature();
	private static final String n = "\n";
	private static final String colon = ";";
	private static final String replaceWith = "";
	private final Pattern p = Pattern.compile("( )*$");

	Map<String, RowAsIs> mapAsIs;
	Map<String, String> mapNomenclature;
	String finalData;

	private Nomenclature() {
		super();
		mapNomenclature = new HashMap<String, String>();
		finalData = "";
	}

	public static Nomenclature getInstanceNomenclature() {
		return nomenclature;
	}

	public void setMapAsIs(Map<String, RowAsIs> map) {
		this.mapAsIs = map;
	}

	public void configureNomenclatureMap() {
		if (mapAsIs != null) {
			mapAsIs.forEach((k, v) -> {
				if (k.toString().endsWith(" ")) {
					Matcher m = p.matcher(k);
					String output = m.replaceAll(replaceWith);
					mapNomenclature.put(output.replace(" ", "_").toLowerCase(), k);
				} else {
					mapNomenclature.put(k.toString().replace(" ", "_").toLowerCase(), k);
				}

			});
			// mapNomenclature.forEach((k, v) -> System.out.println("\t" + k + "\t" + v));
			
			System.out.println("NomenclatureMap.count = "+ mapNomenclature.size());
		}
	}

	public void writeFile(String fileName) throws IOException {
		if (mapNomenclature != null) {

			mapNomenclature.forEach((k, v) -> finalData += k + colon + v + n);
			FileOutputStream outputStream = new FileOutputStream(fileName);

			byte[] strToBytes = finalData.getBytes();

			outputStream.write(strToBytes);

			outputStream.close();
		}
	}

}
