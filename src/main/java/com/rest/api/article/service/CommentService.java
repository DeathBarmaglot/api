package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment addNewComment(Article article, Comment comment) {
        comment.setArticle(article);
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsByPostId(Article article) {
        return commentRepository.findByArticle(article, Sort.unsorted());
    }
}
