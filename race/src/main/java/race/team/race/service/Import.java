package race.team.race.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import race.team.race.utils.Utils;

@Service
public class Import {
    public List<List<String>> importCSV(String csvFilePath, String encoding) {
        List<List<String>> data = new ArrayList<>();

        try {
            Reader reader = new InputStreamReader(new FileInputStream(csvFilePath), encoding);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            // Parcourir chaque enregistrement du fichier CSV
            for (CSVRecord csvRecord : csvParser) {
                List<String> rowData = new ArrayList<>();

                // Accéder aux valeurs de chaque colonne de l'enregistrement
                for (String value : csvRecord) {
                    rowData.add(value);
                }

                // Ajouter la ligne de données à la liste
                data.add(rowData);
            }
            csvParser.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public List<List<String>> importExcel(String excelFilePath) {
        List<List<String>> data = new ArrayList<>();

        try {
            InputStream inputStream = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            List<String> rowData;
            Row row;
            Iterator<Cell> cellIterator;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                rowData = new ArrayList<>();

                cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    rowData.add(Utils.getCellValue(cell));
                }

                data.add(rowData);
            }
            workbook.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
