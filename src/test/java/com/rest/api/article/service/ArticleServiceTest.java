package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ArticleServiceTest {

    @Autowired
    private MockMvc mockMvc;
    private final String HOST = "/api/v1/posts";

    @InjectMocks
    private ArticleService articleService = Mockito.mock(ArticleService.class);
    private CommentService commentService = Mockito.mock(CommentService.class);

    Article ARTICLE = new Article(1L, "Test", "add", true);


    @Test
    void removeArticle() throws Exception {
        this.mockMvc.perform(delete(HOST + "/19", ARTICLE.getId())).andExpect(status().isOk());
    }

    @Test
    void toggle() throws Exception {
        this.mockMvc.perform(put(HOST + "/5/star")
                        .param("star", String.valueOf(true)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\":5,\"star\":true}"));

        this.mockMvc.perform(delete(HOST + "/5/star")
                        .param("star", String.valueOf(false)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\":5,\"star\":false}"));
    }

    @Test
    void addNewArticle() throws Exception {
        this.mockMvc.perform(put(HOST + "/5/star")
                        .param("star", String.valueOf(true)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\":5,\"star\":true}"));
    }

    @Test
    void filteredBy() throws Exception {
        this.mockMvc.perform(get(HOST + "?title=Rest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"title\":\"Rest\",\"content\":\"Writing\",\"star\":false")));
    }

    @Test
    public void testGetPostThrowsException() throws Exception {

        Long postId = ARTICLE.getId();

        when(articleService.getArticle(postId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Not found :" + postId));

        this.mockMvc.perform(get("/posts/{id}", postId))
                .andExpect(status().is4xxClientError())
                .andExpect(response -> assertTrue(response.getResolvedException() instanceof ResponseStatusException))
                .andExpect(response -> assertEquals("Post Not found :" + postId, response.getResponse().getErrorMessage()));
    }
}