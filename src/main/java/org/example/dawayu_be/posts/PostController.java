package org.example.dawayu_be.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dawayu_be.posts.dto.PostsAllResponse;
import org.example.dawayu_be.posts.dto.PostDetailResponse;
import org.example.dawayu_be.posts.dto.PostRequest;
import org.example.dawayu_be.posts.dto.PostUpdateRequest;
import org.example.dawayu_be.global.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    // 등록
    @PostMapping("/posts")
    public ResponseEntity<StatusResponse> register(@RequestBody PostRequest postRequest) {
        return postService.register(postRequest);
    }

    // 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostsAllResponse>> all() {
        return postService.all();
    }

    // 세부 조회
    @GetMapping("/posts/{postNo}")
    public ResponseEntity<PostDetailResponse> detail(@PathVariable Long postNo) {
        return postService.detail(postNo);
    }

    // 수정
    @PutMapping("/posts/{postNo}")
    public ResponseEntity<StatusResponse> update(@PathVariable Long postNo,
                                                 @RequestBody PostUpdateRequest postUpdateRequest) {
        return postService.update(postNo, postUpdateRequest);
    }

    // 삭제
    @DeleteMapping("posts/{postNo}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long postNo) {
        return postService.delete(postNo);
    }

}
