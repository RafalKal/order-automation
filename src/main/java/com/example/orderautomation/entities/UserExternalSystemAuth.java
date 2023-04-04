package com.example.orderautomation.entities;

import jakarta.persistence.*;

@Entity
public class UserExternalSystemAuth {

    @Id
    @GeneratedValue
    private Long id;

    private String serviceName;

    @Column(name="\"authorization\"")
    private String authorization;

    @Column(columnDefinition = "jsonb", name = "config_json")
    private String configJson;

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

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }
}