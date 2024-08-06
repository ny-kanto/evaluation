package race.team.race.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import race.team.race.utils.Utils;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class Export {
    public <T> void exportToExcel(List<T> dataList, String filePath) throws IOException {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("La liste des données est vide.");
        }

        Workbook workbook = WorkbookFactory.create(true); // Crée un nouveau classeur Excel
        Sheet sheet = workbook.createSheet("Data"); // Crée une feuille dans le classeur
        Field[] fields = dataList.get(0).getClass().getDeclaredFields();

        // Ajoute l'en-tête des colonnes
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < fields.length; i++) {
            headerRow.createCell(i).setCellValue(fields[i].getName());
        }

        // Boucle sur les données et les ajoute à la feuille
        int rowIndex = 1;
        Row row;
        Method getterMethod;
        Object value;
        for (T data : dataList) {
            row = sheet.createRow(rowIndex++);
            for (int index = 0; index < fields.length; index++) {
                try {
                    getterMethod = Utils.getGetterMethod(data.getClass(), fields[index].getName());
                    value = getterMethod.invoke(data);
                    if (value != null) {
                        row.createCell(index).setCellValue(value.toString());
                    } else {
                        row.createCell(index).setCellValue("");
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        // Ajuste automatiquement la largeur des colonnes
        for (int i = 0; i < fields.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();
    }

    public <T> void exportToCSV(List<T> dataList, String filePath) throws IOException {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("La liste des données est vide.");
        }

        FileWriter writer = new FileWriter(filePath);
        // Obtient les attributs de la classe T
        Field[] fields = dataList.get(0).getClass().getDeclaredFields();

        // Écrit l'en-tête CSV
        for (int i = 0; i < fields.length; i++) {
            writer.append(fields[i].getName());
            if (i != fields.length - 1) {
                writer.append(",");
            }
        }
        writer.append("\n");
        Method getterMethod;
        Object value;
        // Écrit les données CSV
        for (T data : dataList) {
            for (int i = 0; i < fields.length; i++) {
                try {
                    getterMethod = Utils.getGetterMethod(data.getClass(), fields[i].getName());
                    value = getterMethod.invoke(data);
                    if (value != null) {
                        writer.append(value.toString());
                    }
                    if (i != fields.length - 1) {
                        writer.append(",");
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            writer.append("\n");
        }
        writer.close();
    }
}
