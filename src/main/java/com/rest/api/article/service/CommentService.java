package com.rest.api.article.service;

import com.rest.api.article.dto.CommentWithoutPostDto;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<CommentWithoutPostDto> getAllCommentsByPostId(Article article) {
        List<Comment> comments = commentRepository.findByArticle(article, Sort.unsorted());
        List<CommentWithoutPostDto> result = new ArrayList<>();
        comments.forEach(comment -> result.add(dtoMapper(comment)));
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
        return dtoMapper(comment);
    }

    private CommentWithoutPostDto dtoMapper(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        commentWithoutPostDto.setComment_id(comment.getComment_id());
        commentWithoutPostDto.setText(comment.getText());
        commentWithoutPostDto.setCreatedAt(comment.getCreatedAt());
        return commentWithoutPostDto;
    }
}
// TODO Comments + Tag  without articles id
// http://localhost:8080/api/v1/posts/full
//TODO Comments without articles
//{http://localhost:8080/api/v1/posts/2
//post with  comment(without articles)  & tag (without articles id)
//        "id": 2,
//        "title": "News",
//        "content": "Bad",
//        "star": false,
//        "comments": [],
//        "hashtags": [
//        {
//        "id": 6,
//        "hashtag": "TV",
//        "articles": []
//        },
//        {
//        "id": 7,
//        "hashtag": "book",
//        "articles": []
//        }
//        ]
//        }