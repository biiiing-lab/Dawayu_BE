package org.example.dawayu_be.likes;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.articles.ArticleRepository;
import org.example.dawayu_be.articles.Articles;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.likes.dto.LikeRequest;
import org.example.dawayu_be.users.Users;
import org.example.dawayu_be.users.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UsersRepository usersRepository;
    private final ArticleRepository articleRepository;
    private final LikeRepository likeRepository;

    // 등록
    @Transactional
    public ResponseEntity<StatusResponse> register(Long articleNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users users = usersRepository.findByUserId(userDetails.getUsername()).orElseThrow();
        Articles articles = articleRepository.findById(articleNo).orElseThrow();

        boolean likesCheck = likeRepository.existsByUserNoAndArticleNo(users, articles);

        if(likesCheck) {
            return  ResponseEntity.ok(new StatusResponse(HttpStatus.BAD_GATEWAY.value(), "이미 좋아요를 눌렀습니다."));
        }

        Likes likes = Likes.builder()
                .userNo(users)
                .articleNo(articles)
                .build();
        likeRepository.save(likes);
        articles.likeCount(); // 좋아요 증가

        return  ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "좋아요하였습니다."));

    }

    @Transactional
    public ResponseEntity<StatusResponse> delete(Long articleNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users realUsers = usersRepository.findByUserId(userDetails.getUsername()).orElseThrow();

        Articles articles = articleRepository.findById(articleNo).orElseThrow();
        Optional<Likes> likes = likeRepository.findByArticleNoAndUserNo(articles, realUsers);

        if (likes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusResponse(HttpStatus.NOT_FOUND.value(), "좋아요 기록을 찾을 수 없습니다."));
        }

        likeRepository.deleteById(likes.get().getLikesNo());
        articles.discountLike(); // 좋아요 삭제

        return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "좋아요가 삭제되었습니다."));
    }
}
