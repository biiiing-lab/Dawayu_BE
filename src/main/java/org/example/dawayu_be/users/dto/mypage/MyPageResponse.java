package org.example.dawayu_be.users.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class MyPageResponse {
    List<MyPostsResponse> posts;
    List<MyLikesResponse> liked;
}
