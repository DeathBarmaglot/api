package com.rest.api.article.controller;

import com.rest.api.article.dto.PostWithCommentsDto;
import com.rest.api.article.dto.PostWithoutCommentDto;
import com.rest.api.article.entity.Article;
import com.rest.api.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class ArticleController {

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
        return articleService.updateArticle(articleDb, article);
    }

    @DeleteMapping("/{id}")
    void deleteArticle(
            @PathVariable(value = "id") Article article) {
        articleService.removeArticle(article);
    }

    @PutMapping("/{id}/star")
    public Article editStar(
            @PathVariable(value = "id") Article article) {
        return articleService.toggle(article, true);
    }

    @DeleteMapping("/{id}/star")
    public Article deleteStar(
            @PathVariable(value = "id") Article article) {
        return articleService.toggle(article, false);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public List<PostWithoutCommentDto> filterBy(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<Integer> page) {
        return articleService.filteredBy(sort, title, page);
    }

    @GetMapping("/{id}")
    public PostWithCommentsDto getById(
            @PathVariable(value = "id") Article articleDb) {
        return articleService.getPostWithComment(articleDb);
    }

    @GetMapping("/star")
    public List<Article> getTop() {
        return articleService.getByStar();
    }

    @GetMapping("/full")
    public List<PostWithCommentsDto> getFullArticles() {
        return articleService.getFullArticle();
    }
}
