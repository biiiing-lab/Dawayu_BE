package org.example.dawayu_be.likes;

import org.example.dawayu_be.articles.Articles;
import org.example.dawayu_be.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByArticleNoAndUserNo(Articles articles, Users users);
    boolean existsByUserNoAndArticleNo(Users users, Articles articles);
}
