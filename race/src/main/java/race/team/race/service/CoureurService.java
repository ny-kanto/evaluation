package race.team.race.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import race.team.race.entity.Coureur;
import race.team.race.entity.Equipe;
import race.team.race.repository.CoureurRepository;
import race.team.race.repository.EquipeRepository;

@Service
public class CoureurService {
    @Autowired
    private CoureurRepository cr;

    @Autowired
    private EquipeRepository er;

    @Autowired
    private DataSource dataSource;

    public Coureur insererCoureur(Coureur coureur) {
        return cr.save(coureur);
    }

    public List<Coureur> getCoureurs() {
        return cr.findAll();
    }

    public Coureur getCoureurById(String id) {
        return cr.findById(id).get();
    }

    public List<Coureur> getCoureurByEquipe(Equipe equipe) {
        return cr.findByEquipe(equipe);
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    // RESULTAT CSV TEMPORAIRE
    public void importCsv(String tableName) {
        try {
            Connection c = dataSource.getConnection();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            String sql = "SELECT \r\n" + //
                                "    'COUR' || LPAD(nextval('coureur_seq')::text, 4, '0') AS generated_id, \r\n" + //
                                "    temp.numero_dossard, \r\n" + //
                                "    temp.nom, \r\n" + //
                                "    temp.genre, \r\n" + //
                                "    temp.date_naissance, \r\n" + //
                                "    temp.id_equipe \r\n" + //
                                "FROM (\r\n" + //
                                "    SELECT DISTINCT ON (rt.numero_dossard) \r\n" + //
                                "        rt.numero_dossard, \r\n" + //
                                "        rt.nom, \r\n" + //
                                "        rt.genre, \r\n" + //
                                "        rt.date_naissance, \r\n" + //
                                "        e.id AS id_equipe \r\n" + //
                                "    FROM \r\n" + //
                                "     " + tableName + " rt \r\n"
                                + //
                                "    JOIN \r\n" + //
                                "        equipe e ON e.nom = LOWER(rt.equipe) \r\n" + //
                                "    ORDER BY \r\n" + //
                                "        rt.numero_dossard\r\n" + //
                                ") AS temp\r\n" + //
                                "";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Coureur coureur;
            Equipe e;
            while (rs.next()) {
                e = er.findById(rs.getString("id_equipe")).get();
                coureur = new Coureur();
                coureur.setId(rs.getString("generated_id"));
                coureur.setNom(rs.getString("nom"));
                coureur.setNumeroDossard(rs.getInt("numero_dossard"));
                coureur.setGenre(rs.getString("genre"));
                coureur.setDateNaissance(new Date(dateFormat.parse(rs.getString("date_naissance")).getTime()));
                coureur.setEquipe(e);
                cr.save(coureur);
            }

            rs.close();
            ps.close();
            c.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Coureur> getCoureurPerPage(int noPage, int nbParPage, String column, int sort) {
        List<Coureur> coureur = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM coureur ORDER BY " + column + " " + ((sort % 2) == 1 ? "ASC" : "DESC")
                    + " LIMIT ? OFFSET ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, nbParPage);
            ps.setInt(2, (noPage - 1) * nbParPage);
            ResultSet rs = ps.executeQuery();

            Coureur l;
            while (rs.next()) {
                l = new Coureur();
                l.setId(rs.getString("id"));
                l.setNom(rs.getString("nom"));
                l.setNumeroDossard(rs.getInt("numero_dossard"));
                l.setGenre(rs.getInt("genre"));
                l.setDateNaissance(rs.getDate("date_naissance"));
                coureur.add(l);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coureur;
    }

    public List<Coureur> getCoureurByEquipeNotInEtape(String idEquipe, String idEtape) {
        List<Coureur> coureur = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM coureur WHERE id_equipe = ? AND id NOT IN (SELECT id_coureur FROM detail_coureur_etape WHERE id_etape = ?)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idEquipe);
            ps.setString(2, idEtape);
            ResultSet rs = ps.executeQuery();

            Coureur l;
            while (rs.next()) {
                l = new Coureur();
                l.setId(rs.getString("id"));
                l.setNom(rs.getString("nom"));
                l.setNumeroDossard(rs.getInt("numero_dossard"));
                l.setGenre(rs.getInt("genre"));
                l.setDateNaissance(rs.getDate("date_naissance"));
                coureur.add(l);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coureur;
    }

    public List<Coureur> getCoureurFiltre(String nom) {
        nom = nom.toLowerCase();
        List<Coureur> coureur = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM coureur WHERE nom LIKE ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();

            Coureur l;
            while (rs.next()) {
                l = new Coureur();
                l.setId(rs.getString("id"));
                l.setNom(rs.getString("nom"));
                l.setNumeroDossard(rs.getInt("numero_dossard"));
                l.setGenre(rs.getInt("genre"));
                l.setDateNaissance(rs.getDate("date_naissance"));
                coureur.add(l);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coureur;
    }

    public int countCoureur() {
        int countCoureur = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(id) as countCoureur FROM coureur";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countCoureur = rs.getInt("countCoureur");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countCoureur;
    }

    public void deleteCoureursbyId(String id) {
        cr.deleteById(id);
    }
}
