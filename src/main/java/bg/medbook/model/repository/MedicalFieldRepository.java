package bg.medbook.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.medbook.model.entity.MedicalField;

@Repository
public interface MedicalFieldRepository extends JpaRepository<MedicalField, Integer> {

}
