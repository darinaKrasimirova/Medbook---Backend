package bg.medbook.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.medbook.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
