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

        Comment commentSaved = commentRepository.save(comment);
        return commentSaved;
    }

    public List<Comment> getAllCommentsByPostId(Article article) {
        return commentRepository.findByArticle(article, Sort.unsorted());
    }

}
