package org.example.dawayu_be.articles;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.example.dawayu_be.articles.dto.ArticleDetailResponse;
import org.example.dawayu_be.articles.dto.ArticleRequest;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.users.Users;
import org.example.dawayu_be.users.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final UsersRepository usersRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public ResponseEntity<StatusResponse> register(ArticleRequest articleRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users users = usersRepository.findByUserId(userDetails.getUsername()).orElseThrow();

        Articles articles = articleRequest.toEntity(users);
        articleRepository.save(articles);

        return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "게시글 등록 성공"));
    }

    @Transactional
    public ResponseEntity<ArticleDetailResponse> detail(Long articleNo) {
        Articles articles = articleRepository.findById(articleNo).orElseThrow();
        Users users = articles.getUserNo();

        ArticleDetailResponse response = ArticleDetailResponse.builder()
                .title(articles.getTitle())
                .content(articles.getContent())
                .likesCount(articles.getLikesCount())
                .createdAt(articles.getPostRegisterDate())
                .nickName(users.getNickName())
                .build();

        return ResponseEntity.ok(response);

    }
}
