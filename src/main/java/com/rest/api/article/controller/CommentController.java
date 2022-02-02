package com.rest.api.article.controller;

import com.rest.api.article.dto.CommentWithoutPostDto;
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

    @GetMapping("/{id}/comments")
    public List<CommentWithoutPostDto> getAllCommentsByPostId(
            @PathVariable(value = "id") Article articleDb) {
        return commentService.getAllCommentsByPostId(articleDb);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public CommentWithoutPostDto getCommentByPostId(
            @PathVariable(value = "postId") Article article,
            @PathVariable(value = "commentId") Comment comment) {
        return commentService.getCommentByPost(article, comment);
    }

    @PostMapping("/{postId}/comments")
    public Comment addNewComment(
            @PathVariable(value = "postId") Article article,
            @RequestBody Comment comment) {
        return commentService.addNewComment(article, comment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public Comment removeCommentById(
            @PathVariable(value = "postId") Article article,
            @PathVariable(value = "commentId") Comment comment) {
        return commentService.removeComment(article, comment);
    }
}
