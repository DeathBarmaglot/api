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
            @PathVariable(value = "id") Article articleDb) {
        return articleDb;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getAllCommentsByPostId(
            @PathVariable(value = "id") Article articleDb) {
        return articleDb.getComments();
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getCommentByPostId(
            @PathVariable(value = "postId") Article articleDb,
            @PathVariable(value = "commentId") Long comment_id) {
        return commentService.getById(comment_id);
    }

    @GetMapping("/{postId}/full")
    public List<Comment> getFullCommentsByPostId(
            @PathVariable(value = "postId") Article articleDb) {
        return articleDb.getComments();
    }

    @PostMapping("/{postId}/comments")
    public Article addNewComment(
            @PathVariable(value = "postId") Article article,
            @RequestBody Comment comment) {
        Article savedComment = commentService.addNewComment(article, comment);
        return savedComment;
    }
}
