package bg.medbook.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import bg.medbook.model.dto.WorkplaceDto;
import bg.medbook.model.entity.Workplace;

@Mapper(uses = WorkscheduleMapper.class)
public interface WorkplaceMapper {

    WorkplaceMapper INSTANCE = Mappers.getMapper(WorkplaceMapper.class);

    @Mapping(source = "workschedules", target = "workschedule")
    WorkplaceDto entityToDto(Workplace entity);

    @Mapping(source = "workschedule", target = "workschedules")
    Workplace dtoToEntity(WorkplaceDto dto);

}
