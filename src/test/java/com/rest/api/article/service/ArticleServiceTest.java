package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ArticleServiceTest {

    private final ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);
    private final ArticleService articleService = new ArticleService(articleRepository);


    @Test
    public void testDeleteArticleBy() {

        Article savedArticle = generatePost();

        Article mockedArticle = generateUnit();
        Optional<Article> optionalArticle = Optional.of(mockedArticle);

        when(articleRepository.findById(1L)).thenReturn(optionalArticle);
        when(articleRepository.save(any())).thenReturn(savedArticle);

        Article mockedSavedArticle = articleService.addNewArticle(savedArticle);
        articleService.removeArticle(mockedSavedArticle);
        List<Article> articleRepositoryAll = articleRepository.findAll();
        assertTrue(articleRepositoryAll.isEmpty());
    }

    @Test
    public void addNewCommentService() {

        Article savedArticle = generatePost();

        Article mockedArticle = generateUnit();
        Optional<Article> optionalArticle = Optional.of(mockedArticle);

        when(articleRepository.findById(1L)).thenReturn(optionalArticle);
        when(articleRepository.save(any())).thenReturn(savedArticle);

        Article mockedSavedArticle = articleService.addNewArticle(savedArticle);

        assertEquals(1L, mockedSavedArticle.getId());
        assertEquals("Post", mockedSavedArticle.getTitle());
        assertEquals("Comment", mockedSavedArticle.getComments().get(0).getText());
    }

    private Article generatePost() {
        Article savedArticle = new Article();
        List<Comment> comments = new ArrayList<>();
        Comment COMMENT0 = new Comment(null, "Comment", null);
        comments.add(COMMENT0);
        savedArticle.setComments(comments);
        savedArticle.setTitle("Post");
        savedArticle.setId(1L);
        savedArticle.setContent("Test");
        return savedArticle;
    }

    private Article generateUnit() {
        List<Comment> commentList = new ArrayList<>();
        Comment COMMENT = new Comment(null, "Comment", null);
        commentList.add(COMMENT);
        Article mockedArticle = new Article(1L, "Post", "Test", false, commentList);
        return mockedArticle;
    }
}