package org.example.dawayu_be.replies;

import org.example.dawayu_be.articles.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByArticleNo(Articles articles);
}
