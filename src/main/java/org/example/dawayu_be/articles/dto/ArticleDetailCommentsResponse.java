package org.example.dawayu_be.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ArticleDetailCommentsResponse {
    private String comment;
    private String commentUserNickname;
    private LocalDateTime commentCreatedAt;
}
