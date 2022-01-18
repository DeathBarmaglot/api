package com.rest.api.article.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final String HOST = "/api/v1/posts";

    Article ARTICLE = new Article(7L, "Post", "Test", true);
    Comment COMMENT = new Comment(5L, "Comment", null, ARTICLE);

    @InjectMocks
    private final ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);
    private final CommentRepository commentRepository = Mockito.mock(CommentRepository.class);

    @Test
    public void getAllCommentsByPostId() throws Exception {
    }

    @Test
    public void getCommentByPostId() throws Exception {
        articleRepository.save(ARTICLE);
        System.out.println(articleRepository.findById(7L).isPresent());

        this.mockMvc.perform(get(HOST + "/7/comments/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("5")));
    }

    @Test
    public void getTop() {
    }

    @Test
    public void getFullCommentsByPostId() {
    }

    @Test
    public void addNewComment() throws Exception {

        String uri = HOST + "/5/comments";
        Comment comment = new Comment(1L, "Test", LocalDateTime.now(), ARTICLE);
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(comment));

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.text", is("Test")));
    }

    private String mapper(Object post) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(post);

    }
}



//add comment +
//delete http://localhost:8080/api/v1/posts/id/comments/

//edit post
//edit comments http://localhost:8080/api/v1/posts/id/comments/
