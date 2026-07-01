package dev.vorstu.dto.user;

import dev.vorstu.entity.Role;
import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String password;
    private Role role;
}