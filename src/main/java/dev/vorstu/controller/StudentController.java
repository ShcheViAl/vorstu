package dev.vorstu.controller;

import dev.vorstu.dto.student.StudentRequestDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.service.StudentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @SneakyThrows
    @GetMapping()
    public Page<StudentResponseDto> getAllStudents(Pageable pageable){
        return studentService.getAllStudents(pageable);
    }

    @SneakyThrows
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto createStudent(@RequestBody StudentRequestDto newStudent){
        return studentService.createStudent(newStudent);
    }

    @SneakyThrows
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto changeStudent(@RequestBody StudentRequestDto changingStudent, @PathVariable Long id) {
        return studentService.changeStudent(changingStudent, id);
    }

    @SneakyThrows
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto deleteStudent(@PathVariable("id") Long id) {
        return studentService.deleteStudent(id);
    }
}
