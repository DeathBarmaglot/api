//package com.rest.api.article.service;
//
//import com.rest.api.article.entity.Article;
//import com.rest.api.article.entity.Comment;
//import com.rest.api.article.repository.ArticleRepository;
//import com.rest.api.article.repository.CommentRepository;
//import org.junit.jupiter.api.Test;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Sort;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CommentServiceTest {
//
//    @Mock
//    private CommentRepository commentRepository;
//    private ArticleRepository articleRepository;
//
//    @Test
//    @DisplayName("Add new Comment Test")
//    void addNewComment() {
//
//        CommentService commentService = new CommentService(commentRepository);
//        Comment comment_mock = Comment.builder().id(1L).text("Comment").build();
//        Article article_mock = Article.builder().id(1L).title("Post").content("Test").star(true)
//                .comments(Collections.singletonList(comment_mock)).build();
//
//        when(commentRepository.save(comment_mock)).thenReturn(comment_mock);
//
//        Comment actual = commentService.addNewComment(article_mock, comment_mock);
//        assertEquals(comment_mock, actual);
//
//        verify(commentRepository, times(1)).save(comment_mock);
//    }
//
//    @Test
//    @DisplayName("Get all Comments By Article Test")
//    void getAllCommentsByPostId() {
//        CommentService commentService = new CommentService(commentRepository);
//        Comment comment_mock1 = Comment.builder().id(1L).text("New Test").build();
//        Comment comment_mock = Comment.builder().id(2L).text("Test").build();
//        List<Comment> commentList = List.of(comment_mock, comment_mock1);
//
//        Article article_mock = Article.builder().id(1L).title("Post").content("Test").comments(commentList).build();
//
//        when(commentRepository.findByArticle(article_mock, Sort.unsorted())).thenReturn(commentList);
//
//        List<Comment> actual = commentService.getAllCommentsByPostId(article_mock);
//
//        assertEquals(commentList, actual);
//        verify(commentRepository, times(1)).findByArticle(article_mock, Sort.unsorted());
//    }
//
//    @Test
//    @DisplayName("Remove Comment Test")
//    void removeComment() {
//
//        CommentService commentService = new CommentService(commentRepository);
////        Comment comment_mock1 = Comment.builder().id(1L).text("New Test").build();
//        Comment comment_mock = Comment.builder().id(2L).text("Test").build();
//        List<Comment> commentList = List.of(comment_mock);
//
//        Article article_mock = Article.builder().id(1L).title("Post").content("Test").comments(commentList).build();
//
//        when(commentRepository.findByArticle(article_mock, Sort.unsorted())).thenReturn(commentList);
//        List<Comment> actual = commentService.getAllCommentsByPostId(article_mock);
//
//        assertEquals(1, actual.size());
//
//        commentService.removeComment(article_mock, comment_mock);
//
//        List<Comment> actual1 = commentService.getAllCommentsByPostId(article_mock);
//        assertEquals(0, actual1.size());
//        verify(commentRepository, times(1)).delete(comment_mock);
//    }
////
//
//////        Optional<Article> optionalArticle = Optional.of(article_mock);
//////        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);
//////
//////        Article actual = articleService.getArticle(article_mock.getId());
//////
//////        assertEquals(article_mock.getContent(), actual.getContent());
//////        verify(articleRepository, times(1)).findById(1L);
//////    }
//
//    @Test
//    @DisplayName("Get Comment By Id Test")
//    void getCommentByPostId() {
//
//        CommentService commentService = new CommentService(commentRepository);
//        Comment comment_mock1 = Comment.builder().id(1L).text("New Test").build();
//        Comment comment_mock = Comment.builder().id(2L).text("Test").build();
//        List<Comment> commentList = List.of(comment_mock, comment_mock1);
//
//        Article article_mock = Article.builder().id(1L).title("Post").content("Test").comments(commentList).build();
//
////        when(commentRepository.findCommentByArticle(article_mock.getId(), comment_mock.getComment_id())).thenReturn(comment_mock);
////
////        Comment actual = commentService.getCommentByPostId(article_mock.getId(), comment_mock.getComment_id());
////        assertEquals(comment_mock, actual);
////        verify(commentRepository, times(1)).findCommentByArticle(article_mock.getId(), comment_mock.getComment_id());
//    }
//}