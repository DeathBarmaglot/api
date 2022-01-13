package com.rest.api;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner mappingDemo(ArticleRepository articleRepository,
                                         CommentRepository commentRepository) {
        return args -> {

            Article article = new Article("Bruce Eckel", "java philosophy");

            articleRepository.save(article);
            Comment comment1 = new Comment(null, "Introduction contents", LocalDateTime.now(), article);
            Comment comment2 = new Comment(null, "Java 8 contents", LocalDateTime.now(), article);
            Comment comment3 =new Comment(null, "Concurrency contents", LocalDateTime.now(), article);

            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);

//            Set<Comment> set = new HashSet<>(commentRepository.findAll());
//
//            article.setComments(set);
//

        };
    }

}
