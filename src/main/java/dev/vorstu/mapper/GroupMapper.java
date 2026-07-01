package dev.vorstu.mapper;

import dev.vorstu.dto.group.GroupRequestDto;
import dev.vorstu.dto.group.GroupResponseDto;
import dev.vorstu.entity.Group;
import dev.vorstu.entity.Teacher;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {
    GroupResponseDto toDto(Group group);
    default Long teacherToId(Teacher teacher){
        return teacher == null ? null : teacher.getId();
    }

    @Mapping(target = "teachers", ignore = true)
    Group toEntity(GroupRequestDto dto);
    @Mapping(target = "teachers", ignore = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(GroupRequestDto dto, @MappingTarget Group group);
}