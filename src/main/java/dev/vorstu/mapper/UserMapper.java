package dev.vorstu.mapper;

import dev.vorstu.dto.user.UserRequestDto;
import dev.vorstu.dto.user.UserResponseDto;
import dev.vorstu.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toEntity(UserRequestDto dto);
    void updateEntityFromDto(@MappingTarget User user, UserRequestDto dto);
}
