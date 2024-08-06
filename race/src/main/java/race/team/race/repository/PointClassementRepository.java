package race.team.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import race.team.race.entity.PointClassement;

@Repository
public interface PointClassementRepository extends JpaRepository<PointClassement, String> {
}
