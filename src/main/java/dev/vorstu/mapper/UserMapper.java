package dev.vorstu.mapper;

import dev.vorstu.dto.user.UserRequestDto;
import dev.vorstu.dto.user.UserResponseDto;
import dev.vorstu.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toEntity(UserRequestDto dto);
    @Mapping(target = "password", ignore = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget User user, UserRequestDto dto);
}
