package race.team.race.entity;

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
import race.team.race.utils.IdGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "penalite")
public class Penalite {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_etape", referencedColumnName = "id")
    private Etape etape;

    @ManyToOne
    @JoinColumn(name = "id_equipe", referencedColumnName = "id")
    private Equipe equipe;

    @Transient
    private String temps;

    public void setId(IdGenerator idGenerator) {
        String id = "";
        id = idGenerator.generateId("PEN", "penalite_seq");
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
