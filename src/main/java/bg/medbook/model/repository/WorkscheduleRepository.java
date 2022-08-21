package bg.medbook.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.medbook.model.entity.Workschedule;

@Repository
public interface WorkscheduleRepository extends JpaRepository<Workschedule, Integer> {

}
