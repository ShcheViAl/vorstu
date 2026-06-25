package dev.vorstu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponseDto {
    private Long id;
    private String fio;
    private String group;
    private String phoneNumber;
}
