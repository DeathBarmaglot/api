package com.rest.api.blog.tag;

import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t left join t.articles article where article in ?1")
    List<List<Tag>> findTagsByArticles(Set<Long> ids);

    @Query("select t from Tag t where t.tag = ?1")
    Map<String, Article> findAllArticlesByTag(Long id);

}
