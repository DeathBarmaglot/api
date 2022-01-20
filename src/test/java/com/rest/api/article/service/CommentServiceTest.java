package com.rest.api.article.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    private MockMvc mockMvc;

    Article ARTICLE = new Article(1L, "Post", "Test", false);
    Article ARTICLE1 = new Article("Article", "New Test");

    Comment COMMENT = new Comment(2L, "Comment", null, ARTICLE);
    Comment COMMENT1 = new Comment(3L, "Comment2", null, ARTICLE);

    private final ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);
    private final CommentRepository commentRepository = Mockito.mock(CommentRepository.class);


    @Test
    void addNewComment() {
        CommentService commentService = new CommentService(commentRepository);

        Article mockedArticle = new Article(1L, "Post", "Test", false);
        Optional<Article> optionalArticle = Optional.of(mockedArticle);
        when(articleRepository.findById(1L)).thenReturn(optionalArticle);

        Comment commentToSave = new Comment();
        commentToSave.setText("test");
        commentToSave.setId(1L);

        Comment savedComment = new Comment();
        savedComment.setText("test");
        savedComment.setId(1L);

        when(commentRepository.save(any())).thenReturn(savedComment);

        Comment mockedSavedComment = commentService.addNewComment(ARTICLE, commentToSave);

        assertEquals(1L, mockedSavedComment.getId());
        assertEquals("test", mockedSavedComment.getText());

    //    verify(commentRepository, times(1)).findById(1L);
    }


    @Test
    void allCommentByPost() throws Exception {
    }

    @Test
    void getByStar() throws Exception {
    }

    @Test
    void getAllCommentsByPostId() throws Exception {

    }

    private String mapper(Object post) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(post);
    }
}