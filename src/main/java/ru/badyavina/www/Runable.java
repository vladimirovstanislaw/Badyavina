package ru.badyavina.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.badyavina.www.configure.files.Nomenclature;
import ru.badyavina.www.configure.files.Upload;
import ru.badyavina.www.rows.AllDataRow;
import ru.badyavina.www.rows.CentralProviderRow;
import ru.badyavina.www.rows.OtherProviderRow;

public class Runable {

	public static final String otherProviderFilePath = "C:\\vianor_stock\\OtherProvider";
	public static final String centralProviderFilePath = "C:\\vianor_stock\\CentralProvider";
	public static final String OTHER_TYPE = "other";
	public static final String CENTRAL_TYPE = "central";
	static int i = 0;

	public static void main(String[] args) throws IOException {

		if (args.length != 0) {

			String path_from = args[0]; // �������� �����
			String path_to = args[1]; // ���� ������ .csv - �������� � �����������
			String fileNameUpload = args[2]; // ��� ������������� �����

			String lastFileOtherProvider = getLastModifiedFileNameByType(OTHER_TYPE);

			String lastFileCentralProvider = getLastModifiedFileNameByType(CENTRAL_TYPE);

			// �������� �������
//			Map<String, EntityAsIs> mapAsIs;
//			Parser parser = Parser.getParserInstance();
//			parser.setFilenameFrom(pathFromFull);
//			mapAsIs = parser.Parse();

			// ������ ���� ���������� ��-������
			OhterProviderParser otherParser = OhterProviderParser.getInstance();
			otherParser.setFilenameFrom(lastFileOtherProvider.toString()); // ���� � ������� �� ������� ����������
			Map<String, OtherProviderRow> otherMap = otherParser.Parse();

			// ������ ���� ������������ ����������
			CentralProviderParser centralParser = CentralProviderParser.getInstance();
			centralParser.setFilenameFrom(lastFileCentralProvider.toString());
			Map<String, CentralProviderRow> centralMap = centralParser.Parse(); // ���� � ������� �� ������������
																				// ����������

			// ������� ����������� ����
			Collection<String> intersection = CollectionUtils.intersection(otherMap.keySet(), centralMap.keySet());

			Map<String, AllDataRow> allDataMap = new HashMap<String, AllDataRow>();

			// ��������� �����������
			intersection.stream().forEach(e -> {
				// ������� ������
				AllDataRow tmpRow = new AllDataRow();
				// ��������� ���
				tmpRow.setCode(e.toString());
				// ��������� ���
				tmpRow.setName(otherMap.get(e.toString()).getName());
				// ��������� �������
				tmpRow.setLeftOver(otherMap.get(e.toString()).getLeftOver());
				// ��������� ��������� ����
				tmpRow.setRetailPrice(centralMap.get(e.toString()).getRetailPrice());
				allDataMap.put(e.toString(), tmpRow);

			});
//			allDataMap.entrySet().stream().forEach(e -> {
//				System.out.println(e.getKey().toString() + "\t" + e.getValue().getCode() + "\t"
//						+ e.getValue().getLeftOver() + "\t" + e.getValue().getRetailPrice());
//			});

			// ������������� ���� ������������
			Nomenclature nomenclature = Nomenclature.getInstanceNomenclature();
			nomenclature.setMapAsIs(allDataMap);
			nomenclature.configureNomenclatureMap();
			Date date = new Date();
			nomenclature.writeFile(path_to + "\\Nomenclature_" + date.getDate() + "_" + date.getMonth() + "_"
					+ (date.getYear() + 1900) + ".csv");
			// ������������� ���� ��������
			Upload upload = Upload.getInstanceUpload();
			upload.setMapAsIs(allDataMap);
			upload.configureUploadMap();
			upload.writeFile(path_to + "\\" + fileNameUpload);
			// �������� ��������
			Sender sender = new Sender();
			sender.setData(path_from, fileNameUpload);
			sender.send();

			System.out.println("����� [Enter]: ");
		} else {

			System.out.println("�� ����������� ��������� �������.");
		}
	}

	public static String getLastModifiedFileNameByType(String type) {
		File folder = null;
		if (type.equals("other")) {

			// ���� ������ ���� OTHER provider'� , �� ��� ������� ���������
			folder = new File(otherProviderFilePath);
		}
		if (type.equals("central")) {
			folder = new File(centralProviderFilePath);
		}
		File[] matchingFiles = folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xlsx");
			}
		});
		File lastFile = matchingFiles[0];

		for (int i = 0; i < matchingFiles.length; i++) {
			if (lastFile.lastModified() < matchingFiles[i].lastModified()) {
				lastFile = matchingFiles[i];
			}
		}

		System.out.println("Last modified file of " + type + " provider:" + lastFile);

		return lastFile.getPath();

	}
//
//	public String getCentralProviderFileName(String from) {
//
//	}
//
//	public String getLastModifiedFileName(String from) {
//
//	}

}

/*
 * ��������� ����: 1)��������� ������������ 1.1) ����� �������� ��� ������� ��
 * "_" 1.2) ��������� ������������ ������� ������ ��� ��� �������� 2)����������
 * ����,�������� ���, ��������� 2.1) ����� ������ ������ ����, �� ������� ���.
 * ��� �� ����� ������� �� �������. 2.2) ������ �������� � C:\. �����������
 * ����� ������, ����� �� ��������� �������� ����� �������. 2.3) ����������
 * ������� ������� ����� java
 * 
 * 
 */
/*
  cd C:/Users/svladimirov/eclipse-workspace/ru.badyavina.www
  
  git add . 
  git commit -m "first commit" 
  git push -u origin master
  
 */
