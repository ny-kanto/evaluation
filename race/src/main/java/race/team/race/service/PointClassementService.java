package race.team.race.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.io.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

import race.team.race.entity.PointClassement;
import race.team.race.entity.VClassementGeneral;
import race.team.race.entity.VPointsCoureurEtape;
import race.team.race.repository.PointClassementRepository;
import race.team.race.utils.IdGenerator;

@Service
public class PointClassementService {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PointClassementRepository pcr;

    @Autowired
    private IdGenerator idGenerator;

    public void importCsvPoints(String csvFile) {
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            // Ignorer la première ligne qui contient les en-têtes
            reader.readNext();

            PointClassement pointClassement;
            while ((line = reader.readNext()) != null) {
                pointClassement = new PointClassement();
                pointClassement.setRang(Integer.valueOf(line[0]));
                pointClassement.setPoint(line[1]);
                pointClassement.setId(idGenerator);

                pcr.save(pointClassement);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VClassementGeneral> getClassementPerPage(int noPage, int nbParPage, String column, int sort) {
        List<VClassementGeneral> classement = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM v_classement_general ORDER BY " + column + " " + ((sort % 2) == 1 ? "ASC" : "DESC")
                    + " LIMIT ? OFFSET ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, nbParPage);
            ps.setInt(2, (noPage - 1) * nbParPage);
            ResultSet rs = ps.executeQuery();

            VClassementGeneral vcg;
            while (rs.next()) {
                vcg = new VClassementGeneral();
                vcg.setIdCoureur(rs.getString("id_coureur"));
                vcg.setNomCoureur(rs.getString("nom_coureur"));
                vcg.setIdEquipe(rs.getString("id_equipe"));
                vcg.setNomEquipe(rs.getString("nom_equipe"));
                vcg.setTotalPoints(rs.getDouble("total_points"));
                vcg.setRang(rs.getInt("rang"));
                classement.add(vcg);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classement;
    }

    public String sql(String nomCoureur, String idEquipe, int rangMin, int rangMax) {
        String sql = "SELECT * FROM v_classement_general WHERE 1 = 1 ";

        if (!nomCoureur.equals("")) {
            sql += "AND nom_coureur LIKE ? ";
        }

        if (!idEquipe.equals("")) {
            sql += "AND id_equipe = ? ";
        }

        if (rangMin != 0.0) {
            sql += "AND rang >= ? ";
        }

        if (rangMax != 0.0) {
            sql += "AND rang <= ? ";
        }
        return sql;
    }

    public List<VClassementGeneral> getClassementFiltre(String nomCoureur, String idEquipe, int nbCoureurMin, int nbCoureurMax) {
        List<VClassementGeneral> classement = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = sql(nomCoureur, idEquipe, nbCoureurMin, nbCoureurMax);

            PreparedStatement ps = c.prepareStatement(sql);
            int parameterIndex = 1;

            if (!nomCoureur.equals("")) {
                ps.setString(parameterIndex, "%" + nomCoureur + "%");
                parameterIndex++;
            }

            if (!idEquipe.equals("")) {
                ps.setString(parameterIndex, idEquipe);
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

            VClassementGeneral vcg;
            while (rs.next()) {
                vcg = new VClassementGeneral();
                vcg.setIdCoureur(rs.getString("id_coureur"));
                vcg.setNomCoureur(rs.getString("nom_coureur"));
                vcg.setIdEquipe(rs.getString("id_equipe"));
                vcg.setNomEquipe(rs.getString("nom_equipe"));
                vcg.setTotalPoints(rs.getDouble("total_points"));
                vcg.setRang(rs.getInt("rang"));
                classement.add(vcg);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classement;
    }

    public int countClassement() {
        int countClassement = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(*) as countClassement FROM v_classement_general";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countClassement = rs.getInt("countClassement");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countClassement;
    }


    public List<VPointsCoureurEtape> getClassementEtape(String idEtape) {
        List<VPointsCoureurEtape> classementEtape = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM v_points_coureur_etape WHERE id_etape = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idEtape);
            ResultSet rs = ps.executeQuery();

            VPointsCoureurEtape vpce;
            while (rs.next()) {
                vpce = new VPointsCoureurEtape();
                vpce.setIdEtape(rs.getString("id_etape"));
                vpce.setIdCoureur(rs.getString("id_coureur"));
                vpce.setNomCoureur(rs.getString("nom_coureur"));
                vpce.setIdEquipe(rs.getString("id_equipe"));
                vpce.setNomEquipe(rs.getString("nom_equipe"));
                vpce.setPoint(rs.getDouble("point"));
                vpce.setDuree(rs.getString("duree"));
                vpce.setRang(rs.getInt("rang"));
                classementEtape.add(vpce);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classementEtape;
    }

    public List<VClassementGeneral> getClassementEtapeEquipe(String idEquipe) {
        List<VClassementGeneral> classementEtape = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM v_classement_general WHERE id_equipe = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idEquipe);
            ResultSet rs = ps.executeQuery();

            VClassementGeneral vcg;
            while (rs.next()) {
                vcg = new VClassementGeneral();
                vcg.setIdCoureur(rs.getString("id_coureur"));
                vcg.setNomCoureur(rs.getString("nom_coureur"));
                vcg.setIdEquipe(rs.getString("id_equipe"));
                vcg.setNomEquipe(rs.getString("nom_equipe"));
                vcg.setTotalPoints(rs.getDouble("total_points"));
                classementEtape.add(vcg);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classementEtape;
    }

    public List<VPointsCoureurEtape> getClassementEtapeJ(String idEtape) {
        List<VPointsCoureurEtape> classementEtape = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM v_points_coureur_etape_j WHERE id_etape = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idEtape);
            ResultSet rs = ps.executeQuery();

            VPointsCoureurEtape vpce;
            while (rs.next()) {
                vpce = new VPointsCoureurEtape();
                vpce.setIdEtape(rs.getString("id_etape"));
                vpce.setIdCoureur(rs.getString("id_coureur"));
                vpce.setNomCoureur(rs.getString("nom_coureur"));
                vpce.setGenre(rs.getInt("genre_coureur"));
                vpce.setIdEquipe(rs.getString("id_equipe"));
                vpce.setNomEquipe(rs.getString("nom_equipe"));
                vpce.setPoint(rs.getDouble("point"));
                vpce.setDuree(rs.getString("chrono"));
                vpce.setPenalite(rs.getString("penalite"));
                vpce.setTempsFinal(rs.getString("temps_final"));
                vpce.setRang(rs.getInt("rang"));
                classementEtape.add(vpce);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classementEtape;
    }

    public List<VPointsCoureurEtape> getClassementEquipeCategorie(String idCategorie) {
        List<VPointsCoureurEtape> classementEtape = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT\n" + //
                                "    vpcec.id_equipe,\n" + //
                                "    vpcec.nom_equipe,\n" + //
                                "    vpcec.id_categorie,\n" + //
                                "    SUM(vpcec.point) AS total_points,\n" + //
                                "    DENSE_RANK() OVER (ORDER BY SUM(vpcec.point) DESC) AS rang\n" + //
                                "FROM\n" + //
                                "    v_points_coureur_etape_categorie vpcec\n" + //
                                "WHERE id_categorie = ?\n" + //
                                "GROUP BY\n" + //
                                "    vpcec.id_equipe, vpcec.nom_equipe, vpcec.id_categorie\n" + //
                                "ORDER BY\n" + //
                                "    total_points DESC;";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idCategorie);
            ResultSet rs = ps.executeQuery();

            VPointsCoureurEtape vpce;
            while (rs.next()) {
                vpce = new VPointsCoureurEtape();
                vpce.setIdEquipe(rs.getString("id_equipe"));
                vpce.setNomEquipe(rs.getString("nom_equipe"));
                vpce.setPoint(rs.getDouble("total_points"));
                vpce.setRang(rs.getInt("rang"));
                classementEtape.add(vpce);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classementEtape;
    }

    public List<VPointsCoureurEtape> getClassementEquipe() {
        List<VPointsCoureurEtape> classementEtape = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM v_rang_equipe";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            VPointsCoureurEtape vpce;
            while (rs.next()) {
                vpce = new VPointsCoureurEtape();
                vpce.setIdEquipe(rs.getString("id_equipe"));
                vpce.setNomEquipe(rs.getString("nom_equipe"));
                vpce.setPoint(rs.getDouble("point"));
                vpce.setRang(rs.getInt("rang"));
                classementEtape.add(vpce);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classementEtape;
    }
}
