package org.example.dawayu_be.comments;

import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.global.StatusResponse;
import org.example.dawayu_be.comments.dto.CommentRequest;
import org.example.dawayu_be.comments.dto.CommentUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 등록
    @PostMapping("/posts/{postNo}/comments")
    public ResponseEntity<StatusResponse> register(@PathVariable Long postNo, @RequestBody CommentRequest commentRequest) {
        return commentService.register(postNo, commentRequest);
    }

    // 수정
    @PutMapping("/posts/{postNo}/update-comment/{commentNo}")
    public ResponseEntity<StatusResponse> update(@PathVariable Long postNo,
                                                 @PathVariable Long commentNo,
                                                 @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.update(commentNo, commentUpdateRequest);
    }

    // 삭제
    @DeleteMapping("/posts/{postNo}/delete-comment/{commentNo}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long postNo,
                                                 @PathVariable Long commentNo) {
        return commentService.delete(commentNo);
    }
}
