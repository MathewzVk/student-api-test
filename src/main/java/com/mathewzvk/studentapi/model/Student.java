package com.mathewzvk.studentapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String gender;

    private String password;
}
