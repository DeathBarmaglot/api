package com.rest.api;

import com.rest.api.article.Article;
import com.rest.api.article.ArticleServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(ArticleServiceImpl articleService) {
        return args -> {
            Article post = new Article("Good News everyone", "task #1 is done");
            articleService.saveArticle(post);
        };
    }
}
