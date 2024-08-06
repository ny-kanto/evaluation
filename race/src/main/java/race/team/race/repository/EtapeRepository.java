package race.team.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import race.team.race.entity.Etape;

@Repository
public interface EtapeRepository extends JpaRepository<Etape, String> {
}
