package com.rest.api.blog.aricle;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByStarTrue();

    @EntityGraph(attributePaths = {"tags"})
    List<Article> findAll();
}
