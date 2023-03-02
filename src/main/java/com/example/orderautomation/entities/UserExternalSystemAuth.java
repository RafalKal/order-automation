package com.example.orderautomation.entities;

import jakarta.persistence.*;

@Entity
public class UserExternalSystemAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String serviceName;
    @Column(name="\"authorization\"")
    private String authorization;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    public String getServiceName() {
        return serviceName;
    }

    public String getAuthorization() {
        return authorization;
    }

    public UserExternalSystemAuth() {
    }
}