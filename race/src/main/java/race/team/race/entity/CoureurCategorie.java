package race.team.race.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coureur_categorie")
public class CoureurCategorie {
    @ManyToOne
    @JoinColumn(name = "coureur", referencedColumnName = "id")
    private Coureur coureur;

    @ManyToOne
    @JoinColumn(name = "id_categorie", referencedColumnName = "id")
    private Categorie categorie;
}
