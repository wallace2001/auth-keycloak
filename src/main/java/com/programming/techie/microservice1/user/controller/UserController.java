package com.programming.techie.microservice1.user.controller;

import com.programming.techie.microservice1.user.dto.UserRegistrationRecord;
import com.programming.techie.microservice1.keycloak.service.KeycloakService;
import com.programming.techie.microservice1.user.service.UserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired()
    private UserService userService;

    @Autowired()
    private KeycloakService keycloakService;

    @Value("${keycloak.clientId}")
    String clientId;

    @GetMapping("/")
    public UserRepresentation list(@RequestParam("userId") String userId) {
        return keycloakService.getUserById(userId);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody() UserRegistrationRecord userRegistrationRecord) {
        try {
            Response response = userService.create(userRegistrationRecord);
            if (response.getStatus() == 201) {
                Map<String, List<String>> roles = Collections.singletonMap(clientId, userRegistrationRecord.roles());

                keycloakService.assignRoles(roles, userRegistrationRecord.username());
                return ResponseEntity.status(201).body("Usuário criado com sucesso!");
            } else {
                return ResponseEntity.status(400).body("Erro ao criar usuário");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar usuário");
        }
    }
}
