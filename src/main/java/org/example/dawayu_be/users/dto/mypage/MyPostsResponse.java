package org.example.dawayu_be.users.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class MyPostsResponse {
    private String title;
    private LocalDateTime createdAt;
}
