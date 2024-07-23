package org.example.dawayu_be.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PostDetailCommentsResponse {
    private String comment;
    private String commentUserNickname;
    private LocalDateTime commentCreatedAt;
}
