package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
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
    public List<Comment> getAllCommentsByPostId(
            @PathVariable(value = "id") Article article) {
        return commentService.getAllCommentsByPostId(article);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getCommentByPostId(
            @PathVariable(value = "postId") Article article,
            @PathVariable(value = "commentId") Comment comment) {
        return comment;
    }

    @GetMapping("/{postId}/full")
    public List<Comment> getFullCommentsByPostId(
            @PathVariable(value = "postId") Article article) {
        return commentService.getAllCommentsByPostId(article);
    }

    @PostMapping("/{postId}/comments")
    public Comment addNewComment(
            @PathVariable(value = "postId") Article article,
            @RequestBody Comment comment) {
        return commentService.addNewComment(article, comment);
    }
}
