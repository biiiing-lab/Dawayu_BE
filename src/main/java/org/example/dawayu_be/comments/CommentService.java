package org.example.dawayu_be.comments;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.articles.ArticleRepository;
import org.example.dawayu_be.articles.Articles;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.comments.dto.CommentRequest;
import org.example.dawayu_be.comments.dto.CommentUpdateRequest;
import org.example.dawayu_be.users.Users;
import org.example.dawayu_be.users.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UsersRepository usersRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    @Transactional
    public ResponseEntity<StatusResponse> register(Long articleNo, CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users users = usersRepository.findByUserId(userDetails.getUsername()).orElseThrow();
        Articles articles = articleRepository.findById(articleNo).orElseThrow();

        Comments comments = commentRequest.toEntity(users, articles);
        commentRepository.save(comments);

        return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "댓글 등록 성공"));

    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<StatusResponse> update(Long commentNo, CommentUpdateRequest updateRequest) {
        Comments comments = commentRepository.findById(commentNo).orElseThrow();

        if (checkAuth(comments.getUserNo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "댓글 수정할 수 없습니다."));
        }

        comments.update(updateRequest.getContent());
        return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "댓글 수정 성공"));
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<StatusResponse> delete(Long commentNo) {
        Comments comments = commentRepository.findById(commentNo).orElseThrow();

        if (checkAuth(comments.getUserNo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "댓글을 삭제할 수 없습니다."));
        }

        commentRepository.deleteById(commentNo);
        return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "댓글 삭제 성공"));

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
