package com.example.orderautomation.entities.repositories;

import com.example.orderautomation.entities.UserExternalSystemAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserExternalSystemAuth, Long> {}
