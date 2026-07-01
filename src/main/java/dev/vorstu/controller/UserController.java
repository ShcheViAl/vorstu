package dev.vorstu.controller;

import dev.vorstu.dto.user.UserRequestDto;
import dev.vorstu.dto.user.UserResponseDto;
import dev.vorstu.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto dto){
        return userService.createUser(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public Page<UserResponseDto> getAllUsers(Pageable pageable){
        return userService.getAllUsers(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public UserResponseDto changeUser(@Valid @RequestBody UserRequestDto dto, @PathVariable Long id){
        return userService.changeUser(dto, id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public UserResponseDto deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
