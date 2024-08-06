package race.team.race.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import race.team.race.exception.MyException;
import race.team.race.service.DetailCoureurEtapeService;
import race.team.race.utils.IdGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detail_coureur_etape")
public class DetailCoureurEtape {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_etape", referencedColumnName = "id")
    private Etape etape;

    @ManyToOne
    @JoinColumn(name = "id_coureur", referencedColumnName = "id")
    private Coureur coureur;

    @Column(name = "date_heure_arrive", nullable = true)
    private Timestamp dateHeureArrive;

    @Transient
    private Time duree;

    public void setId(IdGenerator idGenerator) {
        String id = "";
        id = idGenerator.generateId("DECOETA", "detail_coureur_etape_seq");
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String dureeForm() {
        if(this.duree == null) {
            return "";
        } else {
            return this.duree.toString();
        }
    }

    public void verifDetailCoureurEtape(DetailCoureurEtapeService dces) throws MyException {
        if (dces.getDetailCoureurEtapesByEtapeAndCoureur(etape, coureur) != null) {
            throw new MyException("Cette coureur coure deja dans cette etape");
        }
    }

    public void verifDate(Timestamp depart) throws MyException {
        if (depart.compareTo(this.dateHeureArrive) >= 0) {
            throw new MyException("La date arrive doit etre apres la date depart : " + depart);
        }
    }
}