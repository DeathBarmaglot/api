package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public Article addNewComment(Article articleDb, Comment comment) {
        List<Comment> comments = articleDb.getComments();
        comments.add(comment);
        articleDb.setComments(comments);
        Article articleSaved = articleRepository.save(articleDb);
        return articleSaved;
    }

    public Comment getById(Long comment_id) {
        return commentRepository.findById(comment_id).orElseThrow();
    }
}
