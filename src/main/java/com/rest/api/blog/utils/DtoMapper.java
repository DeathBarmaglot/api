package com.rest.api.blog.utils;

import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.comment.Comment;
import com.rest.api.blog.dto.CommentWithoutPostDto;
import com.rest.api.blog.dto.Post;
import com.rest.api.blog.dto.PostWithoutCommentDto;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class DtoMapper {

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

    protected List<Post> getPostsDto(List<Article> articles, Map<Long, List<Comment>> commentsMap) {

        List<Post> postList = new ArrayList<>();
        articles.forEach(article -> {
            Long id = article.getId();
            for (Map.Entry<Long, List<Comment>> entry : commentsMap.entrySet()) {
                if (entry.getKey() == id) {
                    List<Comment> value = entry.getValue();
                    postList.add(getPostWithoutComment(article, fetchCommentsDto(value)));
                } else {
                    List<Comment> comments = new ArrayList<>();
                    postList.add(getPostWithoutComment(article, fetchCommentsDto(comments)));
                }
            }
        });
        return postList;
    }

    protected Post getPostWithoutComment(Article article, List<CommentWithoutPostDto> comments) {
        Post postDto = new Post();
        BeanUtils.copyProperties(article, postDto, "comments", "tags");
        Set<String> tags = new HashSet<>();
        article.getTags().forEach(tag -> tags.add(tag.getTag()));
        postDto.setTags(tags);
        postDto.setComments(comments);
        return postDto;
    }

    protected List<PostWithoutCommentDto> getPostWithoutCommentDto(List<Article> articles) {
        List<PostWithoutCommentDto> posts = new ArrayList<>();
        articles.forEach(article -> {
            posts.add(clearPost(article));
        });
        return posts;
    }

    protected PostWithoutCommentDto clearPost(Article article) {
        PostWithoutCommentDto post = new PostWithoutCommentDto();
        BeanUtils.copyProperties(article, post, "comments", "tags");
        Set<String> tags = new HashSet<>();
        article.getTags().forEach(tag -> tags.add(tag.getTag()));
        post.setTags(tags);
        return post;
    }
}