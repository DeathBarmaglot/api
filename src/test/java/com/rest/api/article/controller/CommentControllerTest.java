package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.service.ArticleService;
import com.rest.api.article.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

    @Test
    void findBy() throws Exception {
        articleService = new ArticleService(articleRepository);
        Comment COMMENT = Comment.builder().comment_id(1L).text("Comment").build();
        Comment COMMENT2 = Comment.builder().comment_id(2L).text("Comment2").build();
        List<Comment> comments = Arrays.asList(COMMENT, COMMENT2);
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").star(true).comments(comments).build();
        List<Article> listArticle = List.of(article_mock);

        Optional<Article> optionalArticle = Optional.of(article_mock);
        articleService.addNewArticle(article_mock);

        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);
        Article actual = articleService.getArticle(article_mock.getId());
        assertEquals(article_mock, actual);
        verify(articleRepository, times(1)).save(article_mock);

        when(articleRepository.findAll()).thenReturn(listArticle);
        List<Article> actualAll = articleService.getAll();
        assertEquals(listArticle.size(), actualAll.size());
        verify(articleRepository, times(1)).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string(containsString("\"title\":\"Rest\",\"content\":\"Writing\",\"star\":false")));
        verify(commentService, times(1)).getAllCommentsByPostId(article_mock);
    }
}