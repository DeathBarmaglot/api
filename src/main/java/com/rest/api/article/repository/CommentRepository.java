package com.rest.api.article.repository;

import com.rest.api.article.dto.CommentWithoutPostDto;
import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @EntityGraph(attributePaths = { "article.comments" })
//    List<Comment> findByArticle(Article article, Sort sort);

    @Query("select c from Comment c left join c.article articles where articles.id in ?1")
    List<List<Comment>> findCommentsByArticle(List<Long> ids);

    List<Comment> findByArticleId(Long id);
    List<Comment> findByArticleIdAndId(Long articleId, Long commentId);
}