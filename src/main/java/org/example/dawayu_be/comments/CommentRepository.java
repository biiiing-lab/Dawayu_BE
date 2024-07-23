package org.example.dawayu_be.comments;

import org.example.dawayu_be.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByPostNo(Posts posts);
    List<Comments> findAllByPostNoOrderByCommentRegisterDateDesc(Posts article);
}
