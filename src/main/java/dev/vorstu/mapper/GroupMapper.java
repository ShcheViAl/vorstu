package dev.vorstu.mapper;

import dev.vorstu.dto.group.GroupRequestDto;
import dev.vorstu.dto.group.GroupResponseDto;
import dev.vorstu.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface GroupMapper {
    GroupResponseDto toDto(Group group);
    Group toEntity(GroupRequestDto dto);
    void updateEntityFromDto(GroupRequestDto dto, @MappingTarget Group group);
}