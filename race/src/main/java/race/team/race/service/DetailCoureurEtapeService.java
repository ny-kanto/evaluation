package race.team.race.service;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Locale;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import race.team.race.entity.Coureur;
import race.team.race.entity.DetailCoureurEtape;
import race.team.race.entity.Etape;
import race.team.race.repository.CoureurRepository;
import race.team.race.repository.DetailCoureurEtapeRepository;
import race.team.race.repository.EtapeRepository;

@Service
public class DetailCoureurEtapeService {
    @Autowired
    private DetailCoureurEtapeRepository dcer;

    @Autowired
    private EtapeRepository er;

    @Autowired
    private CoureurRepository cr;

    @Autowired
    private DataSource dataSource;

    public DetailCoureurEtape insererDetailCoureurEtape(DetailCoureurEtape dce) {
        return dcer.save(dce);
    }

    public List<DetailCoureurEtape> getDetailCoureurEtapes() {
        return dcer.findAll();
    }

    public DetailCoureurEtape getDetailCoureurEtapeById(String id) {
        return dcer.findById(id).get();
    }

    public DetailCoureurEtape getDetailCoureurEtapesByEtapeAndCoureur(Etape etape, Coureur coureur) {
        return dcer.findByEtapeAndCoureur(etape, coureur);
    }

    public List<DetailCoureurEtape> getDetailCoureurEtapesByEtape(Etape etape) {
        return dcer.findByEtape(etape);
    }

    // RESULTAT CSV TEMPORAIRE
    public void importCsv(String tableName) {
        try {
            Connection c = dataSource.getConnection();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.ENGLISH);

            String sql = "SELECT \r\n" + //
                    "    'DECOETA' || LPAD(nextval('detail_coureur_etape_seq')::text, 4, '0') AS generated_id, \r\n" + //
                    "    temp.id_etape, \r\n" + //
                    "    temp.id_coureur, \r\n" + //
                    "    temp.date_heure_arrivee \r\n" + //
                    "FROM (\r\n" + //
                    "    SELECT \r\n" + //
                    "    e.id AS id_etape,\r\n" + //
                    "    c.id AS id_coureur,\r\n" + //
                    "    arrivee AS date_heure_arrivee\r\n" + //
                    "       FROM \r\n" + //
                    "     " + tableName + " rt \r\n"
                    + //
                    "   JOIN \r\n" + //
                    "    etape e ON rt.etape_rang = e.rang || '' \r\n" + //
                    "   JOIN \r\n" + //
                    "    coureur c ON rt.numero_dossard = c.numero_dossard || '' \r\n" + //
                    ") AS temp\r\n" + //
                    "";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DetailCoureurEtape dce;
            Coureur coureur;
            Etape e;
            while (rs.next()) {
                e = er.findById(rs.getString("id_etape")).get();
                coureur = cr.findById(rs.getString("id_coureur")).get();
                dce = new DetailCoureurEtape();
                dce.setEtape(e);
                dce.setCoureur(coureur);
                dce.setDateHeureArrive(new Timestamp(dateFormat.parse(rs.getString("date_heure_arrivee")).getTime()));
                dce.setId(rs.getString("generated_id"));
                dcer.save(dce);
            }
            rs.close();
            ps.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DetailCoureurEtape> getListDetailByEquipe(String idEquipe) {
        List<DetailCoureurEtape> detailCoureurEtapes = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();

            String sql = "SELECT *, (dce.date_heure_arrive - (et.date_depart + et.heure_depart)) AS duree FROM detail_coureur_etape dce JOIN etape et ON et.id = dce.id_etape JOIN coureur c ON c.id = dce.id_coureur JOIN equipe e ON e.id = c.id_equipe WHERE e.id = ? ";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idEquipe);
            ResultSet rs = ps.executeQuery();

            DetailCoureurEtape dce;
            Coureur coureur;
            Etape e;
            while (rs.next()) {
                e = er.findById(rs.getString("id_etape")).get();
                coureur = cr.findById(rs.getString("id_coureur")).get();
                dce = new DetailCoureurEtape();
                dce.setEtape(e);
                dce.setCoureur(coureur);
                dce.setDateHeureArrive(rs.getTimestamp("date_heure_arrive"));
                dce.setDuree(rs.getTime("duree"));
                dce.setId(rs.getString("id"));
                detailCoureurEtapes.add(dce);
            }
            rs.close();
            ps.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return detailCoureurEtapes;
    }

    public int countDetail(String idEtape, String idEquipe) {
        int countDetail = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(*) as countDetail FROM detail_coureur_etape dce JOIN coureur c ON c.id = dce.id_coureur JOIN equipe e ON e.id = c.id_equipe WHERE id_etape = ? AND id_equipe = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idEtape);
            ps.setString(2, idEquipe);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countDetail = rs.getInt("countDetail");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countDetail;
    }
}
