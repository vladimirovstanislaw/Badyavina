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

			String path_from = args[0]; // Корневая папка
			String path_to = args[1]; // Куда кладем .csv - выгрузку и номеклатуру
			String fileNameUpload = args[2]; // имя отправляемого файла

			String lastFileOtherProvider = getLastModifiedFileNameByType(OTHER_TYPE);

			String lastFileCentralProvider = getLastModifiedFileNameByType(CENTRAL_TYPE);

			// Начинаем парсить
//			Map<String, EntityAsIs> mapAsIs;
//			Parser parser = Parser.getParserInstance();
//			parser.setFilenameFrom(pathFromFull);
//			mapAsIs = parser.Parse();

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
//			allDataMap.entrySet().stream().forEach(e -> {
//				System.out.println(e.getKey().toString() + "\t" + e.getValue().getCode() + "\t"
//						+ e.getValue().getLeftOver() + "\t" + e.getValue().getRetailPrice());
//			});

			// Конфигурируем файл номенклатуры
			Nomenclature nomenclature = Nomenclature.getInstanceNomenclature();
			nomenclature.setMapAsIs(allDataMap);
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

			System.out.println("Выход [Enter]: ");
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
 * Примерный план: 1)Выгрузить номенклатуру 1.1) Нужно заменить все пробелы на
 * "_" 1.2) Выгружать номенклатуру дефолту каждый раз при выгрузке 2)распарсить
 * файл,записать его, отправить 2.1) Важно только читать файл, не изменяя его.
 * Так же нужно следить за дублями. 2.2) Просто записать в C:\. Приджоинить
 * поток записи, чтобы не допустить отправки перед записью. 2.3) Желательно
 * сделать нативно через java
 * 
 * 
 */
/*
  cd C:/Users/svladimirov/eclipse-workspace/ru.badyavina.www
  
  git add . 
  git commit -m "first commit" 
  git push -u origin master
  
 */
