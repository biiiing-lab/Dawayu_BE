package org.example.dawayu_be.posts.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {
    private String title;
    private String content;

    @Builder
    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
