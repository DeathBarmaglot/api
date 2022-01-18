package com.rest.api.article.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rest.api.article.NotFoundException;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final String HOST = "/api/v1/posts";

    @InjectMocks
    private ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);
    private CommentRepository commentRepository = Mockito.mock(CommentRepository.class);

    Article ARTICLE = new Article(7L, "Test", "add", true);

    @Test
    public void addNewArticle() {

        articleRepository.save(ARTICLE);
        ArgumentCaptor<Article> articleArgumentCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleRepository).save(articleArgumentCaptor.capture());
        Article article = articleArgumentCaptor.getValue();
        assertThat(article).isEqualTo(ARTICLE);
    }

    @Test
    public void replaceArticle() throws Exception {
        this.mockMvc.perform(
                        put(HOST + "/8")
                                .param("title", "test")
                                .param("content", "test"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"title\":\"test\"}"));
    }

    @Test
    public void updateArticle() throws Exception {
        String uri = HOST + "/7";
        Article newArticle = new Article(7L, "Test", "add", true);
        Mockito.when(articleRepository.save(newArticle)).thenReturn(newArticle);

        Mockito.when(articleRepository.findById(ARTICLE.getId())).thenReturn(Optional.of(ARTICLE));
        Mockito.when(articleRepository.save(newArticle)).thenReturn(newArticle);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(newArticle));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test")));
    }

    @Test
    public void deleteArticle() throws Exception {
        String uri = HOST + "/7";
        MvcResult mvcResult = mockMvc.perform(delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


    @Test
    public void addNewComment() throws Exception {
        String uri = HOST + "/7/comments";
        Comment comment = new Comment(1L, "Test", LocalDateTime.now(),ARTICLE);
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(comment));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.text", is("Test")));

    }

    @Test
    public void editStar() throws Exception {
        this.mockMvc.perform(put(HOST + "/5/star")
                        .param("star", String.valueOf(true)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\":5,\"star\":true}"));
    }

    public void deleteStar() throws Exception {
        this.mockMvc.perform(delete(HOST + "/5/star")
                        .param("star", String.valueOf(false)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\":5,\"star\":false}"));
    }

    @Test
    public void testFilterByTitle() throws Exception {
        this.mockMvc.perform(get(HOST + "?title=Rest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].postId", is(10)))
                .andExpect(content().string(containsString("{\"id\":5,\"title\":\"Rest\",\"content\":\"Writing\",\"star\":false}")));
    }

    @Test
    public void testSortBy() throws Exception {
        this.mockMvc.perform(get(HOST + "?sort=title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":5,\"title\":\"Rest\",\"content\":\"Writing\",\"star\":false}")));
    }

    @Test
    public void deleteArticleById_success() throws Exception {
        Mockito.when(articleRepository.findById(ARTICLE.getId())).thenReturn(Optional.of(ARTICLE));

        mockMvc.perform(delete(HOST + "/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteArticleById_notFound() throws Exception {
        Mockito.when(articleRepository.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result ->
                        assertEquals("Article with ID 2 does not exist.", result.getResolvedException().getMessage()));
    }

    @Test
    public void updateArticle_nullId() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Rest\"}");

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArticle_success() throws Exception {
        Article post = new Article(1L, "Test", "add", true);
        Mockito.when(articleRepository.save(post)).thenReturn(post);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(HOST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(post));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("Test")));
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
