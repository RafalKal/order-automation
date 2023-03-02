package com.example.orderautomation.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String token;

    @OneToMany(mappedBy="user")
    private List<UserExternalSystemAuth> userAuths;


    public List<UserExternalSystemAuth> getUserAuths() {
        return userAuths;
    }

    public User() {
    }
}