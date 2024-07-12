package com.cotsoft.security.auth_server.repositories;

import com.cotsoft.security.auth_server.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByClientId(String clientId);
    ClientEntity findByAppName(String appName);
}
