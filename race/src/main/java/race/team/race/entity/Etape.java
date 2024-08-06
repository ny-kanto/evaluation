package race.team.race.entity;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import race.team.race.exception.MyException;
import race.team.race.utils.IdGenerator;
import race.team.race.utils.Utils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "etape")
public class Etape {
    @Id
    private String id;

    private String nom;

    private double longueur;

    @Column(name = "nb_coureur_equipe")
    private int nbCoureurEquipe;

    private int rang;

    @Column(name = "date_depart")
    private Date dateDepart;

    @Column(name = "heure_depart")
    private Time heureDepart;

    public void setId(IdGenerator idGenerator) {
        String id = "";
        id = idGenerator.generateId("ETA", "etape_seq");
        this.id = id;
    }

    public void verifNbCoureur(int nb) throws MyException {
        if (nbCoureurEquipe <= nb) {
            throw new MyException("Coureur deja complet pour cette etape");
        }
    }

    public void verifNbCoureurInsert(int nbAo, int nb) throws MyException {
        if ((nbCoureurEquipe - nbAo) < nb) {
            throw new MyException("Vous depassez le nombre des coureurs pour cette etape. Nb coureur Max dispo : " + (nbCoureurEquipe - nbAo));
        }
    }

    public String longueurForm() {
        return Utils.getNombreFormat(longueur);
    }

    public void setLongueur(String longueur) throws MyException {
        if (Utils.isDecimal(longueur)) {
            this.longueur = Double.valueOf(longueur);
        } else {
            throw new MyException("Veuillez inserer un nombre");
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
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