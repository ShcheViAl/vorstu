package dev.vorstu.dto.student;

import dev.vorstu.dto.user.UserRequestDto;
import dev.vorstu.validation.ContainsMinTwoWords;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StudentCreateRequestDto {
    @NotBlank
    @ContainsMinTwoWords
    private String fio;
    private Long groupId;
    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phoneNumber;
    @NotNull
    @Valid
    private UserRequestDto userRequestDto;
}
