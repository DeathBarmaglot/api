package com.rest.api.article.controller;

import com.rest.api.article.ArticleNotFoundException;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import com.rest.api.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;


    @RequestMapping(path = "/api/v1/posts", method = RequestMethod.POST)
    public ResponseEntity<Article> addNewArticle(@RequestBody Article article) {
        Article newArticle = new Article(article.getTitle(), article.getContent());
        articleService.saveArticle(newArticle);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/posts").toUriString());
        return ResponseEntity.created(uri).body(articleService.getArticle(newArticle.getTitle()));
    }

    @PutMapping("/api/v1/posts/{id}")
    public Article replaceEmployee(@RequestBody Article newArticle, @PathVariable Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(newArticle.getTitle());
                    article.setContent(newArticle.getContent());
                    article.setStar(newArticle.getStar());
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return articleRepository.save(newArticle);
                });
    }

    @DeleteMapping("/api/v1/posts/{id}")
    void deleteArticle(@PathVariable Long id) {
        articleService.removeArticle(id);
    }

    @PostMapping("/api/v1/posts/{articleId}/comments")
    public Comment addNewComment(@PathVariable(value = "articleId") Long articleId, @RequestBody Comment comment) {
        return articleRepository.findById(articleId).map(article -> {
            comment.setArticles(article);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ArticleNotFoundException(articleId));
    }

}