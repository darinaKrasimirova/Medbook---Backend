package bg.medbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.medbook.model.entity.Appointment;
import bg.medbook.model.entity.Doctor;
import bg.medbook.model.entity.User;
import bg.medbook.model.enumeration.UserAccountType;
import bg.medbook.model.repository.AppointmentRepository;
import bg.medbook.model.repository.UserRepository;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorService doctorService;

    public AppointmentService(@Autowired AppointmentRepository appointmentRepository) {
	this.appointmentRepository = appointmentRepository;
    }

    public void createAppointment(Appointment appointment) {
	User user = userRepository.findByUsername(appointment.getPatient().getUsername()).orElseThrow();
	appointment.setPatient(user);
	appointment = appointmentRepository.save(appointment);
    }

    public List<Appointment> findByUser(Integer userId) {
	User user = userRepository.findById(userId).orElseThrow();
	if (user.getAccountType() == UserAccountType.PATIENT) {
	    return appointmentRepository.findByPatient(user);
	}
	Doctor doctor = doctorService.getDoctorForUser(user);
	return appointmentRepository.findByDoctor(doctor);
    }

}
