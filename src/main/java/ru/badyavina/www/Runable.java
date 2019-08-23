package ru.badyavina.www;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import ru.badyavina.www.configure.files.Nomenclature;
import ru.badyavina.www.configure.files.Upload;
import ru.badyavina.www.get.files.GmailQuickstart;
import ru.badyavina.www.rows.AllDataRow;
import ru.badyavina.www.rows.CentralProviderRow;
import ru.badyavina.www.rows.OtherProviderRow;

public class Runable {

	public static String otherProviderFilePath = "C:\\vianor_stock\\OtherProvider";
	public static String centralProviderFilePath = "C:\\vianor_stock\\CentralProvider";
	public static final String OTHER_TYPE = "other";
	public static final String CENTRAL_TYPE = "central";
	static int i = 0;

	public static void main(String[] args) throws IOException, GeneralSecurityException {

		if (args.length != 0) {

			String path_from = args[0]; // �������� �����
			String path_to = args[1]; // ���� ������ .csv - �������� � �����������
			String fileNameUpload = args[2]; // ��� ������������� �����
			String pathToSaveCentralFiles = args[3]; // ���� ����� ������ �������� central provider'a
			String pathToSaveOtherFiles = args[4]; // ���� ����� ������ �������� other provider'a
			String emailCentralProvider = args[5]; // email cental provider
			String emailOtherProvider = args[6]; // email other provider

			otherProviderFilePath = pathToSaveOtherFiles;
			centralProviderFilePath = pathToSaveCentralFiles;

			File folderCentral = new File(pathToSaveCentralFiles);
			File folderOther = new File(pathToSaveOtherFiles);

			
			
//									    ||
//									    ||
			//Uncomment that after test ||
//									   \  /
//										\/
			

			
//			GmailQuickstart gmail = new GmailQuickstart(pathToSaveCentralFiles, pathToSaveOtherFiles,
//					emailCentralProvider, emailOtherProvider);
//
//			
//
//			GmailQuickstart.clearFolder(folderCentral);// ������� ����� central provider'a
//			GmailQuickstart.clearFolder(folderOther);// ������� ����� other provider'a
//
//			gmail.run();

			String lastFileOtherProvider = getLastModifiedFileNameByType(OTHER_TYPE);

			String lastFileCentralProvider = getLastModifiedFileNameByType(CENTRAL_TYPE);

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
				System.out.println(dir.toString()+name);
				return name.endsWith(".xls");
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

//	//public static boolean isFileAcceptedByTime(String fileName) {
//
//		File file = new File(fileName);
//		Date now = new Date();
//
//		double howOldIsSource = (now.getTime() - file.lastModified()) / (86400000);
//
//		if (howOldIsSource <= 1.5) {
//			return true;
//		}
//		return false;
//	}
}
