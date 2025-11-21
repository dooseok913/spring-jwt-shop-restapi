package com.springboot.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "member")
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private  String name;

    private  String email;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;


    public Member(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = Role.USER;
        this.createdAt = LocalDateTime.now();
    }


}
