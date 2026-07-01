package dev.vorstu.controller;

import dev.vorstu.dto.teacher.TeacherCreateRequestDto;
import dev.vorstu.dto.teacher.TeacherRequestDto;
import dev.vorstu.dto.teacher.TeacherResponseDto;
import dev.vorstu.entity.User;
import dev.vorstu.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/{id}")
    public TeacherResponseDto getTeacher(@PathVariable Long id, @AuthenticationPrincipal User user){
        return teacherService.getTeacher(id, user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public TeacherResponseDto createTeacher(@Valid @RequestBody TeacherCreateRequestDto dto){
        return teacherService.createTeacher(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public Page<TeacherResponseDto> getAllTeachers(Pageable pageable){
        return teacherService.getAllTeachers(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public TeacherResponseDto changeTeacher(@Valid @RequestBody TeacherRequestDto dto, @PathVariable Long id){
        return teacherService.changeTeacher(dto, id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public TeacherResponseDto deleteTeacher(@PathVariable Long id){
        return teacherService.deleteTeacher(id);
    }
}
