package race.team.race.service;

import java.util.List;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import race.team.race.entity.Equipe;
import race.team.race.repository.EquipeRepository;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository er;

    @Autowired
    private DataSource dataSource;

    public Equipe insererEquipe(Equipe equipe) {
        return er.save(equipe);
    }

    public Equipe getEquipe(String email, String mdp) {
        return er.findByEmailAndMdp(email, mdp);
    }

    public Equipe getEquipeById(String id) {
        return er.findById(id).get();
    }

    public List<Equipe> getEquipes() {
        return er.findAll();
    }

    // RESULTAT CSV TEMPORAIRE
    public void importCsv(String tableName) {
        try {
            Connection c = dataSource.getConnection();
            
            String sql = "SELECT 'EQUI' || LPAD(nextval('equipe_seq')::text, 4, '0') AS generated_id, equipe FROM (SELECT DISTINCT equipe FROM " + tableName + ") AS temp";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Equipe equipe;
            while (rs.next()) {
                equipe = new Equipe();
                equipe.setId(rs.getString("generated_id"));
                equipe.setNom(rs.getString("equipe"));
                equipe.setEmail(equipe.getNom().toLowerCase());
                equipe.setMdp(equipe.getNom().toLowerCase());
                er.save(equipe);
            }
            rs.close();
            ps.close();
            c.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
