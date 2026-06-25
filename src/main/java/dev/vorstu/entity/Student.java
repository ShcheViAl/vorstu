package dev.vorstu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="students")
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String fio;
    @Column(name="group_of_students")
    private String group;
    private String phoneNumber;
}
