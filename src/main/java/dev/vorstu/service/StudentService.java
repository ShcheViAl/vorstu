package dev.vorstu.service;

import dev.vorstu.dto.StudentRequestDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.entity.Student;
import dev.vorstu.exceptions.NotFoundException;

import static dev.vorstu.mapper.StudentMapper.*;

import dev.vorstu.mapper.StudentMapper;
import dev.vorstu.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Page<StudentResponseDto> getAllStudents(Pageable pageable){
        return studentRepository.findAll(pageable)
                .map(StudentMapper::toDto);
    }

    public StudentResponseDto createStudent(StudentRequestDto newStudentDto){
        Student savedStudent = studentRepository.save(toEntity(newStudentDto));
        return toDto(savedStudent);
    }

    public StudentResponseDto changeStudent(StudentRequestDto changingStudentDto, Long id) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Student with this id does not found"));

        updateEntityFromDto(changingStudentDto, existingStudent);

        return toDto(studentRepository.save(existingStudent));
    }

    public StudentResponseDto deleteStudent(Long id) {
        Student deletedStudent = studentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Student with this id does not found"));

        studentRepository.deleteById(id);

        return toDto(deletedStudent);
    }
}
