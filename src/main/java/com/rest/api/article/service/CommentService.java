package com.rest.api.article.service;

import com.rest.api.article.NotFoundException;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public Comment addNewComment(Long articleId, Comment comment) {
        return articleRepository.findById(articleId).map(article -> {
            comment.setArticle(article);
            return commentRepository.save(comment);
        }).orElseThrow(NotFoundException::new);
    }

    public List<Object> allCommentByPost(Long postId) {
        Article article = articleRepository.findById(postId).orElseThrow(NotFoundException::new);
        List<Comment> commentList = commentRepository.findByArticle(article, Sort.unsorted());
        return List.of(article, commentList);
    }

    public List<Article> getByStar() {
        return articleRepository.findAll().stream().filter(article ->
                Boolean.TRUE.equals(article.isStar())).collect(Collectors.toList());
    }

    public List<Comment> getAllCommentsByPostId(Article article) {
        return commentRepository.findByArticle(article, Sort.unsorted());
    }
}
