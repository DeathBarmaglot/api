package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment addNewComment(Article articleDb, Comment comment) {
        BeanUtils.copyProperties(articleDb, articleDb, "comment");
        comment.setArticle(articleDb);
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsByPostId(Article article) {
        return commentRepository.findByArticle(article, Sort.unsorted());
    }

    public Comment removeComment(Article article, Comment comment) {
        List<Comment> comments = article.getComments();
        comments.remove(comment);
        article.setComments(comments);
        commentRepository.delete(comment);
        return comment;
    }

    public Comment getCommentByPostId(Long articleId, Long commentId) {
        return commentRepository.findCommentByArticle(articleId, commentId);
    }
}
