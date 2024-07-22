package org.example.dawayu_be.comments.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.articles.Articles;
import org.example.dawayu_be.comments.Comments;
import org.example.dawayu_be.users.Users;

@Data
@RequiredArgsConstructor
public class CommentRequest {
    private String content;

    public Comments toEntity(Users users, Articles articles) {
        return Comments.builder()
                .content(content)
                .userNo(users)
                .articleNo(articles)
                .build();
    }
}
