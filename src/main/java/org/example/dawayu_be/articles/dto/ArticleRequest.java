package org.example.dawayu_be.articles.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.dawayu_be.articles.Articles;
import org.example.dawayu_be.users.Users;

@Data
@RequiredArgsConstructor
public class ArticleRequest {
    private String title;
    private String content;

    public Articles toEntity(Users users) {
        return Articles.builder()
                .title(title)
                .content(content)
                .userNo(users)
                .build();
    }

}
