package race.team.race.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import race.team.race.utils.IdGenerator;
import race.team.race.utils.Utils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorie")
public class Categorie {
    @Id
    private String id;

    private String nom;

    public void setId(IdGenerator idGenerator) {
        String id = "";
        id = idGenerator.generateId("CAT", "categorie_seq");
        this.id = id;
    }    

    public String getNom() {
        return Utils.capitalizeFirstLetter(nom);
    }

    public void setNom(String nom) {
        String cleanNom = nom.trim();
        cleanNom = cleanNom.replaceAll("\\s+", " ");
        this.nom = cleanNom.toLowerCase();
    }
}