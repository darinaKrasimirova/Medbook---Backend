package bg.medbook.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.medbook.model.entity.Workplace;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Integer> {

}
