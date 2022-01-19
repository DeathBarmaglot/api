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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {


    @Autowired
    private MockMvc mockMvc;
    private final String HOST = "/api/v1/posts";

    Article ARTICLE = new Article(7L, "Post", "Test", true);
    Article ARTICLE1 = new Article(1L, "Article", "New Test", false);
    Comment COMMENT = new Comment(2L, "Comment", null, ARTICLE);
    Comment COMMENT1 = new Comment(3L, "Comment2", null, ARTICLE);

    @InjectMocks
    private final ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);
    private final CommentRepository commentRepository = Mockito.mock(CommentRepository.class);


    @Test
    void addNewComment() throws Exception {
        String uri = HOST + "/7";
        Mockito.when(articleRepository.save(ARTICLE)).thenReturn(ARTICLE);
        Mockito.when(commentRepository.save(COMMENT)).thenReturn(COMMENT);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(COMMENT));

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.text", is("Test")));
    }

    @Test
    void allCommentByPost() throws Exception {
        String uri = HOST + "/5/comments";
        Mockito.when(commentRepository.save(COMMENT)).thenReturn(COMMENT);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(COMMENT));

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.text", is("Test")));
    }

    @Test
    void getByStar() throws Exception {
        String uri = HOST + "/7/star";
        Mockito.when(articleRepository.save(ARTICLE)).thenReturn(ARTICLE);
        Mockito.when(articleRepository.save(ARTICLE1)).thenReturn(ARTICLE1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(COMMENT));

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.star", is(true)));

    }

    @Test
    void getAllCommentsByPostId() throws Exception {
        List<Comment> listComment = commentRepository.findByArticle(ARTICLE, Sort.unsorted());
        System.out.println(listComment);

        String uri = HOST + "/1/full";
        List<Comment> commentList = new ArrayList<>(Arrays.asList(COMMENT, COMMENT1));

        Mockito.when(commentRepository.findAll()).thenReturn(commentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].Comment", is("Comment")));
    }

    private String mapper(Object post) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(post);
    }
}