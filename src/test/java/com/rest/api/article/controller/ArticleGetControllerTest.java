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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ArticleGetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ArticleRepository articleRepository;
    private ArticleService articleService;

    @BeforeEach
    public void setUp() throws Exception {
        articleService = new ArticleService(articleRepository);
    }

    @Test
    public void articleGetControllerTestIsNull() throws Exception {
        assertThat(articleService).isNull();
    }

    @Test
    public void articleArgumentCaptor() throws Exception {
        Article newArticle = new Article("Test", "add");
        articleRepository.save(newArticle);
        ArgumentCaptor<Article> articleArgumentCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleRepository).save(articleArgumentCaptor.capture());
        Article article = articleArgumentCaptor.getValue();
        assertThat(article).isEqualTo(newArticle);
    }

    @Test
    public void articleGetAll() throws Exception {
        articleRepository.save(new Article("Test", "add"));
        assertThat(articleRepository).isNotNull();
        articleRepository.findAll();
        verify(articleRepository).findAll();

        //        articleService.getAllPosts();

    }

    @Test
    public void articleGetControllerTestStatusOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("java philosophy")));
    }

    @Test
    public void articleGetControllerTestContainsJson() throws Exception {
        this.mockMvc.perform(get("/api/v1/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is("java")));
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