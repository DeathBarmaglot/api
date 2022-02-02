package com.rest.api.article.repository;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = { "article.comments" })
    List<Comment> findByArticle(Article article, Sort sort);
}