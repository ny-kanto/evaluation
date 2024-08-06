package race.team.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import race.team.race.entity.Equipe;



@Repository
public interface EquipeRepository extends JpaRepository<Equipe, String> {
    Equipe findByEmailAndMdp(String email, String mdp);
}

