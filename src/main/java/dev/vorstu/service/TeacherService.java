package dev.vorstu.service;

import dev.vorstu.dto.teacher.TeacherRequestDto;
import dev.vorstu.dto.teacher.TeacherResponseDto;
import dev.vorstu.entity.Teacher;
import dev.vorstu.entity.User;
import dev.vorstu.exceptions.NotFoundException;
import dev.vorstu.mapper.TeacherMapper;
import dev.vorstu.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

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

    public Page<TeacherResponseDto> getAllTeachers(Pageable pageable){
        return teacherRepository.findAll(pageable).map(teacherMapper::toDto);
    }

    //todo add user
    public TeacherResponseDto createTeacher(TeacherRequestDto dto){
        Teacher newTeacher = teacherMapper.toEntity(dto);
        Teacher savedTeacher = teacherRepository.save(newTeacher);
        return teacherMapper.toDto(savedTeacher);
    }

    public TeacherResponseDto changeTeacher(TeacherRequestDto dto, Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()-> new NotFoundException("Teacher not found"));
        teacherMapper.updateEntityFromDto(dto, teacher);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public TeacherResponseDto deleteTeacher(Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()-> new NotFoundException("Teacher not found"));
        teacherRepository.delete(teacher);
        return teacherMapper.toDto(teacher);
    }
}
