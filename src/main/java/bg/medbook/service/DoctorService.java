package bg.medbook.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.medbook.model.entity.Doctor;
import bg.medbook.model.entity.User;
import bg.medbook.model.enumeration.UserAccountStatus;
import bg.medbook.model.repository.DoctorRepository;
import bg.medbook.model.repository.UserRepository;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public DoctorService(DoctorRepository repo) {
	doctorRepository = repo;
    }

    public List<Doctor> findDoctors(Integer cityId, Integer medicalFieldId, String name) {
	if (cityId != null && medicalFieldId != null) {
	    return doctorRepository.findDistinctByMedicalField_IdAndWorkplaces_City_Id(medicalFieldId, cityId);
	} else if (cityId != null) {
	    return doctorRepository.findDistinctByWorkplaces_City_Id(cityId);
	} else if (medicalFieldId != null) {
	    return doctorRepository.findByMedicalField_Id(medicalFieldId);
	} else {
	    return doctorRepository
		    .findByUser_NameLikeIgnoreCase("%" + StringUtils.defaultString(name).toLowerCase() + "%");
	}
    }

    public Optional<Doctor> findById(Integer id) {
	return doctorRepository.findById(id);
    }

    @Transactional
    public void create(Doctor doctor) {
	doctor.getWorkplaces().forEach(wp -> {
	    wp.setDoctor(doctor);
	    wp.getWorkschedules().forEach(ws -> ws.setWorkplace(wp));
	});

	User user = userRepository.findByUsername(doctor.getUser().getUsername()).orElseThrow();
	user.setAccountStatus(UserAccountStatus.ACTIVE);
	userRepository.save(user);
	doctor.setUser(user);
	doctorRepository.save(doctor);
    }

    public Doctor getDoctorForUser(User user) {
	return doctorRepository.findOneByUser(user);
    }
}
