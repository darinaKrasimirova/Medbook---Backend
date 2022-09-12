package bg.medbook.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.medbook.model.dto.TimeSlotsPerDate;
import bg.medbook.model.dto.WorkplaceDto;
import bg.medbook.model.mapper.WorkplaceMapper;
import bg.medbook.service.Constants;
import bg.medbook.service.WorkplaceService;

@RestController
@RequestMapping("/workplaces")
public class WorkplaceController {

    private WorkplaceMapper mapper = WorkplaceMapper.INSTANCE;
    @Autowired
    private WorkplaceService service;

    @GetMapping(path = "/{id}/timeSlots")
    public ResponseEntity<List<TimeSlotsPerDate>> getTimeSlots(@PathVariable(value = "id") Integer id,
	    @RequestParam(name = "rangeBeginning") String dateStart, @RequestParam(name = "rangeEnd") String dateEnd) {
	try {
	    return ResponseEntity.ok(service.createTimeSlots(id, LocalDate.parse(dateStart, Constants.DATE_PATTERN),
		    LocalDate.parse(dateEnd, Constants.DATE_PATTERN)));
	} catch (NoSuchElementException e) {
	    return ResponseEntity.notFound().build();
	}
    }

    @GetMapping
    public ResponseEntity<List<WorkplaceDto>> getWorkplacesForDoctor(
	    @RequestParam(name = "doctorId") @Positive Integer doctorId) {
	return ResponseEntity.ok(service.findWorkplacesForDoctor(doctorId).stream().map(mapper::entityToDto)
		.collect(Collectors.toList()));
    }

}
