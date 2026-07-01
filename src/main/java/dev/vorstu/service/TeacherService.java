package dev.vorstu.service;

import dev.vorstu.dto.teacher.TeacherCreateRequestDto;
import dev.vorstu.dto.teacher.TeacherRequestDto;
import dev.vorstu.dto.teacher.TeacherResponseDto;
import dev.vorstu.entity.Group;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.Teacher;
import dev.vorstu.entity.User;
import dev.vorstu.exceptions.NotFoundException;
import dev.vorstu.mapper.TeacherMapper;
import dev.vorstu.mapper.UserMapper;
import dev.vorstu.repositories.GroupRepository;
import dev.vorstu.repositories.TeacherRepository;
import dev.vorstu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public TeacherResponseDto getTeacher(Long id, User user){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()->new NotFoundException("Teacher not found"));
        switch (user.getRole()){
            case ADMIN -> {}
            case TEACHER -> {
                if (!teacher.getUser().getId().equals(user.getId())){
                    throw new AccessDeniedException("Teacher has access only to his own information");
                }
            }
            case STUDENT ->
                throw new AccessDeniedException("Student does not have access to teacher's information");
            default ->
                throw new AccessDeniedException("Unknown role");
        }
        return teacherMapper.toDto(teacher);
    }

    @Transactional(readOnly = true)
    public Page<TeacherResponseDto> getAllTeachers(Pageable pageable){
        return teacherRepository.findAll(pageable).map(teacherMapper::toDto);
    }

    @Transactional
    public TeacherResponseDto createTeacher(TeacherCreateRequestDto dto){
        Teacher newTeacher = teacherMapper.toCreatedEntity(dto);
        User newUser = userMapper.toEntity(dto.getUserRequestDto());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole(Role.TEACHER);
        User savedUser = userRepository.save(newUser);
        newTeacher.setUser(savedUser);

        Set<Group> newAssignedGroups = new HashSet<>(groupRepository.findAllById(dto.getGroupIds()));
        if (newAssignedGroups.size() != dto.getGroupIds().size()){
            throw new NotFoundException("One or more groups not found");
        }
        newTeacher.setGroups(newAssignedGroups);
        Teacher savedTeacher = teacherRepository.save(newTeacher);
        return teacherMapper.toDto(savedTeacher);
    }

    @Transactional
    public TeacherResponseDto changeTeacher(TeacherRequestDto dto, Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()-> new NotFoundException("Teacher not found"));
        if (dto.getGroupIds()!=null){
            Set<Group> newAssignedGroups = new HashSet<>(groupRepository.findAllById(dto.getGroupIds()));
            if (newAssignedGroups.size() != dto.getGroupIds().size()){
                throw new NotFoundException("One or more groups not found");
            }
            teacher.setGroups(newAssignedGroups);
        }
        teacherMapper.updateEntityFromDto(dto, teacher);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    @Transactional
    public TeacherResponseDto deleteTeacher(Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()-> new NotFoundException("Teacher not found"));
        teacherRepository.delete(teacher);
        return teacherMapper.toDto(teacher);
    }
}
