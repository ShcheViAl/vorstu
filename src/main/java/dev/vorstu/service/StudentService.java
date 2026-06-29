package dev.vorstu.service;

import dev.vorstu.dto.student.StudentRequestDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.exceptions.NotFoundException;

import dev.vorstu.mapper.StudentMapper;
import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentMapper studentMapper;

    public Page<StudentResponseDto> getAllStudents(Pageable pageable) throws AccessDeniedException {
        User user = getUser();
        switch (user.getRole()){
            case ADMIN:
                return studentRepository.findAll(pageable)
                        .map(student -> studentMapper.toResponseDto(student));

            case TEACHER:
                Long teacherId = teacherRepository.findByUserId(user.getId()).orElseThrow(()-> new NotFoundException("User not found")).getId();
                return studentRepository.findAllByGroupTeacherId(teacherId, pageable).map(student ->  studentMapper.toResponseDto(student));

            case STUDENT:
                Student student = studentRepository.findByUserId(user.getId()).orElseThrow(()->new NotFoundException("User noy found"));
                return studentRepository.findAllByGroupId(student.getGroup().getId(),pageable).map(student1 -> studentMapper.toResponseDto(student1));

            default:
                throw new AccessDeniedException("Role Unknown");
        }
    }

    public StudentResponseDto createStudent(StudentRequestDto newStudentDto) throws AccessDeniedException {
        User user = getUser();
        if (!user.getRole().equals(Role.ADMIN)) throw new AccessDeniedException("only admin can create Student");
        Student savedStudent = studentRepository.save(studentMapper.toEntity(newStudentDto));
        return studentMapper.toResponseDto(savedStudent);
    }

    public StudentResponseDto changeStudent(StudentRequestDto changingStudentDto, Long id) throws AccessDeniedException {
        Student changingStudent = studentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Student with this id does not found"));

        User user = getUser();
        switch (user.getRole()){
            case STUDENT:
                if (!user.getId().equals(changingStudent.getUser().getId())){
                    throw new AccessDeniedException("Student not allowed to change another student");
                }
                studentMapper.updateEntityFromDto(changingStudentDto, changingStudent);
                return studentMapper.toResponseDto(studentRepository.save(changingStudent));
            case TEACHER:
                if (!user.getId().equals(changingStudent.getGroup().getTeacher().getUser().getId())){
                    throw new AccessDeniedException("Teacher can change only studends assigned to him");
                }
                studentMapper.updateEntityFromDto(changingStudentDto, changingStudent);
                return studentMapper.toResponseDto(studentRepository.save(changingStudent));
            case ADMIN:
                studentMapper.updateEntityFromDto(changingStudentDto, changingStudent);
                return studentMapper.toResponseDto(studentRepository.save(changingStudent));
            default:
                throw new AccessDeniedException("Role Unknown");
        }
    }

    public StudentResponseDto deleteStudent(Long id) throws AccessDeniedException {
        Student deletedStudent = studentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Student with this id does not found"));

        User user = getUser();

        if (!user.getRole().equals(Role.ADMIN)) throw new AccessDeniedException("only admin can delete Student");

        studentRepository.deleteById(id);
        return studentMapper.toResponseDto(deletedStudent);
    }


    private User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
