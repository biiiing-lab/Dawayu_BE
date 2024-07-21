package org.example.dawayu_be.users.dto;

import lombok.*;
import org.example.dawayu_be.users.Users;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@RequiredArgsConstructor
public class JoinRequest {
    private String userId;
    private String password;
    private String nickName;
    private String email;

    public Users toEntity() {
        return Users.builder()
                .userId(userId)
                .password(password)
                .nickName(nickName)
                .email(email)
                .build();
    }

    public void password(String password) {
        this.password= password;
    }
}
