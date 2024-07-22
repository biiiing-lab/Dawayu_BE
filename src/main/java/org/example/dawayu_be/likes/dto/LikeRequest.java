package org.example.dawayu_be.likes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.articles.Articles;
import org.example.dawayu_be.users.Users;

@Data
@RequiredArgsConstructor
public class LikeRequest {
    Long articleNo;
}
