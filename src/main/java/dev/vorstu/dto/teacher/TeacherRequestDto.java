package dev.vorstu.dto.teacher;

import dev.vorstu.entity.Group;
import dev.vorstu.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class TeacherRequestDto {
    private String fio;
    private User user;
    private List<Group> groups;
}
