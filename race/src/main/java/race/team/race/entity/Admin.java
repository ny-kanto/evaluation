package race.team.race.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "admin")
public class Admin {
    @Id
    private String id;

    private String email;

    private String mdp;

    public void setId(IdGenerator idGenerator) {
        String id = "";
        id = idGenerator.generateId("ADM", "admin_seq");
        this.id = id;
    }
}
