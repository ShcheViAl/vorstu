package dev.vorstu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="teachers")
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "teacher_gen")
    @SequenceGenerator(name = "teacher_gen", sequenceName = "teacher_seq")
    private Long id;
    private String fio;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "teachers_groups", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Teacher teacher = (Teacher) object;
        return Objects.equals(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
