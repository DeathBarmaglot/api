package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    private final ArticleService articleService;

    @PostMapping
    public Article addNewArticle(
            @RequestBody Article article) {
        return articleService.addNewArticle(article);
    }

    @PutMapping("/{id}")
    public Article replaceArticle(
            @PathVariable(value = "id") Article articleDb,
            @RequestBody Article article) {
        BeanUtils.copyProperties(articleDb, article, "id");
        return articleService.addNewArticle(articleDb);
    }

    @DeleteMapping("/{id}")
    void deleteArticle(
            @PathVariable(value = "id") Article article) {
        articleService.removeArticle(article);
    }

    @PutMapping("/{id}/star")
    public Article editStar(
            @PathVariable Long id) {
        return articleService.toggle(id, true);
    }

    @DeleteMapping("/{id}/star")
    public Article deleteStar(
            @PathVariable Long id) {
        return articleService.toggle(id, false);
    }

    @GetMapping
    public List<Object> filterBy(
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> title,
            @RequestParam Optional<Integer> page) {
        return articleService.filteredBy(sort, title, page);

    }
}
