package race.team.race.service;

import java.util.List;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

import java.sql.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import race.team.race.entity.Admin;
import race.team.race.entity.Categorie;
import race.team.race.entity.Coureur;
import race.team.race.repository.AdminRepository;
import race.team.race.repository.CategorieRepository;
import race.team.race.repository.CoureurRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository ar;

    @Autowired
    private CoureurRepository cor;

    @Autowired
    private CategorieRepository cr;

    @Autowired
    private DataSource dataSource;

    public Admin insererAdmin(Admin admin) {
        return ar.save(admin);
    }

    public Admin getAdmin(String email, String mdp) {
        return ar.findByEmailAndMdp(email, mdp);
    }

    public void generateCategory() {
        try {
            Connection connection = dataSource.getConnection();
            List<Coureur> coureurs = cor.findAll();
            List<Categorie> categories = cr.findAll();

            String sql = "INSERT INTO coureur_categorie(id_coureur, id_categorie) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            String idCoureur = "";
            boolean verifAge = false;
            String idCategorie = "";
            for (int index = 0; index < coureurs.size(); index++) {
                idCoureur = coureurs.get(index).getId();
                verifAge = coureurs.get(index).verifAge();

                for (int i = 0; i < categories.size(); i++) {
                    idCategorie = categories.get(i).getId();
                    if (coureurs.get(index).getGenre() == 1) {
                        if (idCategorie.equals("CAT0001")) {
                            ps.setString(1, idCoureur);
                            ps.setString(2, idCategorie);
                            ps.executeUpdate();
                        }
                    } else if (coureurs.get(index).getGenre() == 2) {
                        if (idCategorie.equals("CAT0002")) {
                            ps.setString(1, idCoureur);
                            ps.setString(2, idCategorie);
                            ps.executeUpdate();
                        }
                    }
                    if (!verifAge) {
                        if (idCategorie.equals("CAT0003")) {
                            ps.setString(1, idCoureur);
                            ps.setString(2, idCategorie);
                            ps.executeUpdate();
                        }
                    }
                }
            }

            ps.close();
            connection.close();
        } catch (Exception e) {
        }
    }
}
