package com.programming.techie.microservice1.user.service;

import com.programming.techie.microservice1.user.dto.UserRegistrationRecord;
import jakarta.ws.rs.core.Response;

public interface UserService {
    Response create(UserRegistrationRecord userRegistrationRecord);
}
