package dev.vorstu.service;

import dev.vorstu.dto.StudentRequestDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.entity.Student;
import dev.vorstu.exceptions.NotFoundException;
import dev.vorstu.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    @Test
    void createStudentTest(){
        StudentRequestDto requestDto = new StudentRequestDto();
        requestDto.setFio("Иванов Иван");
        requestDto.setGroup("ИФСТ-21");
        requestDto.setPhoneNumber("+7888");

        Student savedStudent = Student.builder()
                .id(1L)
                .fio("Иванов Иван")
                .phoneNumber("+7888")
                .group("ИФСТ-21")
                .build();

        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentResponseDto responseDto = studentService.createStudent(requestDto);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getId());
        assertEquals("Иванов Иван", responseDto.getFio());
        assertEquals("ИФСТ-21", responseDto.getGroup());
        assertEquals("+7888", responseDto.getPhoneNumber());
        Mockito.verify(studentRepository, Mockito.times(1)).save(any(Student.class));
    }

    @Test
    void changeStudentTest(){
        Long studentId = 100L;
        StudentRequestDto requestDto = new StudentRequestDto();

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> studentService.changeStudent(requestDto, studentId));
    }
}
