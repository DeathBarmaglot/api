package com.rest.api.blog.comment;

import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.dto.CommentWithoutPostDto;
import com.rest.api.blog.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService extends DtoMapper {

    private final CommentRepository commentRepository;

    public Comment addNewComment(Article articleDb, Comment comment) {
        BeanUtils.copyProperties(articleDb, articleDb, "comment");
        comment.setArticle(articleDb);
        return commentRepository.save(comment);
    }

    public List<CommentWithoutPostDto> getAllCommentsByPostId(Long id) {
        List<Comment> comments = commentRepository.findByArticleId(id);
        List<CommentWithoutPostDto> result = new ArrayList<>();
        comments.forEach(comment -> result.add(dtoCommentMapper(comment)));
        return result;
    }

    public Long removeComment(Article article, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        log.debug("Searching All articles with all comments {}", comment.getArticle().equals(article));
        commentRepository.delete(comment);
        return comment.getId();
    }

    public List<Comment> getCommentByPost(Long articleId, Long commentId) {
        return commentRepository.findByArticleIdAndId(articleId, commentId);
    }

    public List<List<Comment>> getCommentsByIds(List<Long> ids) {
        log.debug("Searching all comments");
        return commentRepository.findCommentsByArticle(ids);
    }
}