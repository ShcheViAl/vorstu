package dev.vorstu.repositories;

import dev.vorstu.entity.Group;
import dev.vorstu.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserId(Long id);
    Set<Teacher> findAllByGroup(Group group);
    boolean existsByGroupsIdAndUserId(Long groupId, Long userId);
}
