package com.users.microserviceuser.dto;

public record RegistrationDto(
        String username,
        String email,
        String password
) {
}
