package com.rest.api.blog.service;

import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.aricle.ArticleRepository;
import com.rest.api.blog.aricle.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleTagServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Get All Top Articles By Stars Test")
    void getByStar() {
        ArticleService articleService = new ArticleService(articleRepository);

        Article article_mock = Article.builder().id(1L).title("Post").content("Test").star(true).build();
        Article article_mock2 = Article.builder().id(2L).title("Post").content("Test").star(false).build();
        Article article_mock1 = Article.builder().id(3L).title("Post").content("Test").build();
        List<Article> listArticle = List.of(article_mock);

        articleService.addNewArticle(article_mock);
        articleService.addNewArticle(article_mock1);
        articleService.addNewArticle(article_mock2);

        when(articleRepository.findByStarTrue()).thenReturn(listArticle);

        List<Article> articleFromDb = articleService.getByStar();

        assertEquals(listArticle.size(), articleFromDb.size());

        verify(articleRepository, times(1)).findByStarTrue();
    }

    @Test
    @DisplayName("Get All Articles Test")
    void getAll() {
        ArticleService articleService = new ArticleService(articleRepository);

        Article article_mock = Article.builder().id(1L).title("Post").content("Test").build();
        Article article_mock1 = Article.builder().id(2L).title("Article").content("New Test").build();
        List<Article> listArticle = List.of(article_mock, article_mock1);

        when(articleRepository.findAll()).thenReturn(listArticle);
        List<Article> actual = articleService.getAll();
        assertEquals(listArticle.size(), actual.size());
        verify(articleRepository, times(1)).findAll();
    }


}