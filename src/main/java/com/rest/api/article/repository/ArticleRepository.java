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


    Article findByTags(String tag);

    @Override
    @EntityGraph(attributePaths = {"tags"})
    List<Article> findAll();

    @Query("select a from Article a left join a.tags tags where tags.tag in ?1")
    List<Article> findArticlesByTags(String tag);
//
//    @Query("select a from Article a left join a.tags tags where tags.tag in ?1")
//    List<Article> find(String tag);
}
