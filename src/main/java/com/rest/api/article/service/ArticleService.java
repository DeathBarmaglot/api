package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public void removeArticle(Article article) {
        List<Comment> list = commentRepository.findByArticle(article, Sort.unsorted());
        list.forEach(comment -> commentRepository.deleteById(comment.getId()));
        articleRepository.delete(article);
        log.info("Removing post {} to the database", article.getTitle());
    }

    public Article toggle(Long id, boolean isStar) {
        Article newArticle = articleRepository.getById(id);

        log.info("The post {} to the database is {}", newArticle.getTitle(), newArticle.isStar());

        return articleRepository.findById(id)
                .map(article -> {
                    article.setStar(isStar);
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setStar(isStar);
                    return articleRepository.save(newArticle);
                });
    }

    public Article addNewArticle(Article article) {
        log.info("Saving article {} to the database", article.getTitle());
        return articleRepository.save(article);
    }
}
