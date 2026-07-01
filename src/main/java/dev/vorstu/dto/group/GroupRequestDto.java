package dev.vorstu.dto.group;

import dev.vorstu.entity.Teacher;
import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDto {
    private String name;
    private List<Teacher> teachers;
}
