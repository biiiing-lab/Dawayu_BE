package org.example.dawayu_be.users;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.users.dto.JoinRequest;
import org.example.dawayu_be.users.dto.LoginRequest;
import org.example.dawayu_be.global.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UsersController {

    private final UsersService usersService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<StatusResponse> login(@RequestBody LoginRequest loginRequest) {
        return usersService.login(loginRequest);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<StatusResponse> signUp(@RequestBody JoinRequest joinRequest) {
        return usersService.signUp(joinRequest);
    }

}
