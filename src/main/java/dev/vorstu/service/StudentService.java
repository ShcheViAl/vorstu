package dev.vorstu.service;

import dev.vorstu.dto.student.StudentCreateRequestDto;
import dev.vorstu.dto.student.StudentRequestDto;
import dev.vorstu.dto.student.StudentResponseDto;
import dev.vorstu.entity.*;
import dev.vorstu.exceptions.NotFoundException;

import dev.vorstu.mapper.StudentMapper;
import dev.vorstu.mapper.UserMapper;
import dev.vorstu.repositories.GroupRepository;
import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.TeacherRepository;
import dev.vorstu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Page<StudentResponseDto> getAllStudents(Pageable pageable, User user) {
        switch (user.getRole()){
            case ADMIN -> {
                return studentRepository.findAll(pageable)
                        .map(studentMapper::toResponseDto);
            }
            case TEACHER -> {
                Long teacherId = teacherRepository.findByUserId(user.getId()).orElseThrow(() -> new NotFoundException("User not found")).getId();
                return studentRepository.findAllByGroupTeachersId(teacherId, pageable).map(studentMapper::toResponseDto);
            }
            case STUDENT -> throw new AccessDeniedException("Student has access only to his information");
            default -> throw new AccessDeniedException("Role Unknown");
        }
    }

    @Transactional
    public StudentResponseDto getStudent(Long id, User user) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new NotFoundException("Student not found"));

        switch (user.getRole()){
            case ADMIN -> {}
            case TEACHER -> {
                if (student.getGroup() == null || !teacherRepository.existsByGroupsIdAndUserId(student.getGroup().getId(), user.getId())){
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

    @Transactional
    public StudentResponseDto createStudent(StudentCreateRequestDto newStudentDto) {
        Student newStudent = studentMapper.toCreatedEntity(newStudentDto);
        Group newAssignedGroup = groupRepository.findById(newStudentDto.getGroupId()).orElseThrow(()->
                new NotFoundException("Group with this id not found"));
        newStudent.setGroup(newAssignedGroup);

        User newUser = userMapper.toEntity(newStudentDto.getUserRequestDto());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole(Role.STUDENT);
        User savedUser = userRepository.save(newUser);
        newStudent.setUser(savedUser);

        Student savedStudent = studentRepository.save(newStudent);
        return studentMapper.toResponseDto(savedStudent);
    }

    @Transactional
    public StudentResponseDto changeStudent(StudentRequestDto changingStudentDto, Long id, User user) {
        Student changingStudent = studentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Student with this id not found"));
        switch (user.getRole()){
            case STUDENT -> {
                if (!user.getId().equals(changingStudent.getUser().getId())){
                    throw new AccessDeniedException("Student not allowed to change another student");
                }
            }
            case TEACHER -> {
                Group group = changingStudent.getGroup();
                if (!teacherRepository.existsByGroupsIdAndUserId(group.getId(), user.getId())){
                    throw new AccessDeniedException("Teacher can change only students assigned to him");
                }
            }
            case ADMIN -> {}
            default ->
                throw new AccessDeniedException("Role Unknown");
        }
        if (changingStudentDto.getGroupId()!=null){
            Group newAssignedGroup = groupRepository.findById(changingStudentDto.getGroupId()).orElseThrow(()->
                    new NotFoundException("Group with this id not found"));
            changingStudent.setGroup(newAssignedGroup);
        }
        studentMapper.updateEntityFromDto(changingStudentDto, changingStudent);
        return studentMapper.toResponseDto(studentRepository.save(changingStudent));
    }

    public StudentResponseDto deleteStudent(Long id){
        Student deletedStudent = studentRepository.findById(id).orElseThrow(()->
                new NotFoundException("Student with this id does not found"));
        studentRepository.delete(deletedStudent);
        return studentMapper.toResponseDto(deletedStudent);
    }
}
