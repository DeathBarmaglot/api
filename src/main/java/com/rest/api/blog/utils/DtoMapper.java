package com.rest.api.blog.utils;

import com.rest.api.blog.dto.CommentWithoutPostDto;
import com.rest.api.blog.dto.Post;
import com.rest.api.blog.dto.PostWithCommentsDto;
import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.comment.Comment;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DtoMapper {


    protected Map<Long, Post> mapper(List<Article> articles) {
        Map<Long, Post> map = new HashMap<>();
        articles.forEach(article -> {
            Post post = new Post();
            BeanUtils.copyProperties(article, post, "comments", "tags");
            map.put(article.getId(), post);
        });
        return map;
    }

    protected CommentWithoutPostDto dtoCommentMapper(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        BeanUtils.copyProperties(comment, commentWithoutPostDto, "article");
        return commentWithoutPostDto;
    }

    protected List<CommentWithoutPostDto> fetchCommentsDto(List<Comment> comments) {
        List<CommentWithoutPostDto> commentWithoutPostDtoList = new ArrayList<>();
        comments.forEach(comment -> commentWithoutPostDtoList.add(dtoComment(comment)));
        return commentWithoutPostDtoList;
    }

    private CommentWithoutPostDto dtoComment(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        BeanUtils.copyProperties(comment, commentWithoutPostDto, "article");
        return commentWithoutPostDto;
    }

    protected PostWithCommentsDto getPostsWithComments(Article article, List<Comment> comments) {
        PostWithCommentsDto post = new PostWithCommentsDto();
        BeanUtils.copyProperties(article, post, "comment");
        post.setComments(fetchCommentsDto(comments));
        return post;
    }
}