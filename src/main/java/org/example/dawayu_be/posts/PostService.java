package org.example.dawayu_be.posts;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.posts.dto.*;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.comments.CommentRepository;
import org.example.dawayu_be.comments.Comments;
import org.example.dawayu_be.users.Users;
import org.example.dawayu_be.users.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<StatusResponse> register(PostRequest postRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users users = usersRepository.findByUserId(userDetails.getUsername()).orElseThrow();

        Posts posts = postRequest.toEntity(users);
        postRepository.save(posts);

        return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "게시글 등록 성공"));
    }

    // 게시글 세부 내용 + 댓글
    @Transactional
    public ResponseEntity<PostDetailResponse> detail(Long articleNo) {
        Posts posts = postRepository.findById(articleNo).orElseThrow();
        Users users = posts.getUserNo();
        List<Comments> articleComments = commentRepository.findAllByPostNoOrderByCommentRegisterDateDesc(posts);

        // 댓글을 DTO로 변환
        List<PostDetailCommentsResponse> commentResponses = articleComments.stream()
                .map(comment -> PostDetailCommentsResponse.builder()
                        .comment(comment.getContent())
                        .commentUserNickname(comment.getUserNo().getNickName())
                        .commentCreatedAt(comment.getCommentRegisterDate())
                        .build())
                .collect(Collectors.toList());

        PostDetailResponse response = PostDetailResponse.builder()
                .title(posts.getTitle())
                .content(posts.getContent())
                .likesCount(posts.getLikesCount())
                .createdAt(posts.getPostRegisterDate())
                .username(users.getNickName())
                .comments(commentResponses)
                .build();

        return ResponseEntity.ok(response);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<PostsAllResponse>> all() {
        List<Posts> post = postRepository.findAllByOrderByPostRegisterDateDesc();

        List<PostsAllResponse> responses = post.stream()
                .map(posts -> new PostsAllResponse(
                        posts.getTitle(),
                        posts.getPostRegisterDate(),
                        posts.getUserNo().getNickName(),
                        posts.getLikesCount()
                ))
                .toList();

        return ResponseEntity.ok(responses);
    }

    @Transactional
    public ResponseEntity<StatusResponse> update(Long postNo, PostUpdateRequest updateRequest) {
        Posts posts = postRepository.findById(postNo).orElseThrow();

        if (checkAuth(posts.getUserNo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "수정할 수 없습니다."));
        }

        posts.update(updateRequest.getTitle(), updateRequest.getContent());
       return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "게시글 수정 성공"));
    }

    @Transactional
    public ResponseEntity<StatusResponse> delete(Long postNo) {
        Posts posts = postRepository.findById(postNo).orElseThrow();
        if (checkAuth(posts.getUserNo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StatusResponse(HttpStatus.UNAUTHORIZED.value(), "삭제할 수 없습니다."));
        }

        postRepository.deleteById(postNo);
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
