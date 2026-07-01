package dev.vorstu.service;

import dev.vorstu.dto.student.StudentRequestDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.entity.*;
import dev.vorstu.exceptions.NotFoundException;

import dev.vorstu.mapper.StudentMapper;
import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final StudentMapper studentMapper;

    public Page<StudentResponseDto> getAllStudents(Pageable pageable, User user) {
        switch (user.getRole()){
            case ADMIN:
                return studentRepository.findAll(pageable)
                        .map(studentMapper::toResponseDto);

            case TEACHER:
                Long teacherId = teacherRepository.findByUserId(user.getId()).orElseThrow(()-> new NotFoundException("User not found")).getId();
                return studentRepository.findAllByGroupTeacherId(teacherId, pageable).map(studentMapper::toResponseDto);

            case STUDENT:
                Student student = studentRepository.findByUserId(user.getId()).orElseThrow(()->new NotFoundException("User not found"));
                return studentRepository.findAllByGroupId(student.getGroup().getId(),pageable).map(studentMapper::toResponseDto);

            default:
                throw new AccessDeniedException("Role Unknown");
        }
    }

    public StudentResponseDto getStudent(Long id, User user) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new NotFoundException("Student not found"));

        switch (user.getRole()){
            case ADMIN -> {}
            case TEACHER -> {
                if (!groupHasTeacherByUserId(student.getGroup(), user.getId())){
                    throw new AccessDeniedException("Teacher has access only to students assigned to him");
                }
            }
            case STUDENT -> {
                if (!user.getId().equals(student.getUser().getId())){
                    throw new AccessDeniedException("Student has access only to his information");
                }
            }
            default ->
                throw new AccessDeniedException("Role Unknown");
        }
        return studentMapper.toResponseDto(student);
    }
    //TODO create User
    public StudentResponseDto createStudent(StudentRequestDto newStudentDto) {
        Student savedStudent = studentRepository.save(studentMapper.toEntity(newStudentDto));
        return studentMapper.toResponseDto(savedStudent);
    }

    public StudentResponseDto changeStudent(StudentRequestDto changingStudentDto, Long id, User user) {
        Student changingStudent = studentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Student with this id does not found"));

        switch (user.getRole()){
            case STUDENT -> {
                if (!user.getId().equals(changingStudent.getUser().getId())){
                    throw new AccessDeniedException("Student not allowed to change another student");
                }
            }
            case TEACHER -> {
                Group group = changingStudent.getGroup();
                if (!groupHasTeacherByUserId(group, user.getId())){
                    throw new AccessDeniedException("Teacher can change only students assigned to him");
                }
            }
            case ADMIN -> {}
            default ->
                throw new AccessDeniedException("Role Unknown");
        }
        studentMapper.updateEntityFromDto(changingStudentDto, changingStudent);
        return studentMapper.toResponseDto(studentRepository.save(changingStudent));
    }

    public StudentResponseDto deleteStudent(Long id){
        Student deletedStudent = studentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Student with this id does not found"));
        studentRepository.deleteById(id);
        return studentMapper.toResponseDto(deletedStudent);
    }

    private boolean groupHasTeacherByUserId(Group group, Long userId){
        if (group.getTeachers()==null || userId==null){
            return false;
        }
        return group.getTeachers().stream()
                .map(Teacher::getUser)
                .filter(Objects::nonNull)
                .anyMatch(user -> userId.equals(user.getId()));
    }
}
