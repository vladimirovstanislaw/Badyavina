package ru.badyavina.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

			String path_from = args[0]; // Корневая папка
			String path_to = args[1]; // Куда кладем .csv - выгрузку и номеклатуру
			String fileNameUpload = args[2]; // имя отправляемого файла
			String pathToSaveCentralFiles = args[3]; // куда будем класть выгрузку central provider'a
			String pathToSaveOtherFiles = args[4]; // куда будем класть выгрузку other provider'a
			String emailCentralProvider = args[5]; // email cental provider
			String emailOtherProvider = args[6]; // email other provider
			String codeShini = args[7]; // email other provider
			
			otherProviderFilePath = pathToSaveOtherFiles;
			centralProviderFilePath = pathToSaveCentralFiles;

			File folderCentral = new File(pathToSaveCentralFiles);
			File folderOther = new File(pathToSaveOtherFiles);

//									    ||
//									    ||
			// Uncomment that after test ||
//									   \  /
//										\/

			GmailQuickstart gmail = new GmailQuickstart(pathToSaveCentralFiles, pathToSaveOtherFiles,
					emailCentralProvider, emailOtherProvider,codeShini);

			GmailQuickstart.clearFolder(folderCentral);// очищаем папку central provider'a
			GmailQuickstart.clearFolder(folderOther);// очищаем папку other provider'a

			gmail.run();
			unzipOther(pathToSaveOtherFiles);
			String lastFileCentralProvider = getLastModifiedFileNameByType(CENTRAL_TYPE);
			String lastFileOtherProvider = getLastModifiedFileNameByType(OTHER_TYPE);

			System.out.println("Resolved name of central provider file:" + lastFileCentralProvider);
			System.out.println("Resolved name of other provider file:" + lastFileOtherProvider);


			// Парсим файл поставщика по-меньше
			OhterProviderParser otherParser = OhterProviderParser.getInstance();
			otherParser.setFilenameFrom(lastFileOtherProvider.toString()); // мапа с данными от другого поставщика
			Map<String, OtherProviderRow> otherMap = otherParser.Parse();

			// Парсим файл центрального поставщика
			CentralProviderParser centralParser = CentralProviderParser.getInstance();
			centralParser.setFilenameFrom(lastFileCentralProvider.toString());
			Map<String, CentralProviderRow> centralMap = centralParser.Parse(); // мапа с данными от центрального
																				// поставщика


			// находим пересечение карт
			Collection<String> intersection = CollectionUtils.intersection(otherMap.keySet(), centralMap.keySet());

			Map<String, AllDataRow> allDataMap = new HashMap<String, AllDataRow>();

			// добавляем пересечение
			intersection.stream().forEach(e -> {
				// Создаем объект
				AllDataRow tmpRow = new AllDataRow();
				// Вставляем код
				tmpRow.setCode(e.toString());
				// Вставляем имя
				tmpRow.setName(otherMap.get(e.toString()).getName());
				// Вставляем остатки
				tmpRow.setLeftOver(otherMap.get(e.toString()).getLeftOver());
				// Вставляем розничную цену
				tmpRow.setRetailPrice(centralMap.get(e.toString()).getRetailPrice());
				allDataMap.put(e.toString(), tmpRow);

			});

			// Конфигурируем файл номенклатуры
			Nomenclature nomenclature = Nomenclature.getInstanceNomenclature();
			nomenclature.setMapAsIs(centralMap);
			nomenclature.configureNomenclatureMap();
			Date date = new Date();
			nomenclature.writeFile(path_to + "\\Nomenclature_" + date.getDate() + "_" + date.getMonth() + "_"
					+ (date.getYear() + 1900) + ".csv");
			// Конфигурируем файл выгрузки
			Upload upload = Upload.getInstanceUpload();
			upload.setMapAsIs(allDataMap);
			upload.configureUploadMap();
			upload.writeFile(path_to + "\\" + fileNameUpload);
			// Высылаем выгрузку
			Sender sender = new Sender();
			sender.setData(path_from, fileNameUpload);
			sender.send();

			System.out.println("Done. \u203E\\( \u25CF , \u25CF)/\u203E");
		} else {

			System.out.println("Не установлены параметры запуска.");
		}

	}

	public static String getLastModifiedFileNameByType(String type) {
		File folder = null;
		if (type.equals("other")) {

			// Ищем нужный файл OTHER provider'а , кт был изменен последним
			folder = new File(otherProviderFilePath);
		}
		if (type.equals("central")) {
			folder = new File(centralProviderFilePath);
		}
		File[] matchingFiles = folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
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

	public static void unzipOther(String path) throws IOException {
		File folder = null;
		folder = new File(otherProviderFilePath);
		File[] matchingFiles = folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {

				return name.endsWith(".zip");
			}
		});

		String fileZip = matchingFiles[0].toString();

		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {
			File newFile = newFile(folder, zipEntry);
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
	}

	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

}
