package dev.vorstu.dto.teacher;

import dev.vorstu.dto.user.UserRequestDto;
import dev.vorstu.validation.ContainsMinTwoWords;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TeacherCreateRequestDto {
    @NotBlank
    @ContainsMinTwoWords
    private String fio;
    private List<Long> groupIds;
    @NotNull
    @Valid
    private UserRequestDto userRequestDto;
}
