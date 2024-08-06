package race.team.race.entity;

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
@Table(name = "point_classement")
public class PointClassement {
    @Id
    private String id;

    // @ManyToOne
    // @JoinColumn(name = "id_etape", referencedColumnName = "id")
    // private Etape etape;

    private int rang;

    private double point;

    public void setId(IdGenerator idGenerator) {
        String id = "";
        id = idGenerator.generateId("POCLA", "point_classement_seq");
        this.id = id;
    }

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
}
