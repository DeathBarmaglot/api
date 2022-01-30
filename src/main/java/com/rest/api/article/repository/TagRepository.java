package com.rest.api.article.repository;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;


public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t where t.hash = ?1")
    Map<String, Article> findAllArticlesByAndHash();
}
