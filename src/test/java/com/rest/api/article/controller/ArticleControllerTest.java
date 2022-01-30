package com.rest.api.article.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rest.api.article.NotFoundException;
import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ArticleControllerTest {

    private MockMvc mockMvc;
    private final String HOST = "/api/v1/posts";

    private final ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);

    Article ARTICLE = Article.builder().id(1L).title("Post").content("Test").star(true).build();

    @Test
    public void testAddNewArticle() {

        articleRepository.save(ARTICLE);
        ArgumentCaptor<Article> articleArgumentCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleRepository).save(articleArgumentCaptor.capture());
        Article article = articleArgumentCaptor.getValue();
        assertThat(article).isEqualTo(ARTICLE);
    }

    @Test
    public void testUpdateArticle() throws Exception {

        String uri = HOST + "/1";
        Article newArticle = Article.builder().id(1L).title("Post").content("Test").star(true).build();
        Mockito.when(articleRepository.save(newArticle)).thenReturn(newArticle);

        Mockito.when(articleRepository.findById(ARTICLE.getId())).thenReturn(Optional.of(ARTICLE));
        Mockito.when(articleRepository.save(newArticle)).thenReturn(newArticle);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(newArticle));

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test")));
    }

    @Test
    public void testDeleteArticle() throws Exception {
        String uri = HOST + "/16";
        MvcResult mvcResult = mockMvc.perform(delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void testAddStar() throws Exception {
        mockMvc.perform(put(HOST + "/5/star")
                        .param("star", String.valueOf(true)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\":5,\"star\":true}"));
    }

    @Test
    public void testDeleteStar() throws Exception {
        mockMvc.perform(delete(HOST + "/1/star")
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
                .andExpect(content().string(containsString("\"title\":\"Rest\",\"content\":\"Writing\",\"star\":false")));
    }

    @Test
    public void testSortBy() throws Exception {
        this.mockMvc.perform(get(HOST + "?sort=title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"title\":\"Bad\",\"content\":\"News\",\"star\":false")));
    }

    @Test
    public void testDeleteArticleById_success() throws Exception {
        Mockito.when(articleRepository.findById(ARTICLE.getId())).thenReturn(Optional.of(ARTICLE));

        mockMvc.perform(delete(HOST + "/19")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteArticleByIdNotFound() throws Exception {
        Mockito.when(articleRepository.findById(20L)).thenReturn(null);

        mockMvc.perform(delete(HOST + "/17")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result ->
                        assertEquals("Article with ID 2 does not exist.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testUpdateArticleNullId() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Rest\"}");

        mockMvc.perform(mockRequest)
                .andExpect(status().is(404));
    }

    @Test
    public void testCreateArticle_success() throws Exception {
        Article post = Article.builder().id(1L).title("Post").content("Test").star(true).build();
        Mockito.when(articleRepository.save(post)).thenReturn(post);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(HOST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper(post));

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("Test")));
    }

    @Test
    public void testGetAllArticles() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(HOST)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(content().contentType(MediaType.parseMediaType("application/json")))
                .andExpect(content().string(containsString("Test")));
    }

    @Test
    public void testGetArticle() throws Exception {
        String uri = HOST + "/1";
        when(articleRepository.getById(ARTICLE.getId())).thenReturn(ARTICLE);

        this.mockMvc.perform(get(uri, ARTICLE.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.content").value("News"))
                .andExpect(jsonPath("$.title").value("Rest"));
    }

    @Test
    public void testGetById() throws Exception {
        this.mockMvc.perform(get(HOST + "/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":5")));
    }

    @Test
    public void testArticleDelete() throws Exception {
        this.mockMvc.perform(delete(HOST + "/19", ARTICLE.getId())).andExpect(status().isOk());
    }

    protected String mapper(Object post) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(post);
    }
}