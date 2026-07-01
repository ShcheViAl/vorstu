package dev.vorstu.dto.user;

import dev.vorstu.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private Role role;
}
