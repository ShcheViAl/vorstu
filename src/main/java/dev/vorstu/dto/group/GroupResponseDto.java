package dev.vorstu.dto.group;

import dev.vorstu.entity.Teacher;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupResponseDto {
    private Long id;
    private String name;
    private List<Teacher> teachers;
}
