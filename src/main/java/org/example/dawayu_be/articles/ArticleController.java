package org.example.dawayu_be.articles;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.dawayu_be.articles.dto.ArticleRequest;
import org.example.dawayu_be.global.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/post")
    public ResponseEntity<StatusResponse> register(@RequestBody ArticleRequest articleRequest) {
        return articleService.register(articleRequest);
    }
}
