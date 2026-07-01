package dev.vorstu.dto.group;

import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDto {
    private String name;
    private List<Long> teacherIds;
}
