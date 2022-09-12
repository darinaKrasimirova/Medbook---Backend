package bg.medbook.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import bg.medbook.model.dto.UserDto;
import bg.medbook.model.entity.User;
import bg.medbook.model.enumeration.UserAccountStatus;
import bg.medbook.model.enumeration.UserAccountType;

@Mapper(imports = { UserAccountType.class, UserAccountStatus.class })
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "accountType", expression = "java(entity.getAccountType().toString())")
    @Mapping(target = "accountStatus", expression = "java(entity.getAccountStatus().toString())")
    UserDto entityToDto(User entity);

    @Mapping(target = "accountType", expression = "java(dto.getAccountType() == null ? UserAccountType.PATIENT : UserAccountType.valueOf(dto.getAccountType().toUpperCase()))")
    @Mapping(target = "accountStatus", ignore = true)
    User dtoToEntity(UserDto dto);
}
