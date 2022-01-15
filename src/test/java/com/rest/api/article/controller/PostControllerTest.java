package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PostController controller;
    private ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);

    @Test
    public void articlePostControllerTestStatus() throws Exception {
        this.mockMvc.perform(
                        post("/api/v1/posts/")
                                .param("text", "test")
                                .param("content", "test"))
                .andDo(print())
                .andExpect(status().is(201));
    }

    @Test
    public void addNewArticle() throws Exception {
        Article newArticle = new Article("Test", "add");
        articleRepository.save(newArticle);
        ArgumentCaptor<Article> articleArgumentCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleRepository).save(articleArgumentCaptor.capture());
        Article article = articleArgumentCaptor.getValue();
        assertThat(article).isEqualTo(newArticle);
    }

    @Test
    public void replaceArticle() throws Exception {
        this.mockMvc.perform(
                        put("http://localhost:8080/api/v1/posts/3")
                                .param("text", "test")
                                .param("content", "test"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"text\":\"test\"}"));
    }

    @Test
    public void deleteArticle() throws Exception {
        MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/posts/3")
                .content("{\"title\":\"Good\"}]")
                .session(new MockHttpSession())
                .contentType(MediaType.APPLICATION_JSON);
        assertThat(status().is(200));
    }


    @Test
    public void addNewComment() {

    }

    @Test
    public void editStar() throws Exception {
        this.mockMvc.perform(
                        put("/api/v1/posts/5")
                                .param("text", "test")
                                .param("content", "test"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().json("{\"text\":\"test\"}"));
    }

    @Test
    public void deleteStar() {
    }

}

//add comment +
//delete http://localhost:8080/api/v1/posts/id/comments/

//edit post
//edit comments http://localhost:8080/api/v1/posts/id/comments/
