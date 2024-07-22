package org.example.dawayu_be.articles;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.example.dawayu_be.articles.dto.*;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.replies.CommentRepository;
import org.example.dawayu_be.replies.Comments;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final UsersRepository usersRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

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

    // 게시글 세부 내용 + 댓글
    @Transactional
    public ResponseEntity<ArticleDetailResponse> detail(Long articleNo) {
        Articles articles = articleRepository.findById(articleNo).orElseThrow();
        Users users = articles.getUserNo();
        List<Comments> articleComments = commentRepository.findAllByArticleNoOrderByCommentRegisterDateDesc(articles);

        // 댓글을 DTO로 변환
        List<ArticleDetailCommentsResponse> commentResponses = articleComments.stream()
                .map(comment -> ArticleDetailCommentsResponse.builder()
                        .comment(comment.getContent())
                        .commentUserNickname(comment.getUserNo().getNickName())
                        .commentCreatedAt(comment.getCommentRegisterDate())
                        .build())
                .collect(Collectors.toList());

        ArticleDetailResponse response = ArticleDetailResponse.builder()
                .title(articles.getTitle())
                .content(articles.getContent())
                .likesCount(articles.getLikesCount())
                .createdAt(articles.getPostRegisterDate())
                .nickName(users.getNickName())
                .comments(commentResponses)
                .build();

        return ResponseEntity.ok(response);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ArticleAllResponse>> all() {
        List<Articles> articles = articleRepository.findAllByOrderByPostRegisterDateDesc();
        List<ArticleAllResponse> responses = articles.stream()
                .map(article -> new ArticleAllResponse(
                        article.getTitle(),
                        article.getUserNo().getNickName(),
                        article.getPostRegisterDate()))
                .toList();

        return ResponseEntity.ok(responses);
    }

    @Transactional
    public ResponseEntity<StatusResponse> update(Long articleNo, ArticleUpdateRequest updateRequest) {
        Articles articles = articleRepository.findById(articleNo).orElseThrow();

        if (checkAuth(articles.getUserNo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "수정할 수 없습니다."));
        }

        articles.update(updateRequest.getTitle(), updateRequest.getContent());
       return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "게시글 수정 성공"));
    }

    @Transactional
    public ResponseEntity<StatusResponse> delete(Long articleNo) {
        Articles articles = articleRepository.findById(articleNo).orElseThrow();
        if (checkAuth(articles.getUserNo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "삭제할 수 없습니다."));
        }

        articleRepository.deleteById(articleNo);
        return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "게시글 삭제 성공"));
    }

    private boolean checkAuth(Users users) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users realUsers = usersRepository.findByUserId(userDetails.getUsername()).orElseThrow();

        if(realUsers == users) {
            return false;
        } else {
            return true;
        }
    }
}
