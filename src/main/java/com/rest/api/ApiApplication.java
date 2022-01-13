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
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner mappingDemo(ArticleRepository articleRepository, CommentRepository commentRepository) {

        return args -> {

            Article article = new Article(RandomTitle(), RandomTitle());

            articleRepository.save(article);
            Comment comment1 = new Comment(null, RandomString(), LocalDateTime.now(), article);
            Comment comment2 = new Comment(null, RandomString(), LocalDateTime.now(), article);
            Comment comment3 =new Comment(null, RandomString(), LocalDateTime.now(), article);

            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);

//            Set<Comment> set = new HashSet<>(commentRepository.findAll());
//            article.setComments(set);
        };
    }

    private String RandomString() {
        String names[] = "I have been doing, much studying in java, and I have a new project, I would like to do".split(", ");
        return names[new Random().nextInt(names.length)];
    }

    private String RandomTitle() {
        String names[] = "Writing JUnit Tests Rest Controller MockMvc Mockito".split(" ");
        return names[new Random().nextInt(names.length)];
    }

}
