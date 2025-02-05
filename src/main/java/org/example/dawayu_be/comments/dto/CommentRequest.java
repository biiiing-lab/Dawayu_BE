package org.example.dawayu_be.comments.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.posts.Posts;
import org.example.dawayu_be.comments.Comments;
import org.example.dawayu_be.users.Users;

@Data
@RequiredArgsConstructor
public class CommentRequest {
    private String content;

    public Comments toEntity(Users users, Posts posts) {
        return Comments.builder()
                .content(content)
                .userNo(users)
                .postNo(posts)
                .build();
    }
}
