package org.example.dawayu_be.jwt;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.users.Users;
import org.example.dawayu_be.users.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUserId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원이 없습니다."));
    }

    private UserDetails createUserDetails(Users users) {
        return User.builder()
                .username(users.getUserId())
                .password(users.getPassword())
                .build();
    }
}
