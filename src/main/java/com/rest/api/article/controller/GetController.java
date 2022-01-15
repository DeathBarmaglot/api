package com.rest.api.article.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.entity.Views;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import com.rest.api.article.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class GetController {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    @GetMapping("/{id}")
    @JsonView(Views.IdTitle.class)
    public Article getById(
            @PathVariable(value = "id") Article article) {
        return article;
    }

    @GetMapping
    public Page<Article> filterByTitle(
            @RequestParam Optional<String> sort,
            @RequestParam Optional<Integer> page) {
        return articleRepository.findAll(PageRequest.of(
                page.orElse(0), 100, Sort.Direction.ASC, sort.orElse("id")));
    }

    @GetMapping("/{id}/comments")
    @JsonView(Views.WithDate.class)
    public List<Comment> getAllCommentsByPostId(
            @PathVariable(value = "id") Article article) {
        return commentRepository.findByArticle(article, Sort.unsorted());
    }

    @GetMapping("/{postId}/comments/{commentId}")
    @JsonView(Views.FullArticle.class)
    public Comment getCommentByPostId(
            @PathVariable(value = "postId") Article article,
            @PathVariable(value = "commentId") Comment comment) {
        return comment;
    }

    @GetMapping("/star")
    public List<Article> getTop() {
        return articleRepository.findAll().stream().filter(article ->
                Boolean.TRUE.equals(article.isStar())).collect(Collectors.toList());
    }

    @GetMapping("/{postId}/full")
    public List<Object> getFullCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.allCommentByPost(postId);
    }
}
