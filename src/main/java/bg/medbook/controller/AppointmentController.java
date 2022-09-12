package bg.medbook.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.medbook.model.dto.AppointmentDto;
import bg.medbook.model.entity.Appointment;
import bg.medbook.model.mapper.AppointmentMapper;
import bg.medbook.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
@Validated
public class AppointmentController {
    private AppointmentMapper mapper = AppointmentMapper.INSTANCE;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Void> createAppointment(@RequestBody @Valid AppointmentDto dto) {
	appointmentService.createAppointment(mapper.dtoToEntity(dto));
	return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointmentsForUser(
	    @RequestParam(name = "userId") @Positive Integer userId) {
	try {
	    List<Appointment> res = appointmentService.findByUser(userId);
	    List<AppointmentDto> appointments = res.stream().map(mapper::entityToDto).collect(Collectors.toList());
	    return ResponseEntity.ok(appointments);
	} catch (NoSuchElementException e) {
	    return ResponseEntity.notFound().build();
	}
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleConstraintViolationException(Exception objException) {
	return objException.getMessage();
    }
}
