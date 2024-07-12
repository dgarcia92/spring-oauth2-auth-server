package com.cotsoft.security.auth_server.repositories;
import com.cotsoft.security.auth_server.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
