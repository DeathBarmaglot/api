package com.rest.api.blog.comment;

import com.rest.api.blog.dto.CommentWithoutPostDto;
import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.comment.Comment;
import com.rest.api.blog.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{articleId}/comments/{commentId}")
    public List<Comment> getCommentByPostId(
            @PathVariable(value = "articleId") Long article_id,
            @PathVariable(value = "commentId") Long comment_id) {
        return commentService.getCommentByPost(article_id, comment_id);
    }

    @PostMapping("/{articleId}/comments")
    public Comment addNewComment(
            @PathVariable(value = "articleId") Article article,
            @RequestBody Comment comment) {
        return commentService.addNewComment(article, comment);
    }

    @GetMapping("/{id}/comments")
    public List<CommentWithoutPostDto> getAllCommentsByPostId(
            @PathVariable(value = "id") Long id) {
        return commentService.getAllCommentsByPostId(id);
    }

    @DeleteMapping("/{post}/comments/{commentId}")
    public Long removeCommentById(
            @PathVariable(value = "post") Article article,
            @PathVariable(value = "commentId") Long commentId) {
        return commentService.removeComment(article, commentId);
    }
}