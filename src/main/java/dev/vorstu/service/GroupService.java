package dev.vorstu.service;

import dev.vorstu.dto.group.GroupRequestDto;
import dev.vorstu.dto.group.GroupResponseDto;
import dev.vorstu.entity.*;
import dev.vorstu.exceptions.NotFoundException;
import dev.vorstu.mapper.GroupMapper;
import dev.vorstu.repositories.GroupRepository;
import dev.vorstu.repositories.StudentRepository;
import dev.vorstu.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public GroupResponseDto createGroup(GroupRequestDto dto){
        Group newGroup = groupMapper.toEntity(dto);
        if (dto.getTeacherIds()!=null){
            Set<Teacher> assignedTeachers = new HashSet<>(teacherRepository.findAllById(dto.getTeacherIds()));
            if (assignedTeachers.size() != dto.getTeacherIds().size()){
                throw new NotFoundException("One or more teachers not found");
            }
            for (Teacher teacher:assignedTeachers){
                teacher.getGroups().add(newGroup);
            }
            teacherRepository.saveAll(assignedTeachers);
            newGroup.setTeachers(assignedTeachers);
        }
        Group savedGroup = groupRepository.save(newGroup);
        return groupMapper.toDto(savedGroup);
    }

    public Page<GroupResponseDto> getAllGroups(Pageable pageable){
        return groupRepository.findAll(pageable).map(groupMapper::toDto);
    }

    @Transactional(readOnly = true)
    public GroupResponseDto getGroup(Long id, User user){
        Group group = groupRepository.findById(id).orElseThrow(()-> new NotFoundException("Group not found"));

        switch (user.getRole()){
            case ADMIN:
                return groupMapper.toDto(group);
            case TEACHER:
                Teacher teacher = teacherRepository.findByUserId(user.getId()).orElseThrow(()->new NotFoundException("Teacher not found"));
                if (!teacher.getGroups().contains(group)){
                    throw new AccessDeniedException("Teacher has access only to groups assigned to him");
                }
                return groupMapper.toDto(group);
            case STUDENT:
                Student student = studentRepository.findByUserId(user.getId()).orElseThrow(()->new NotFoundException("Student not found"));
                if (!student.getGroup().equals(group)){
                    throw new AccessDeniedException("Student has access only to his group");
                }
                return groupMapper.toDto(group);
            default:
                throw new AccessDeniedException("Role Unknown");
        }
    }

    @Transactional
    public GroupResponseDto changeGroup(GroupRequestDto dto, Long id){
        Group group = groupRepository.findById(id).orElseThrow(()-> new NotFoundException("Group not found"));
        groupMapper.updateEntityFromDto(dto, group);
        if (dto.getTeacherIds()!=null){
            Set<Teacher> oldTeachers = teacherRepository.findAllByGroup(group);
            Set<Teacher> assignedTeachers = new HashSet<>(teacherRepository.findAllById(dto.getTeacherIds()));
            if (assignedTeachers.size() != dto.getTeacherIds().size()){
                throw new NotFoundException("One or more teachers not found");
            }
            for (Teacher teacher:assignedTeachers){
                teacher.getGroups().add(group);
            }
            for (Teacher teacher:oldTeachers){
                if (!assignedTeachers.contains(teacher)){
                    teacher.getGroups().remove(group);
                }
            }
            teacherRepository.saveAll(oldTeachers);
            teacherRepository.saveAll(assignedTeachers);
            group.setTeachers(assignedTeachers);
        }
        return groupMapper.toDto(groupRepository.save(group));
    }

    public GroupResponseDto deleteGroup(Long id){
        Group group = groupRepository.findById(id).orElseThrow(()-> new NotFoundException("Group not found"));
        groupRepository.delete(group);
        return groupMapper.toDto(group);
    }

}
