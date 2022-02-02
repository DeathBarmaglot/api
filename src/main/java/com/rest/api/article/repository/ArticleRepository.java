package com.rest.api.article.repository;

import com.rest.api.article.entity.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select t from Article t where t.title = ?1")
    List<Article> findByTitle(String title);

    List<Article> findByStarTrue();

    @EntityGraph(attributePaths = {"hashtags"})
    Article findByHashtags(String tag);

    @Override
    @EntityGraph(attributePaths = {"comments", "hashtags"})
    List<Article> findAll();

}
