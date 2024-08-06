package race.team.race.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import race.team.race.entity.Coureur;
import race.team.race.entity.DetailCoureurEtape;
import race.team.race.entity.Etape;

@Repository
public interface DetailCoureurEtapeRepository extends JpaRepository<DetailCoureurEtape, String> {
    List<DetailCoureurEtape> findByEtape(Etape etape);
    DetailCoureurEtape findByEtapeAndCoureur(Etape etape, Coureur coureur);
}
