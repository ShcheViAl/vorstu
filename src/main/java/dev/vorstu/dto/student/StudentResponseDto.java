package dev.vorstu.dto.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponseDto {
    private Long id;
    private String fio;
    private Long groupId;
    private String phoneNumber;
}
