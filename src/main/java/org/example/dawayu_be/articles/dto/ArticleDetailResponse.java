package org.example.dawayu_be.articles.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
public class ArticleDetailResponse {
    private final String title;
    private final String content;
    private final String nickName;
    private final LocalDateTime createdAt;
    private final int likesCount;
}
