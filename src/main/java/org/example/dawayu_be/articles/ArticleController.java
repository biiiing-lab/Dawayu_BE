package org.example.dawayu_be.articles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.example.dawayu_be.articles.dto.ArticleAllResponse;
import org.example.dawayu_be.articles.dto.ArticleDetailResponse;
import org.example.dawayu_be.articles.dto.ArticleRequest;
import org.example.dawayu_be.articles.dto.ArticleUpdateRequest;
import org.example.dawayu_be.global.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;

    // 등록
    @PostMapping("/posts")
    public ResponseEntity<StatusResponse> register(@RequestBody ArticleRequest articleRequest) {
        return articleService.register(articleRequest);
    }

    // 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<List<ArticleAllResponse>> all() {
        return articleService.all();
    }

    // 세부 조회
    @GetMapping("/posts/{articleNo}")
    public ResponseEntity<ArticleDetailResponse> detail(@PathVariable Long articleNo) {
        return articleService.detail(articleNo);
    }

    // 수정
    @PutMapping("/posts/{articleNo}")
    public ResponseEntity<StatusResponse> update(@PathVariable Long articleNo,
                                                 @RequestBody ArticleUpdateRequest articleUpdateRequest) {
        return articleService.update(articleNo, articleUpdateRequest);
    }

    // 삭제
    @DeleteMapping("posts/{articleNo}")
    public ResponseEntity<StatusResponse> delete(@PathVariable Long articleNo) {
        return articleService.delete(articleNo);
    }

}
