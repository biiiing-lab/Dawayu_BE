package org.example.dawayu_be.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class PostsAllResponse {
    private Long postNo;
    private String title;
    private LocalDateTime createdAt;
    private String username;
    private int likeCount;
}
