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
@Builder
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "group_gen")
    @SequenceGenerator(name = "group_gen", sequenceName = "groups_seq")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<Teacher> teachers;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Group group = (Group) object;
        return Objects.equals(id, group.id);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
