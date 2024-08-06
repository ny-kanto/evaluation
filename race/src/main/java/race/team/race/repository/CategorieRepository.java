package race.team.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import race.team.race.entity.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, String> {
}
