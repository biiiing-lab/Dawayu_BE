package org.example.dawayu_be.articles.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.replies.Comments;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
public class ArticleDetailResponse {
    private final String title;
    private final String content;
    private final String nickName;
    private final LocalDateTime createdAt;
    private final int likesCount;
    private final List<ArticleDetailCommentsResponse> comments;

}
