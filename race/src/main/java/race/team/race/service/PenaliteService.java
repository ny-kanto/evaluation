package race.team.race.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import race.team.race.entity.Equipe;
import race.team.race.entity.Etape;
import race.team.race.entity.Penalite;
import race.team.race.repository.EquipeRepository;
import race.team.race.repository.EtapeRepository;
import race.team.race.repository.PenaliteRepository;

@Service
public class PenaliteService {
    @Autowired
    private PenaliteRepository pr;

    @Autowired
    private EtapeRepository etr;

    @Autowired
    private EquipeRepository eqr;

    @Autowired
    private DataSource dataSource;

    public void insererPenalite(String idEtape, String idEquipe, long heure, long minute, long seconde) {
        try {
            Connection c = dataSource.getConnection();
            String temps = String.format("%02d:%02d:%02d", heure, minute, seconde);

            String sql = "INSERT INTO penalite (id, id_etape, id_equipe, temps) VALUES ('PEN' || LPAD(nextval('penalite_seq')::text, 4, '0'), ?, ?, CAST (? AS INTERVAL))";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, idEtape);
            ps.setString(2, idEquipe);
            ps.setString(3, temps);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Penalite> getPenalites() {
        List<Penalite> penalites = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();

            String sql = "SELECT * FROM penalite";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Penalite p;
            Etape et;
            Equipe equipe;
            while (rs.next()) {
                et = etr.findById(rs.getString("id_etape")).get();
                equipe = eqr.findById(rs.getString("id_equipe")).get();
                p = new Penalite();
                p.setEquipe(equipe);
                p.setEtape(et);
                p.setTemps(rs.getString("temps"));
                penalites.add(p);
            }
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return penalites;
    }

    public Penalite getPenaliteById(String id) {
        Penalite p = null;
        try {
            Connection c = dataSource.getConnection();

            String sql = "SELECT * FROM penalite WHERE id = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            Etape et;
            Equipe equipe;
            if (rs.next()) {
                et = etr.findById(rs.getString("id_etape")).get();
                equipe = eqr.findById(rs.getString("id_equipe")).get();
                p = new Penalite();
                p.setEquipe(equipe);
                p.setEtape(et);
                p.setTemps(rs.getString("temps"));
            }
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return p;
    }

    public void deletePenalitesbyId(String id) {
        pr.deleteById(id);
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public List<Penalite> getPenalitePerPage(int noPage, int nbParPage, String column, int sort) {
        List<Penalite> penalite = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM penalite ORDER BY " + column + " " + ((sort % 2) == 1 ? "ASC" : "DESC")
                    + " LIMIT ? OFFSET ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, nbParPage);
            ps.setInt(2, (noPage - 1) * nbParPage);
            ResultSet rs = ps.executeQuery();

            Penalite e;
            Etape et;
            Equipe equipe;
            while (rs.next()) {
                et = etr.findById(rs.getString("id_etape")).get();
                equipe = eqr.findById(rs.getString("id_equipe")).get();
                e = new Penalite();
                e.setEtape(et);
                e.setEquipe(equipe);
                e.setTemps(rs.getString("temps"));
                e.setId(rs.getString("id"));
                penalite.add(e);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penalite;
    }

    public String sql(String idEtape, String idEquipe) {
        String sql = "SELECT * FROM penalite WHERE 1 = 1 ";

        if (!idEtape.equals("")) {
            sql += "AND id_etape = ? ";
        }

        if (!idEquipe.equals("")) {
            sql += "AND id_coureur = ? ";
        }
        return sql;
    }

    public List<Penalite> getPenaliteFiltre(String idEtape, String idEquipe) {
        List<Penalite> penalite = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = sql(idEtape, idEquipe);

            PreparedStatement ps = c.prepareStatement(sql);
            int parameterIndex = 1;

            if (!idEtape.equals("")) {
                ps.setString(parameterIndex, idEtape);
                parameterIndex++;
            }

            if (!idEquipe.equals("")) {
                ps.setString(parameterIndex, idEquipe);
                parameterIndex++;
            }

            ResultSet rs = ps.executeQuery();

            Penalite p;
            Etape et;
            Equipe equipe;
            while (rs.next()) {
                et = etr.findById(rs.getString("id_etape")).get();
                equipe = eqr.findById(rs.getString("id_equipe")).get();
                p = new Penalite();
                p.setEtape(et);
                p.setEquipe(equipe);
                p.setTemps("");
                p.setId(rs.getString("id"));
                penalite.add(p);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penalite;
    }

    public int countPenalite() {
        int countPenalite = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(id) as countPenalite FROM penalite";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countPenalite = rs.getInt("countPenalite");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countPenalite;
    }
}
