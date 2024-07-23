package org.example.dawayu_be.posts.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.posts.Posts;
import org.example.dawayu_be.users.Users;

@Data
@RequiredArgsConstructor
public class PostRequest {
    private String title;
    private String content;

    public Posts toEntity(Users users) {
        return Posts.builder()
                .title(title)
                .content(content)
                .userNo(users)
                .build();
    }

}
