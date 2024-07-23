package org.example.dawayu_be.posts.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
public class PostDetailResponse {
    private final String title;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
    private final int likesCount;
    private final List<PostDetailCommentsResponse> comments;

}
