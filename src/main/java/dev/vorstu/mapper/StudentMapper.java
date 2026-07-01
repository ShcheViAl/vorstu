package dev.vorstu.mapper;

import dev.vorstu.dto.student.StudentCreateRequestDto;
import dev.vorstu.dto.student.StudentRequestDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.entity.Student;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface StudentMapper {
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "user.id", target = "userId")
    StudentResponseDto toResponseDto(Student student);

    @Mapping(target = "group", ignore = true)
    Student toEntity(StudentRequestDto dto);

    @Mapping(target = "group", ignore = true)
    @Mapping(target = "user", ignore = true)
    Student toCreatedEntity(StudentCreateRequestDto dto);

    @Mapping(target = "group", ignore = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StudentRequestDto dto, @MappingTarget Student student);
}
