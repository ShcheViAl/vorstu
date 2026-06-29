package dev.vorstu.dto.student;

import lombok.Data;

@Data
public class StudentRequestDto {
    private String fio;
    private Long groupId;
    private String phoneNumber;
}
