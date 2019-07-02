package ru.badyavina.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.badyavina.www.configure.files.Nomenclature;
import ru.badyavina.www.configure.files.Upload;
import ru.badyavina.www.rows.RowAsIs;

public class Runable {
	public static void main(String[] args) throws IOException {

		if (args.length != 0) {

			String path_from = args[0];
			String path_to = args[1];
			String fileNameUpload = args[2];

			File folder = new File(path_from);

			File[] matchingFiles = folder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.matches("( )*(�������)( )*[0-9]*.[0-9]*.[0-9]*( )*.(xlsx)") && name.endsWith(".xlsx");
				}
			});

			String pathFromFull = path_from + "\\" + matchingFiles[0].getName();

			//System.out.println(pathFromFull);

			Map<String, RowAsIs> mapAsIs;
			Parser parser = Parser.getParserInstance();
			parser.setFilenameFrom(pathFromFull);
			mapAsIs = parser.Parse();

			Nomenclature nomenclature = Nomenclature.getInstanceNomenclature();
			nomenclature.setMapAsIs(mapAsIs);
			nomenclature.configureNomenclatureMap();
			Date date = new Date();
			nomenclature.writeFile(path_to + "\\Nomenclature_" + date.getDate() + "_" + date.getMonth() + "_"
					+ (date.getYear() + 1900) + ".csv");
			Upload upload = Upload.getInstanceUpload();
			upload.setMapAsIs(mapAsIs);
			upload.configureUploadMap();
			upload.writeFile(path_to + "\\" + fileNameUpload);
			
			
			Sender sender = new Sender();
			sender.setFrom(pathFromFull);
			sender.send();
			

			System.out.println("����� [Enter]: ");
		} else {

			System.out.println("�� ����������� ��������� �������.");
		}
	}

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
