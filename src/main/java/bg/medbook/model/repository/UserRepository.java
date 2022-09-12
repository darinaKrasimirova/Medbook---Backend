package bg.medbook.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.medbook.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findOneByUsername(String username);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
