package com.rest.api.article;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class ArticleGetController {

    private final ArticleRepository articleRepository;

    @GetMapping("/{id}")
    public Article getById(@PathVariable Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @GetMapping
    public Page<Article> filterByTitle(
            @PathVariable String title,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<Integer> page) {
        return articleRepository.findAll(PageRequest.of(
                page.orElse(0), 100,
                Sort.Direction.ASC, sort.orElse("id")));
    }
}
