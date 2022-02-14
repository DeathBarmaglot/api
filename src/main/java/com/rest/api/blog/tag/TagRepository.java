package com.rest.api.blog.tag;

import com.rest.api.blog.aricle.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select tags from Article t left join t.tags tags where tags.id in ?1")
    Set<Article> findTagsById(Long id);

    @Query("select t from Tag t where t.tag = ?1")
    Map<String, Article> findAllArticlesByTag(Long id);
}

