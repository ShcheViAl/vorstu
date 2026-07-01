package dev.vorstu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="students")
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "student_gen")
    @SequenceGenerator(name = "student_gen", sequenceName = "students_seq")
    private Long id;
    private String fio;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Student student = (Student) object;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
