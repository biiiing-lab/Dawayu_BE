package org.example.dawayu_be.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
public class ArticleAllResponse {
    private String title;
    private String nickName;
    private LocalDateTime createdAt;
}
