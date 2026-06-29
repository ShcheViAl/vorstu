package dev.vorstu.repositories;

import dev.vorstu.entity.Student;
import dev.vorstu.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAllByGroupId(Long groupId, Pageable pageable);
    Page<Student> findAllByGroupTeacherId(Long teacherId, Pageable pageable);
    Optional<Student> findByUserId(Long id);

}
