package com.rest.api.blog.aricle;

import com.rest.api.blog.dto.PostWithCommentsDto;
import com.rest.api.blog.dto.PostWithoutCommentDto;
import com.rest.api.blog.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public Article addNewArticle(@RequestBody Article article) {
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
    public Article editStar(@PathVariable(value = "id") Article article) {
        return articleService.toggle(article, true);
    }

    @DeleteMapping("/{id}/star")
    public Article deleteStar(@PathVariable(value = "id") Article article) {
        return articleService.toggle(article, false);
    }

    @GetMapping("/{id}")
    public PostWithCommentsDto getById(@PathVariable(value = "id") Long id) {
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