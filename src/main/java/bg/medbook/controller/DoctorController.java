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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.medbook.model.dto.DoctorDto;
import bg.medbook.model.mapper.DoctorMapper;
import bg.medbook.service.DoctorService;

@RestController
@RequestMapping(value = "/doctors")
@Validated
public class DoctorController {
    private DoctorMapper mapper = DoctorMapper.INSTANCE;

    @Autowired
    private DoctorService service;

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getDoctors(@RequestParam(name = "cityId", required = false) Integer cityId,
	    @RequestParam(name = "fieldId", required = false) Integer medicalFieldId,
	    @RequestParam(name = "name", required = false) String name) {
	List<DoctorDto> doctors = service.findDoctors(cityId, medicalFieldId, name).stream().map(mapper::entityToDto)
		.collect(Collectors.toList());

	return ResponseEntity.ok(doctors);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable(value = "id") @Positive Integer id) {
	if (id == null) {
	    return ResponseEntity.badRequest().build();
	}
	try {
	    return ResponseEntity.ok(service.findById(id).map(mapper::entityToDto).get());
	} catch (NoSuchElementException e) {
	    return ResponseEntity.notFound().build();
	}
    }

    @PostMapping
    public ResponseEntity<Integer> createDoctor(@RequestBody @Valid DoctorDto doctor) {
	try {
	    service.create(mapper.dtoToEntity(doctor));
	    return ResponseEntity.status(HttpStatus.CREATED).build();
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
