package bg.medbook.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.medbook.model.dto.TimeSlot;
import bg.medbook.model.dto.TimeSlotsPerDate;
import bg.medbook.model.entity.Workplace;
import bg.medbook.model.entity.Workschedule;
import bg.medbook.model.repository.AppointmentRepository;
import bg.medbook.model.repository.WorkplaceRepository;
import bg.medbook.model.repository.WorkscheduleRepository;

@Service
public class WorkplaceService {

    private WorkplaceRepository workplaceRepository;

    @Autowired
    private WorkscheduleRepository workscheduleRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public WorkplaceService(@Autowired WorkplaceRepository workplaceRepository) {
	this.workplaceRepository = workplaceRepository;
    }

    public List<TimeSlotsPerDate> createTimeSlots(Integer workplaceId, LocalDate from, LocalDate to)
	    throws NoSuchElementException {
	Workplace workplace = workplaceRepository.findById(workplaceId).orElseThrow();

	List<DayOfWeek> weekdays = new ArrayList<>();
	LocalDate fromCopy = LocalDate.of(from.getYear(), from.getMonth(), from.getDayOfMonth());
	while (!fromCopy.isAfter(to)) {
	    weekdays.add(fromCopy.getDayOfWeek());
	    fromCopy = fromCopy.plusDays(1);
	}

	List<Workschedule> schedule = workscheduleRepository.findByWorkplaceAndWeekdayIn(workplace, weekdays);
	if (schedule.isEmpty()) {
	    return new ArrayList<>();
	}

	List<LocalDateTime> appointmentsForPeriod = appointmentRepository
		.findByWorkplaceAndAppointmentTimeBetween(workplace, from.atStartOfDay(), to.atTime(LocalTime.MAX))
		.stream()//
		.map(e -> e.getAppointmentTime())//
		.collect(Collectors.toList());

	List<TimeSlotsPerDate> timeSlots = new ArrayList<>();
	final int interval = 30; // 30min for timeslot
	while (!from.isAfter(to)) {
	    List<TimeSlot> slotsForDay = new ArrayList<>();
	    timeSlots.add(new TimeSlotsPerDate(from.format(Constants.DATE_PATTERN), slotsForDay));

	    DayOfWeek dayOfWeek = from.getDayOfWeek();
	    List<Workschedule> workscheduleForDay = schedule.stream()//
		    .filter(e -> e.getWeekday().equals(dayOfWeek))
		    .sorted((first, second) -> first.getStartTime().isBefore(second.getStartTime()) ? -1 : 1)
		    .collect(Collectors.toList());

	    for (Workschedule sched : workscheduleForDay) {
		LocalDateTime start = from.atTime(sched.getStartTime());
		LocalDateTime lastInterval = from.atTime(sched.getEndTime().minusMinutes(interval));
		while (!start.isAfter(lastInterval)) {
		    slotsForDay.add(
			    new TimeSlot(start.format(Constants.TIME_PATTERN), appointmentsForPeriod.contains(start)));
		    start = start.plusMinutes(interval);
		}
	    }
	    from = from.plusDays(1);
	}
	return timeSlots;
    }

    public List<Workplace> findWorkplacesForDoctor(Integer doctorId) {
	return workplaceRepository.findByDoctor_Id(doctorId);
    }
}
