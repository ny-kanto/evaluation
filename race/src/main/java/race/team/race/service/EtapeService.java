package race.team.race.service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import java.text.SimpleDateFormat;

import java.io.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

import race.team.race.entity.Etape;
import race.team.race.repository.EtapeRepository;
import race.team.race.utils.IdGenerator;

@Service
public class EtapeService {
    @Autowired
    private EtapeRepository er;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IdGenerator idGenerator;

    public Etape insererEtape(Etape etape) {
        return er.save(etape);
    }

    public List<Etape> getEtapes() {
        return er.findAll();
    }

    public Etape getEtapeById(String id) {
        return er.findById(id).get();
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }    

    public void importCsvEtape(String csvFile) {
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            // Ignorer la première ligne qui contient les en-têtes
            reader.readNext();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            Etape etape;
            while ((line = reader.readNext()) != null) {
                etape = new Etape();
                etape.setNom(line[0]);
                etape.setLongueur(line[1].replace(',', '.'));
                etape.setNbCoureurEquipe(Integer.valueOf(line[2]));
                etape.setRang(Integer.valueOf(line[3]));
                etape.setDateDepart(new Date(dateFormat.parse(line[4]).getTime()));
                etape.setHeureDepart(Time.valueOf(line[5]));
                etape.setId(idGenerator);

                er.save(etape);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Etape> getEtapePerPage(int noPage, int nbParPage, String column, int sort) {
        List<Etape> etape = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM etape ORDER BY " + column + " " + ((sort % 2) == 1 ? "ASC" : "DESC")
                    + " LIMIT ? OFFSET ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, nbParPage);
            ps.setInt(2, (noPage - 1) * nbParPage);
            ResultSet rs = ps.executeQuery();

            Etape e;
            while (rs.next()) {
                e = new Etape();
                e.setId(rs.getString("id"));
                e.setNom(rs.getString("nom"));
                e.setLongueur(rs.getDouble("longueur"));
                e.setNbCoureurEquipe(rs.getInt("nb_coureur_equipe"));
                e.setRang(rs.getInt("rang"));
                e.setDateDepart(rs.getDate("date_depart"));
                e.setHeureDepart(rs.getTime("heure_depart"));
                etape.add(e);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etape;
    }

    public String sql(String nom, double longueurMin, double longueurMax, int nbCoureurMin, int nbCoureurMax) {
        String sql = "SELECT * FROM etape WHERE 1 = 1 ";

        if (!nom.equals("")) {
            sql += "AND nom LIKE ? ";
        }

        if (longueurMin != 0.0) {
            sql += "AND longueur >= ? ";
        }

        if (longueurMax != 0.0) {
            sql += "AND longueur <= ? ";
        }

        if (nbCoureurMin != 0.0) {
            sql += "AND nb_coureur_equipe >= ? ";
        }

        if (nbCoureurMax != 0.0) {
            sql += "AND nb_coureur_equipe <= ? ";
        }
        return sql;
    }

    public List<Etape> getEtapeFiltre(String nom, double longueurMin, double longueurMax, int nbCoureurMin, int nbCoureurMax) {
        List<Etape> etape = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = sql(nom, longueurMin, longueurMax, nbCoureurMin, nbCoureurMax);

            PreparedStatement ps = c.prepareStatement(sql);
            int parameterIndex = 1;

            if (!nom.equals("")) {
                ps.setString(parameterIndex, "%" + nom + "%");
                parameterIndex++;
            }

            if (longueurMin != 0.0) {
                ps.setDouble(parameterIndex, longueurMin);
                parameterIndex++;
            }

            if (longueurMax != 0.0) {
                ps.setDouble(parameterIndex, longueurMax);
                parameterIndex++;
            }

            if (nbCoureurMin != 0.0) {
                ps.setDouble(parameterIndex, nbCoureurMin);
                parameterIndex++;
            }

            if (nbCoureurMax != 0.0) {
                ps.setDouble(parameterIndex, nbCoureurMax);
                parameterIndex++;
            }

            ResultSet rs = ps.executeQuery();

            Etape e;
            while (rs.next()) {
                e = new Etape();
                e.setId(rs.getString("id"));
                e.setNom(rs.getString("nom"));
                e.setLongueur(rs.getDouble("longueur"));
                e.setNbCoureurEquipe(rs.getInt("nb_coureur_equipe"));
                e.setRang(rs.getInt("rang"));
                e.setDateDepart(rs.getDate("date_depart"));
                e.setHeureDepart(rs.getTime("heure_depart"));
                etape.add(e);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etape;
    }

    public int countEtape() {
        int countEtape = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(id) as countEtape FROM etape";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countEtape = rs.getInt("countEtape");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countEtape;
    }

    public void deleteEtapesbyId(String id) {
        er.deleteById(id);
    }
}
