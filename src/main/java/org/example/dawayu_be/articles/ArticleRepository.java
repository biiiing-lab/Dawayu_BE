package org.example.dawayu_be.articles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Articles, Long> {
    List<Articles> findAllByOrderByPostRegisterDateDesc();
}
