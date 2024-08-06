package race.team.race.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "coureur")
public class Coureur {
    @Id
    private String id;

    private String nom;

    @Column(name = "numero_dossard")
    private int numeroDossard ;

    private int genre;

    @Column(name = "date_naissance")
    private Date dateNaissance;

    @ManyToOne
    @JoinColumn(name = "id_equipe", referencedColumnName = "id")
    private Equipe equipe;

    public void setId(IdGenerator idGenerator) {
        String id = "";
        id = idGenerator.generateId("COUR", "coureur_seq");
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

    public void setId(String id) {
        this.id = id;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public void setGenre(String genre) {
        if (genre.equals("M")) {
            this.genre = 1;
        } else {
            this.genre = 2;
        }
    }

    public String genreForm() {
        if (this.genre == 1) {
            return "Homme";
        } else {
            return "Femme";
        }
    }

    public int getGenre() {
        return this.genre;
    }

    public int getAge() {
        LocalDate now = LocalDate.now();

        Period age = Period.between(this.dateNaissance.toLocalDate(), now);
        return age.getYears();
    }

    public boolean verifAge() {
        if (this.getAge() >= 18) {
            return true;
        }
        return false;
    }
}