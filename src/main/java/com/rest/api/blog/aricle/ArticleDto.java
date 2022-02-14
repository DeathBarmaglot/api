package com.rest.api.blog.aricle;

import com.rest.api.blog.comment.Comment;
import com.rest.api.blog.comment.CommentService;
import com.rest.api.blog.dto.Post;
import com.rest.api.blog.dto.PostWithCommentsDto;
import com.rest.api.blog.dto.PostWithoutCommentDto;
import com.rest.api.blog.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleDto extends DtoMapper {

    private final ArticleService articleService;
    private final CommentService commentService;

    public PostWithCommentsDto getAllCommentsByPostId(Long id) {
        PostWithCommentsDto post = new PostWithCommentsDto();
        Set<String> setComments = new HashSet<>();
        commentService.getAllCommentsByPostId(id)
                .forEach(comment -> setComments.add(comment.getText()));
        clearPost(articleService.getArticleById(id));
        post.setComments(setComments);
        return post;
    }

    public List<Post> getAll() {
        List<Article> articles = articleService.getAll();
        List<Long> ids = new ArrayList<>();
        articles.forEach(article -> ids.add(article.getId()));
        return getPostsDto(articles, getComments(ids));
    }

    protected List<PostWithoutCommentDto> getFullArticles() {
        List<Article> articles = articleService.getAll();
        return getPostWithoutCommentDto(articles);
    }

    private Map<Long, List<Comment>> getComments(List<Long> ids) {
        Map<Long, List<Comment>> mapComments = new HashMap<>();
        commentService.getCommentsByIds(ids)
                .forEach(comments ->
                        comments.forEach(comment ->
                                mapComments.put(comment.getArticle().getId(), comments)));
        return mapComments;
    }

    public List<Post> getFullPost() {
        List<Article> articles = articleService.getAll();
        List<Long> ids = new ArrayList<>();
        articles.forEach(article -> ids.add(article.getId()));
        return getPostsDto(articles, getComments(ids));
    }
}
