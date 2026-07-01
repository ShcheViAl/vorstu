package dev.vorstu.controller;

import dev.vorstu.dto.student.StudentCreateRequestDto;
import dev.vorstu.dto.student.StudentRequestDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.entity.User;
import dev.vorstu.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/{id}")
    public StudentResponseDto getStudent(@PathVariable Long id, @AuthenticationPrincipal User user){
        return studentService.getStudent(id, user);
    }

    @GetMapping
    public Page<StudentResponseDto> getAllStudents(Pageable pageable, @AuthenticationPrincipal User user){
        return studentService.getAllStudents(pageable, user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public StudentResponseDto createStudent(@Valid @RequestBody StudentCreateRequestDto newStudent){
        return studentService.createStudent(newStudent);
    }


    @PutMapping(value = "/{id}")
    public StudentResponseDto changeStudent(@Valid @RequestBody StudentRequestDto changingStudent, @PathVariable Long id, @AuthenticationPrincipal User user) {
        return studentService.changeStudent(changingStudent, id, user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public StudentResponseDto deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
