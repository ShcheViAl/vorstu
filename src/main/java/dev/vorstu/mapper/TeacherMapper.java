package dev.vorstu.mapper;

import dev.vorstu.dto.teacher.TeacherCreateRequestDto;
import dev.vorstu.dto.teacher.TeacherRequestDto;
import dev.vorstu.dto.teacher.TeacherResponseDto;
import dev.vorstu.entity.Group;
import dev.vorstu.entity.Teacher;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface TeacherMapper {
    @Mapping(source = "user.id", target = "userId")
    TeacherResponseDto toDto(Teacher teacher);
    default Long groupToId(Group group){
        return group == null ? null:group.getId();
    }

    @Mapping(target = "groups", ignore = true)
    Teacher toEntity(TeacherRequestDto dto);
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "user", ignore = true)
    Teacher toCreatedEntity(TeacherCreateRequestDto dto);
    @Mapping(target = "groups", ignore = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TeacherRequestDto dto, @MappingTarget Teacher teacher);
}
