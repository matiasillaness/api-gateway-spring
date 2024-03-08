package com.users.microserviceuser.dto;

public record AuthenticationDto(
        String email,
        String password
) {
}
