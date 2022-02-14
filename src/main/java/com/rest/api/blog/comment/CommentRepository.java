package com.rest.api.blog.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join c.article articles where articles.id in ?1")
    List<List<Comment>> findCommentsByArticle(List<Long> ids);

    List<Comment> findByArticleId(Long id);

    List<Comment> findByArticleIdAndId(Long articleId, Long commentId);

}