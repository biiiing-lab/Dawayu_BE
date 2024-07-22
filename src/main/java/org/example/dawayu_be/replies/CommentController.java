package org.example.dawayu_be.replies;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.replies.dto.CommentRequest;
import org.example.dawayu_be.replies.dto.CommentUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 등록
    @PostMapping("/posts/{articleNo}/comments")
    public ResponseEntity<StatusResponse> register(@PathVariable Long articleNo, @RequestBody CommentRequest commentRequest) {
        return commentService.register(articleNo, commentRequest);
    }

    // 수정
    @PutMapping("/posts/{articleNo}/update-comment/{commentNo}")
    public ResponseEntity<StatusResponse> update(@PathVariable Long articleNo,
                                                 @PathVariable Long commentNo,
                                                 @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.update(commentNo, commentUpdateRequest);
    }

    // 삭제
    @DeleteMapping("/posts/{articleNo}/delete-comment/{commentNo}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long articleNo,
                                                 @PathVariable Long commentNo) {
        return commentService.delete(commentNo);
    }
}
