package com.example.orderautomation.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.orderautomation.entities.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByToken(String token);

}
