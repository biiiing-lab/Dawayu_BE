package org.example.dawayu_be.users;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.jwt.JwtTokenProvider;
import org.example.dawayu_be.users.dto.JoinRequest;
import org.example.dawayu_be.users.dto.LoginRequest;
import org.example.dawayu_be.users.dto.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final JwtTokenProvider tokenProvider;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public ResponseEntity<StatusResponse> login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.createAccessToken(authentication);
    }

    @Transactional
    public ResponseEntity<StatusResponse> signUp(JoinRequest joinRequest) {
        // 유효성 검증
        if(usersRepository.existsByUserId(joinRequest.getUserId())) {
            return ResponseEntity.ok(new StatusResponse(HttpStatus.NO_CONTENT.value(), "이미 사용중인 사용자 이름입니다."));
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        joinRequest.password(encodedPassword);
        Users users = joinRequest.toEntity();

        usersRepository.save(users);

        return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "회원가입 성공"));
    }
}
