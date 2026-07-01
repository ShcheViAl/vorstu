package dev.vorstu.mapper;

import dev.vorstu.dto.teacher.TeacherRequestDto;
import dev.vorstu.dto.teacher.TeacherResponseDto;
import dev.vorstu.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface TeacherMapper {
    TeacherResponseDto toDto(Teacher teacher);
    Teacher toEntity(TeacherRequestDto dto);
    void updateEntityFromDto(TeacherRequestDto dto, @MappingTarget Teacher teacher);
}
