package com.rest.api.article.controller;

import com.rest.api.article.ArticleNotFoundException;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
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
    public Article getById(@PathVariable Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));}

    @GetMapping
    public Page<Article> filterByTitle(
            @RequestParam Optional<String> sort,
            @RequestParam Optional<Integer> page) {
        return articleRepository.findAll(PageRequest.of(
                page.orElse(0), 100, Sort.Direction.ASC, sort.orElse("id")));
    }

    @GetMapping("/{postId}/comments")
    public List<Comment> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        Article article = articleRepository.findById(postId).orElseThrow(() -> new ArticleNotFoundException(postId));
        return commentRepository.findByArticle(article, Sort.unsorted());
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Optional<Comment> getCommentByPostId(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId) {
        articleRepository.findById(postId).orElseThrow(() -> new ArticleNotFoundException(postId));
        return commentRepository.findById(commentId);
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
