package com.rest.api.article.controller;

import com.rest.api.article.ArticleNotFoundException;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class ArticleGetController {


        @Autowired
        private CommentRepository commentRepository;

        @Autowired
        private ArticleRepository articleRepository;

    @GetMapping("/{id}")
    public Article getById(@PathVariable Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @GetMapping
    public Page<Article> filterByTitle(
            @RequestParam Optional<String> sort,
            @RequestParam Optional<Integer> page) {
        return articleRepository.findAll(PageRequest.of(
                page.orElse(0), 100,Sort.Direction.ASC, sort.orElse("id")));
    }
    @GetMapping("/{postId}/comments")
    public Page<Comment> getAllCommentsByPostId(@PathVariable (value = "postId") Long postId, Pageable pageable) {
return commentRepository.findByArticleId(postId, pageable);
    }
}
