package ru.badyavina.www.configure.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.badyavina.www.rows.AllDataRow;
import ru.badyavina.www.rows.CentralProviderRow;

public class Nomenclature {
	private static Nomenclature nomenclature = new Nomenclature();
	private static final String n = "\r\n";
	private static final String colon = ";";
	private static final String replaceWith = "";
	private final Pattern p = Pattern.compile("( )*$");

	Map<String, CentralProviderRow> centralDataMap;
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

	public void setMapAsIs(Map<String, CentralProviderRow> map) {
		this.centralDataMap = map;
	}

	public void configureNomenclatureMap() {
		if (centralDataMap != null) {
			centralDataMap.entrySet().stream().forEach(e->{
				mapNomenclature.put(e.getKey(),e.getValue().getName());
			});
					
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
