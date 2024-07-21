package org.example.dawayu_be.articles.dto;

import lombok.*;
import org.example.dawayu_be.articles.ArticleService;

@Getter
@NoArgsConstructor
public class ArticleUpdateRequest {
    private String title;
    private String content;

    @Builder
    public ArticleUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
