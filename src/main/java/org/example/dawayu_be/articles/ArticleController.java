package org.example.dawayu_be.articles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dawayu_be.articles.dto.ArticleDetailResponse;
import org.example.dawayu_be.articles.dto.ArticleRequest;
import org.example.dawayu_be.global.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/posts")
    public ResponseEntity<StatusResponse> register(@RequestBody ArticleRequest articleRequest) {
        return articleService.register(articleRequest);
    }

    @GetMapping("/posts/{articleNo}")
    public ResponseEntity<ArticleDetailResponse> detail(@PathVariable Long articleNo) {
        return articleService.detail(articleNo);
    }

}
