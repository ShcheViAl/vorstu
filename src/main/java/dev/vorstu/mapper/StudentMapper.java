package dev.vorstu.mapper;

import dev.vorstu.dto.StudentRequestDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    StudentResponseDto toResponseDto(Student student);

    Student toEntity(StudentRequestDto dto);
    void updateEntityFromDto(StudentRequestDto dto, @MappingTarget Student student);
}
