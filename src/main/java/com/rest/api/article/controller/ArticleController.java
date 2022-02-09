package com.rest.api.article.controller;

import com.rest.api.article.dto.CommentWithoutPostDto;
import com.rest.api.article.dto.PostWithCommentsDto;
import com.rest.api.article.dto.PostWithoutCommentDto;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    Long deleteArticle(
            @PathVariable(value = "id") Long articleId) {
        return articleService.removeArticle(articleId);
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

//    @GetMapping
//    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN', 'ROLE_USER')")
//    public List<PostWithoutCommentDto> filterBy(
//            @RequestParam Optional<String> title,
//            @RequestParam Optional<String> sort,
//            @RequestParam Optional<Integer> page) {
//        return articleService.filteredBy(sort, title, page);
//    }

    @GetMapping("/{id}")
    public PostWithCommentsDto getById(
            @PathVariable(value = "id") Long id) {
        return articleService.getAllCommentsByPostId(id);
    }

    @GetMapping("/star")
    public List<Article> getTop() {
        return articleService.getByStar();
    }

    @GetMapping
    public List<PostWithoutCommentDto> getFullArticles() {
        return articleService.getAll();
    }

    @GetMapping("/full")
    public List<List<Comment>> getAll() {
        return articleService.getFullArticles();
    }

}
//TODO With comments, tags in list, user:null
// http://localhost:8080/api/v1/posts/full
// http://localhost:8080/api/v1/posts/tags 400
// http://localhost:8080/api/v1/posts/full
