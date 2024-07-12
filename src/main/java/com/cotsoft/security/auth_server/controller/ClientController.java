package com.cotsoft.security.auth_server.controller;

import com.cotsoft.security.auth_server.dto.AccessDTO;
import com.cotsoft.security.auth_server.dto.ClientDTO;
import com.cotsoft.security.auth_server.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/oauth2/clients")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<AccessDTO> createClient(@RequestBody ClientDTO clientDTO) {
        log.info("Creating client: {}", clientDTO);
        return new ResponseEntity<>(clientService.createClient(clientDTO), null, 201);
    }
}
