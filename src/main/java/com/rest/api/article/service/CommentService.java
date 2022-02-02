package com.rest.api.article.service;

import com.rest.api.article.dto.CommentWithoutPostDto;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment addNewComment(Article articleDb, Comment comment) {
        BeanUtils.copyProperties(articleDb, articleDb, "comment");
        comment.setArticle(articleDb);
        return commentRepository.save(comment);
    }

    public List<CommentWithoutPostDto> getAllCommentsByPostId(Article article) {
        List<Comment> comments = commentRepository.findByArticle(article, Sort.unsorted());
        List<CommentWithoutPostDto> result = new ArrayList<>();
        comments.forEach(comment -> result.add(dtoCommentMapper(comment)));
        return result;
    }

    public Comment removeComment(Article article, Comment comment) {
        List<Comment> comments = article.getComments();
        comments.remove(comment);
        article.setComments(comments);
        commentRepository.delete(comment);
        return comment;
    }

    public CommentWithoutPostDto getCommentByPost(Article article, Comment comment) {
        return dtoCommentMapper(comment);
    }

    private CommentWithoutPostDto dtoCommentMapper(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        BeanUtils.copyProperties(comment, commentWithoutPostDto, "article");
        return commentWithoutPostDto;
    }
}