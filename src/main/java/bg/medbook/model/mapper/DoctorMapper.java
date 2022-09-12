package bg.medbook.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import bg.medbook.model.dto.DoctorDto;
import bg.medbook.model.entity.Doctor;

@Mapper(uses = WorkplaceMapper.class)
public interface DoctorMapper {

    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    DoctorDto entityToDto(Doctor entity);

    @Mapping(source = "name", target = "user.name")
    @Mapping(source = "username", target = "user.username")
    Doctor dtoToEntity(DoctorDto dto);

}
