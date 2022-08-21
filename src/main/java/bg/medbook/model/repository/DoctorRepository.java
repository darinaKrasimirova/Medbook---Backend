package bg.medbook.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import bg.medbook.model.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    public List<Doctor> findByMedicalField_IdAndWorkplaces_City_Id(Integer medicalField,
            Integer cityId);

    public List<Doctor> findByWorkplaces_City_Id(Integer cityId);

    public List<Doctor> findByMedicalField_Id(Integer medicalFieldId);

    public List<Doctor> findByUser_NameLikeIgnoreCase(String name);

}
