package com.rest.api.article;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @RequestMapping(path = "/api/v1/posts", method = RequestMethod.GET)
    public ResponseEntity<List<Article>> findAllPost() {
        return ResponseEntity.ok().body(articleService.getArticles());
    }

    @RequestMapping(path = "/api/v1/posts", method = RequestMethod.POST)
    public ResponseEntity<Article> addNewArticle(@RequestBody Article article) {
        Article newArticle = new Article(article.getTitle(), article.getContent());
        articleService.saveArticle(newArticle);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/posts").toUriString());
        return ResponseEntity.created(uri).body(articleService.getArticle(newArticle.getTitle()));
    }

    @RequestMapping(path = "/api/v1/posts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<List<Article>> editArticles(@PathVariable long id, @RequestBody Article article) {
        Article existed = articleService.getById(id);
        Assert.notNull(existed, "article not found");
        existed.setTitle(article.getTitle());
        existed.setContent(article.getContent());
        articleService.saveArticle(existed);
        return ResponseEntity.ok().body(articleService.getArticles());
    }

    @RequestMapping(path = "/api/v1/posts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<List<Article>> deleteArticle(@PathVariable long id, @RequestBody Article article) {
        articleService.removeArticle(id);
        return ResponseEntity.ok().body(articleService.getArticles());
    }
}
