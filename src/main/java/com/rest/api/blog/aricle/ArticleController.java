package com.rest.api.blog.aricle;

import com.rest.api.blog.dto.Post;
import com.rest.api.blog.dto.PostWithCommentsDto;
import com.rest.api.blog.dto.PostWithoutCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleDto articleDto;

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
    void deleteArticle(
            @PathVariable(value = "id") Long articleId) {
        articleService.removeArticle(articleId);
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
        return articleDto.getAllCommentsByPostId(id);
    }

    @GetMapping("/star")
    public List<Article> getTop() {
        return articleService.getByStar();
    }

    @GetMapping("/comments")
    public List<Post> getArticlesWithComments() {
        return articleDto.getAll();
    }

    @GetMapping("/full")
    public List<Post> getAllArticlesWithComments() {
        return articleDto.getFullPost();
    }

    @GetMapping("/")
    public List<PostWithoutCommentDto> getAll() {
        return articleDto.getFullArticles();
    }
}