package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Test
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

    @Test
    @DisplayName("Remove Article Test")
    void removeArticle() {
        ArticleService articleService = new ArticleService(articleRepository);
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").build();
        articleService.removeArticle(article_mock);
        List<Article> actual = articleService.getAll();

        assertEquals(0, actual.size());
        verify(articleRepository, times(1)).deleteAllById(Collections.singleton(article_mock.getId()));
    }

    @Test
    @DisplayName("Toggle Star False To True")
    void toggleFalseToTrue() {
        ArticleService articleService = new ArticleService(articleRepository);
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").star(false).build();

        Optional<Article> optionalArticle = Optional.of(article_mock);
        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);

        Article articleFromDb = articleService.getArticle(article_mock.getId());
        articleService.toggle(articleFromDb, true);

        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);
        Article actual = articleService.getArticle(article_mock.getId());
        assertEquals(article_mock.isStar(), actual.isStar());

        verify(articleRepository, times(1)).save(article_mock);
    }

    @Test
    @DisplayName("Toggle Star True To False")
    void toggleTrueToFalse() {
        ArticleService articleService = new ArticleService(articleRepository);
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").star(true).build();

        Optional<Article> optionalArticle = Optional.of(article_mock);
        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);

        Article articleFromDb = articleService.getArticle(article_mock.getId());
        articleService.toggle(articleFromDb, false);

        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);
        Article actual = articleService.getArticle(article_mock.getId());
        assertEquals(article_mock.isStar(), actual.isStar());

        verify(articleRepository, times(1)).save(article_mock);
    }

    @Test
    @DisplayName("Add new Article Test")
    void addNewArticle() {
        ArticleService articleService = new ArticleService(articleRepository);
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").build();
        Optional<Article> optionalArticle = Optional.of(article_mock);
        articleService.addNewArticle(article_mock);

        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);
        Article actual = articleService.getArticle(article_mock.getId());
        assertEquals(article_mock, actual);

        verify(articleRepository, times(1)).save(article_mock);
    }

    @Test
    @DisplayName("Get Article By Id Test")
    void getArticle() {
        ArticleService articleService = new ArticleService(articleRepository);
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").build();
        Optional<Article> optionalArticle = Optional.of(article_mock);
        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);

        articleService.getArticle(article_mock.getId());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void updateArticle() {
        ArticleService articleService = new ArticleService(articleRepository);
        Article article_mock = Article.builder().id(1L).title("Post").content("Test").build();
        Article article_mock2 = Article.builder().id(2L).title("Post").content("New Test").build();

        Optional<Article> optionalArticle = Optional.of(article_mock);
        when(articleRepository.findById(article_mock.getId())).thenReturn(optionalArticle);

        Article articleFromDb = articleService.getArticle(article_mock.getId());
        articleService.updateArticle(articleFromDb, article_mock2);

        when(articleRepository.findById(article_mock2.getId())).thenReturn(optionalArticle);
        Article actual = articleService.getArticle(article_mock2.getId());
        assertEquals(article_mock2.getContent(), actual.getContent());

        verify(articleRepository, times(1)).save(article_mock);
    }
}