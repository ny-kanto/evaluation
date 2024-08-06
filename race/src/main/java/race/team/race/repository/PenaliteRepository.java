package race.team.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import race.team.race.entity.Penalite;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, String> {
}
