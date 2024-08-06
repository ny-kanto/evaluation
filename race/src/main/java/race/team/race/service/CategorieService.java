package race.team.race.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import race.team.race.entity.Categorie;
import race.team.race.repository.CategorieRepository;

@Service
public class CategorieService {
    @Autowired
    private CategorieRepository cr;

    public Categorie insererCategorie(Categorie categorie) {
        return cr.save(categorie);
    }

    public List<Categorie> getCategories() {
        return cr.findAll();
    }

    public Categorie getCategorieById(String id) {
        return cr.findById(id).get();
    }
}
