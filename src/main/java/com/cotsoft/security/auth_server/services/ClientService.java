package com.cotsoft.security.auth_server.services;

import com.cotsoft.security.auth_server.dto.AccessDTO;
import com.cotsoft.security.auth_server.dto.ClientDTO;
import com.cotsoft.security.auth_server.entities.ClientEntity;
import com.cotsoft.security.auth_server.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public AccessDTO createClient(ClientDTO clientDTO) {
        log.info("Creating client: {}", clientDTO);

        ClientEntity clientExists = findByAppName(clientDTO.getAppName());
        if (clientExists != null) {
            log.error("Client already exists: {}", clientDTO.getAppName());
            throw new RuntimeException("Client already exists");
        }

        String clientId = generateClientId();
        String clientSecret = generateClientSecret();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAppName(clientDTO.getAppName());
        clientEntity.setDescription(clientDTO.getAppDescription());
        clientEntity.setClientId(clientId);
        clientEntity.setClientSecret(passwordEncoder.encode(clientSecret));
        clientEntity.setRedirectUri(clientDTO.getCallbackUrl());
        clientEntity.setCallbackUri(clientDTO.getCallbackUrl());
        clientEntity.setScope(generateScopes(clientDTO.getScope()));
        clientEntity.setType("CLIENT_CREDENTIALS");
        clientEntity.setIsActive(true);

        clientRepository.save(clientEntity);

        return new AccessDTO(clientId, clientSecret);
    }

    public ClientEntity findByAppName(String appName) {
        return clientRepository.findByAppName(appName);
    }


    public String generateScopes(String[] scope){
        StringBuilder scopes = new StringBuilder();
        for (String s : scope) {
            scopes.append(s).append(" ");
        }
        return scopes.toString().trim();
    }

    public String generateClientId() {
        SecureRandom random = new SecureRandom();
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int length = 10;
        String clientId;

        do {
            StringBuilder clientIdBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                clientIdBuilder.append(characters.charAt(random.nextInt(characters.length())));
            }
            clientId = "MP_" + clientIdBuilder.toString();
        } while (clientRepository.findByClientId(clientId) != null);

        return clientId;
    }


    public String generateClientSecret() {
        SecureRandom random = new SecureRandom();
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
        int length = 50;

        StringBuilder clientId = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            clientId.append(characters.charAt(random.nextInt(characters.length())));
       }

        return clientId.toString();
    }
}
