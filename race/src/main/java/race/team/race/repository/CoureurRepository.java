package race.team.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import race.team.race.entity.Coureur;
import race.team.race.entity.Equipe;

@Repository
public interface CoureurRepository extends JpaRepository<Coureur, String> {
    List<Coureur> findByEquipe(Equipe equipe);
}
