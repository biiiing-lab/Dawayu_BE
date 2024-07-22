package org.example.dawayu_be.likes;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.comments.dto.CommentRequest;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.likes.dto.LikeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 좋아요 등록
    @PostMapping("/posts/{articleNo}/like")
    public ResponseEntity<StatusResponse> register(@PathVariable Long articleNo) {
        return likeService.register(articleNo);
    }

    // 좋아요 삭제
    @DeleteMapping("/posts/{articleNo}/dislike")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long articleNo) {
        return likeService.delete(articleNo);
    }
}
