package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Add new Comment Test")
    void addNewComment() {

        CommentService commentService = new CommentService(commentRepository);
        Comment comment_mock = Comment.builder().comment_id(1L).text("Comment").build();
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").star(true).comments(Collections.singletonList(comment_mock)).build();

        when(commentRepository.save(comment_mock)).thenReturn(comment_mock);

      Comment actual = commentService.addNewComment(article_mock, comment_mock);
        assertEquals(comment_mock, actual);

        verify(commentRepository, times(1)).save(comment_mock);
    }


    @Test
    void getAllCommentsByPostId() {
    }

//    @Test
//    @DisplayName("Remove Comment Test")
//    void removeComment() {
//        CommentService commentService = new CommentService(commentRepository);
//        Comment comment_mock = Comment.builder().comment_id(1L).title("Post").content("Test").build();
//        commentService.removeComment(comment_mock);
//        List<Comment> actual = commentService.getAll();
//
//        assertEquals(0, actual.size());
//        verify(commentRepository, times(1)).deleteAllById(Collections.singleton(comment_mock.getComment_id()));
//    }
//
//
//
//    @Test
//    @DisplayName("Get Comment By Id Test")
//    void getCommentByPostId() {
//
//
//        CommentService commentService = new CommentService(commentRepository);
//        Comment comment_mock = Comment.builder().comment_id(1L).title("Post").content("Test").build();
//        Optional<Comment> optionalComment = Optional.of(comment_mock);
//        when(commentRepository.findById(comment_mock.getComment_id())).thenReturn(optionalComment);
//
//        commentService.getComment(comment_mock.getComment_id());
//        verify(commentRepository, times(1)).findById(1L);
//    }
}