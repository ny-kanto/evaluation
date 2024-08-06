package race.team.race.service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.io.FileReader;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

@Service
public class CsvService {

    @Autowired
    private DataSource dataSource;

    public void importCSV(String filePath, String tableName) {
        try {
            Connection connection = dataSource.getConnection();
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] line;
            // Ignorer la première ligne qui contient les en-têtes
            reader.readNext();

            String sql = "INSERT INTO " + tableName + " (etape_rang, numero_dossard, nom, genre, date_naissance, equipe, arrivee) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement prep = connection.prepareStatement(sql);

            while ((line = reader.readNext()) != null) {
                prep.setString(1, line[0]);
                prep.setString(2, line[1]);
                prep.setString(3, line[2]);
                prep.setString(4, line[3]);
                prep.setString(5, line[4]);
                prep.setString(6, line[5]);
                prep.setString(7, line[6]);
                prep.executeUpdate();
            }

            prep.close();
            connection.close();
            reader.close();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import CSV : " + e.getMessage());
        }
    }
}