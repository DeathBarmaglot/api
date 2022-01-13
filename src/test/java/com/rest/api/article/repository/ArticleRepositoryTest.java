package com.rest.api.article.repository;

import com.rest.api.article.entity.Article;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository test;

    @Test
    void findByTitle() {
        String title = "News";
        Article post = new Article(title, "Good");
        test.save(post);
//        Article exists = testRepo.findByTitle(title);
//        assertThat(exists).usingRecursiveComparison().isEqualTo(post);
//        assertNotNull()
    }

}