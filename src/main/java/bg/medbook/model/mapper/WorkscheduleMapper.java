package bg.medbook.model.mapper;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import bg.medbook.model.dto.WorkscheduleDto;
import bg.medbook.model.entity.Workschedule;
import bg.medbook.service.Constants;

@Mapper
public interface WorkscheduleMapper {
    WorkscheduleMapper INSTANCE = Mappers.getMapper(WorkscheduleMapper.class);

    default WorkscheduleDto entityToDto(Workschedule entity) {
	WorkscheduleDto dto = new WorkscheduleDto();
	dto.setDay(entity.getWeekday().getValue() % 7);
	dto.setStartTime(entity.getStartTime().format(Constants.TIME_PATTERN));
	dto.setEndTime(entity.getEndTime().format(Constants.TIME_PATTERN));
	return dto;
    }

    default Workschedule dtoToEntity(WorkscheduleDto dto) {
	Workschedule entity = new Workschedule();
	entity.setWeekday(DayOfWeek.of(dto.getDay() == 0 ? 7 : dto.getDay()));
	entity.setStartTime(LocalTime.parse(dto.getStartTime(), Constants.TIME_PATTERN));
	entity.setEndTime(LocalTime.parse(dto.getEndTime(), Constants.TIME_PATTERN));
	return entity;
    }

}
