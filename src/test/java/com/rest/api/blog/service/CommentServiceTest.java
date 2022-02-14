package com.rest.api.blog.service;

import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.aricle.ArticleRepository;
import com.rest.api.blog.comment.Comment;
import com.rest.api.blog.comment.CommentRepository;
import com.rest.api.blog.comment.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Add new Comment Test")
    void addNewComment() {

        CommentService commentService = new CommentService(commentRepository);
        Comment comment_mock = Comment.builder().id(1L).text("Comment").build();
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").star(true).build();

        when(commentRepository.save(comment_mock)).thenReturn(comment_mock);

        Comment actual = commentService.addNewComment(article_mock, comment_mock);
        assertEquals(comment_mock, actual);

        verify(commentRepository, times(1)).save(comment_mock);
    }

}