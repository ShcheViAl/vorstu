package dev.vorstu.dto.teacher;

import dev.vorstu.entity.Group;
import dev.vorstu.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeacherResponseDto {
    private Long id;
    private String fio;
    private User user;
    private List<Group> groups;
}
