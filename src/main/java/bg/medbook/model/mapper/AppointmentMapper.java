package bg.medbook.model.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import bg.medbook.model.dto.AppointmentDto;
import bg.medbook.model.entity.Appointment;
import bg.medbook.service.Constants;

@Mapper(uses = { UserMapper.class, DoctorMapper.class, WorkplaceMapper.class }, imports = Constants.class)
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(target = "date", expression = "java(entity.getAppointmentTime().format(Constants.DATE_PATTERN))")
    @Mapping(target = "time", expression = "java(entity.getAppointmentTime().format(Constants.TIME_PATTERN))")
    AppointmentDto entityToDto(Appointment entity);

    @Mapping(target = "appointmentTime", expression = "java(timeDtoToEntity(dto.getDate(), dto.getTime()))")
    Appointment dtoToEntity(AppointmentDto dto);

    default LocalDateTime timeDtoToEntity(String date, String time) {
	return LocalDateTime.of(LocalDate.parse(date, Constants.DATE_PATTERN),
		LocalTime.parse(time, Constants.TIME_PATTERN));
    }

}
