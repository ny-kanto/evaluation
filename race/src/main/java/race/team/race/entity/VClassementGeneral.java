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
@Table(name = "v_classement_general")
public class VClassementGeneral {
    private String idCoureur;

    private String nomCoureur;

    private String idEquipe;

    private String nomEquipe;

    private double totalPoints;

    private int rang;

    public String totalPointsForm() {
        return Utils.getNombreFormat(totalPoints);
    }

    public void setTotalPoints(String totalPoints) throws MyException {
        if (Utils.isDecimal(totalPoints)) {
            this.totalPoints = Double.valueOf(totalPoints);
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

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }
}