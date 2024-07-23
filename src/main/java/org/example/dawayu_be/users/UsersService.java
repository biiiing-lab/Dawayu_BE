package org.example.dawayu_be.users;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.posts.PostRepository;
import org.example.dawayu_be.posts.Posts;
import org.example.dawayu_be.jwt.JwtTokenProvider;
import org.example.dawayu_be.likes.LikeRepository;
import org.example.dawayu_be.likes.Likes;
import org.example.dawayu_be.users.dto.JoinRequest;
import org.example.dawayu_be.users.dto.LoginRequest;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.users.dto.mypage.MyLikesResponse;
import org.example.dawayu_be.users.dto.mypage.MyPageResponse;
import org.example.dawayu_be.users.dto.mypage.MyPostsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final JwtTokenProvider tokenProvider;
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
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

    @Transactional
    public ResponseEntity<MyPageResponse> all() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users realUsers = usersRepository.findByUserId(userDetails.getUsername()).orElseThrow();

        List<Posts> articles = postRepository.findByUserNo(realUsers); // 현재 로그인 된 유저 정보로 작성한 게시글 찾기
        List<Likes> likes = likeRepository.findByUserNo(realUsers) ; // 현재 로그인 된 유저 정보로 좋아요 한 게시글 찾기

        List<MyPostsResponse> postsResponses = articles.stream()
                .map(myPosts -> new MyPostsResponse(
                        myPosts.getTitle(),
                        myPosts.getPostRegisterDate()
                )).collect(Collectors.toList());

        List<MyLikesResponse> likesResponses = likes.stream()
                .map(myLikes -> new MyLikesResponse(
                        myLikes.getPostNo().getTitle()
                )).collect(Collectors.toList());

        MyPageResponse pageResponse = MyPageResponse.builder()
                .posts(postsResponses)
                .likedPosts(likesResponses)
                .build();

        return ResponseEntity.ok(pageResponse);

    }
}
