package bg.medbook.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.medbook.model.entity.Appointment;
import bg.medbook.model.entity.Doctor;
import bg.medbook.model.entity.User;
import bg.medbook.model.entity.Workplace;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByWorkplaceAndAppointmentTimeBetween(Workplace workplace, LocalDateTime atStartOfDay,
	    LocalDateTime atTime);

    List<Appointment> findByPatient(User parient);

    List<Appointment> findByDoctor(Doctor doctor);
}
