package com.rest.api.article.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.entity.Views;
import com.rest.api.article.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public Article getById(
            @PathVariable(value = "id") Article article) {
        return article;
    }

    @GetMapping("/{id}/comments")
    @JsonView(Views.WithDate.class)
    public List<Comment> getAllCommentsByPostId(
            @PathVariable(value = "id") Article article) {
        return commentService.getAllCommentsByPostId(article);
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
        return commentService.getByStar();
    }

    @GetMapping("/{postId}/full")
    public List<Object> getFullCommentsByPostId(
            @PathVariable(value = "postId") Long postId) {
        return commentService.allCommentByPost(postId);
    }

    @PostMapping("/{postId}/comments")
    public Comment addNewComment(
            @PathVariable(value = "postId") Long articleId,
            @RequestBody Comment comment) {
        return commentService.addNewComment(articleId, comment);
    }
}
