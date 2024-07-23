package org.example.dawayu_be.likes;

import org.example.dawayu_be.posts.Posts;
import org.example.dawayu_be.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByArticleNoAndUserNo(Posts posts, Users users);
    boolean existsByUserNoAndArticleNo(Users users, Posts posts);
    List<Likes> findByUserNo(Users users);
}
