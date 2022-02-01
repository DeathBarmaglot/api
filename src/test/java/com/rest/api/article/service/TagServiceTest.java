package com.rest.api.article.service;

import com.rest.api.article.entity.Tag;
import com.rest.api.article.repository.TagRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {


    @Mock
    private ArticleRepository articleRepository;
    private TagRepository tagRepository;

        @Test
    void getAll() {
            TagService tagService = new TagService(tagRepository, articleRepository);
            Tag tag_mock = Tag.builder().id(1L).hashtag("test").build();
            Tag tag_mock1 = Tag.builder().id(2L).hashtag("tag").build();
            Set<Tag> tagSet = new HashSet<>();
            tagSet.add(tag_mock);
            tagSet.add(tag_mock1);
            Article article_mock1 = Article.builder().id(2L).title("Article").content("New Test").hashtags(tagSet).build();

            // Article article_mock = Article.builder().id(1L).title("Post").content("Test").hashtags(tagSet).build();
//            List<Article> listArticle = List.of(article_mock, article_mock1);

            when(tagRepository.findAll()).thenReturn((List<Tag>) tagSet);
            Set<Tag> actual = tagService.getAll(article_mock1);
            assertEquals(tagSet.size(), actual.size());
            verify(articleRepository, times(1)).findAll();
        }

    @Test
    void addNewTag() {
    }

    @Test
    void removeTag() {
    }

    @Test
    void getArticlesByTags() {
    }
}