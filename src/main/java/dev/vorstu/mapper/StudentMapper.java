package dev.vorstu.mapper;

import dev.vorstu.dto.StudentRequestDto;
import dev.vorstu.dto.StudentResponseDto;
import dev.vorstu.entity.Student;

public class StudentMapper {
    public static StudentResponseDto toDto(Student student){
        return StudentResponseDto.builder()
                .id(student.getId())
                .fio(student.getFio())
                .group(student.getGroup())
                .phoneNumber(student.getPhoneNumber())
                .build();
    }

    public static Student toEntity(StudentRequestDto dto){
        return Student.builder()
                .group(dto.getGroup())
                .phoneNumber(dto.getPhoneNumber())
                .fio(dto.getFio())
                .build();
    }

    public static void updateEntityFromDto(StudentRequestDto dto, Student student) {
        student.setFio(dto.getFio());
        student.setGroup(dto.getGroup());
        student.setPhoneNumber(dto.getPhoneNumber());
    }
}
