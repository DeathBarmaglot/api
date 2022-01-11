package com.rest.api.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByTitle(String title);

    List<Article> findAllByTitle(String title, Pageable pageable);

}
