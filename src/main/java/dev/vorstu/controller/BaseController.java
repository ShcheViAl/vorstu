package dev.vorstu.controller;

import dev.vorstu.dto.StudentRequestDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/base")
public class BaseController {
    @Autowired
    private StudentService studentService;

    @GetMapping("students")
    public Page<StudentResponseDto> getAllStudents(Pageable pageable){
        return studentService.getAllStudents(pageable);
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto createStudent(@RequestBody StudentRequestDto newStudent){
        return studentService.createStudent(newStudent);
    }

    @PutMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto changeStudent(@RequestBody StudentRequestDto changingStudent, @PathVariable Long id) {
        return studentService.changeStudent(changingStudent, id);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentResponseDto deleteStudent(@PathVariable("id") Long id) {
        return studentService.deleteStudent(id);
    }
}
