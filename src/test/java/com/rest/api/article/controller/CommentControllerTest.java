package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.service.ArticleService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final String HOST = "/api/v1/posts";

    @Mock
    private ArticleRepository articleRepository;
    private ArticleService articleService;


    @Test
    public void articleGetAll() {
        articleRepository.save(new Article("Test", "add"));
        assertThat(articleRepository).isNotNull();
        articleRepository.findAll();
        verify(articleRepository).findAll();
    }

    @Test
    public void getArticleControllerTestStatusOk() throws Exception {
        this.mockMvc.perform(get(HOST))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Writing")));
    }


    @Test
    public void filterByTitle() throws Exception {


    }

    @Test
    public void testGetById() throws Exception {
        this.mockMvc.perform(get(HOST + "/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("5")));
    }

    @Test
    public void getAllCommentsByPostId() throws Exception {
    }

    @Test
    public void getCommentByPostId() {
    }

    @Test
    public void getTop() {
    }

    @Test
    public void getFullCommentsByPostId() {
    }

    @Test
    public void addNewComment() throws Exception {

    }
}