package dev.vorstu.dto.teacher;

import dev.vorstu.validation.ContainsMinTwoWords;
import lombok.Data;

import java.util.List;

@Data
public class TeacherRequestDto {
    @ContainsMinTwoWords
    private String fio;
    private List<Long> groupIds;
}
