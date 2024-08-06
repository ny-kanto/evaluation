package race.team.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import race.team.race.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByEmailAndMdp(String email, String mdp);
}
