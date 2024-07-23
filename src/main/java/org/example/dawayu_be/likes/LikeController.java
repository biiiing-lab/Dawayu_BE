package org.example.dawayu_be.likes;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.global.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 좋아요 등록
    @PostMapping("/posts/{postNo}/like")
    public ResponseEntity<StatusResponse> register(@PathVariable Long postNo) {
        return likeService.register(postNo);
    }

    // 좋아요 삭제
    @DeleteMapping("/posts/{postNo}/dislike")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long postNo) {
        return likeService.delete(postNo);
    }
}
