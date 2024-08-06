package race.team.race.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import race.team.race.exception.MyException;
import race.team.race.utils.Utils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "v_points_coureur_etape")
public class VPointsCoureurEtape {
    private String idEtape;
    
    private String idCoureur;

    private String nomCoureur;

    private String genre;

    private String idEquipe;

    private String nomEquipe;

    private String duree;

    private String penalite;

    private String tempsFinal;

    private double point;

    private int rang;

    public String pointForm() {
        return Utils.getNombreFormat(point);
    }

    public void setPoint(String point) throws MyException {
        if (Utils.isDecimal(point)) {
            this.point = Double.valueOf(point);
        } else {
            throw new MyException("Veuillez inserer un nombre");
        }
    }

    public String getNomCoureur() {
        return Utils.capitalizeFirstLetter(nomCoureur);
    }

    public void setNomCoureur(String nomCoureur) {
        String cleanNom = nomCoureur.trim();
        cleanNom = cleanNom.replaceAll("\\s+", " ");
        this.nomCoureur = cleanNom.toLowerCase();
    }

    public String getNomEquipe() {
        return Utils.capitalizeFirstLetter(nomEquipe);
    }

    public void setNomEquipe(String nomEquipe) {
        String cleanNom = nomEquipe.trim();
        cleanNom = cleanNom.replaceAll("\\s+", " ");
        this.nomEquipe = cleanNom.toLowerCase();
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public void setGenre (int genre) {
        if (genre == 1) {
            this.genre = "Homme";
        } else {
            this.genre = "Femme";
        }
    }
}