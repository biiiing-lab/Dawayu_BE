package org.example.dawayu_be.users.dto;

import lombok.*;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}
