package dev.vorstu.repositories;

import dev.vorstu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {
}
