package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.service.ArticleService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class GetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ArticleRepository articleRepository;
    private ArticleService articleService;

    @Test
    public void articleGetAll() throws Exception {
        articleRepository.save(new Article("Test", "add"));
        assertThat(articleRepository).isNotNull();
        articleRepository.findAll();
        verify(articleRepository).findAll();
    }

    @Test
    public void getArticleControllerTestStatusOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Writing")));
    }


    @Test
    public void getById() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/api/v1/posts/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":5,\"title\":\"Rest\",\"content\":\"Writing\",\"star\":false}")));
    }

    @Test
    public void filterByTitle() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/api/v1/posts?sort=title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":5,\"title\":\"Rest\",\"content\":\"Writing\",\"star\":false}")));


    }

    @Test
    public void getAllCommentsByPostId() throws Exception {
    }

    @Test
    public void getCommentByPostId() throws Exception {
    }

    @Test
    public void getTop() throws Exception {
    }

    @Test
    public void getFullCommentsByPostId() throws Exception {
    }
}

//TODO test 405, 400

//        MockHttpServletRequestBuilder request =
//                MockMvcRequestBuilders.get("/api/v1/posts");
//        ResultActions result = mockMvc.perform(request);
//        result.andExpect(MockMvcResultMatchers.status().isOk());

//                .andExpect((ResultMatcher) jsonPath("$.title", Matchers.is("java")))
//                .andExpect((ResultMatcher) jsonPath("$.value", Matchers.is("content")))
//                .andExpect((ResultMatcher) jsonPath("$.*", Matchers.hasSize(2)));