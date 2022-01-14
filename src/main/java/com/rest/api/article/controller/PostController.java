package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.service.ArticleService;
import com.rest.api.article.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @RequestMapping(path = "/api/v1/posts", method = RequestMethod.POST)
    public ResponseEntity<Optional<Article>> addNewArticle(@RequestBody Article article) {
        return articleService.addNewArticle(article);}

    @PutMapping("/api/v1/posts/{id}")
    public Article replaceArticle(@RequestBody Article newArticle, @PathVariable Long id) {
        return articleService.updateArticle(newArticle, id);}

    @DeleteMapping("/api/v1/posts/{id}")
    void deleteArticle(@PathVariable Long id) {
        articleService.removeArticle(id);
    }

    @PostMapping("/api/v1/posts/{articleId}/comments")
    public Comment addNewComment(@PathVariable(value = "articleId") Long articleId, @RequestBody Comment comment) {
        return commentService.addNewComment(articleId, comment);}

    @PutMapping("/api/v1/posts/{id}/star")
    public Article editStar(@PathVariable Long id) {
        return articleService.toggle(id, true);
    }

    @DeleteMapping("/api/v1/posts/{id}/star")
    public Article deleteStar(@PathVariable Long id) {
        return articleService.toggle(id, false);
    }
}
