package bg.medbook.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.medbook.model.entity.Doctor;
import bg.medbook.model.entity.User;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    public List<Doctor> findDistinctByMedicalField_IdAndWorkplaces_City_Id(Integer medicalField, Integer cityId);

    public List<Doctor> findDistinctByWorkplaces_City_Id(Integer cityId);

    public List<Doctor> findByMedicalField_Id(Integer medicalFieldId);

    public List<Doctor> findByUser_NameLikeIgnoreCase(String name);

    public Doctor findOneByUser(User user);

}
