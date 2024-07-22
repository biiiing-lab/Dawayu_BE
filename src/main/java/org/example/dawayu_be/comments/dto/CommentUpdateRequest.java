package org.example.dawayu_be.comments.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {
    private String content;

    @Builder
    public CommentUpdateRequest(String content) {
        this.content = content;
    }
}
