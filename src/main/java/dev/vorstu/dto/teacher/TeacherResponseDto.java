package dev.vorstu.dto.teacher;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeacherResponseDto {
    private Long id;
    private String fio;
    private Long userId;
    private List<Long> groupIds;
}
