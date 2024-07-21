package org.example.dawayu_be.users.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    private String userId;
    private String password;
}
