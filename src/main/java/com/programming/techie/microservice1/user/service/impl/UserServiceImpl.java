package com.programming.techie.microservice1.user.service.impl;

import com.programming.techie.microservice1.user.dto.UserRegistrationRecord;
import com.programming.techie.microservice1.keycloak.service.KeycloakService;
import com.programming.techie.microservice1.user.service.UserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired()
    private KeycloakService keycloakService;

    @Override
    public Response create(UserRegistrationRecord userRegistrationRecord) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userRegistrationRecord.username());
        userRepresentation.setEmail(userRegistrationRecord.email());
        userRepresentation.setFirstName(userRegistrationRecord.firstName());
        userRepresentation.setLastName(userRegistrationRecord.lastName());
        userRepresentation.setRequiredActions(Collections.singletonList("VERIFY_EMAIL"));
        userRepresentation.setEmailVerified(Boolean.FALSE);

        userRepresentation.setCredentials(createCredentials(userRegistrationRecord.password()));

        return keycloakService.createUser(userRepresentation);
    }

    private List<CredentialRepresentation> createCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        return Collections.singletonList(credentialRepresentation);
    }
}
