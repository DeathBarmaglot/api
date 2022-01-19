package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Comment;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public List<Object> filteredBy(
            Optional<String> sort, Optional<String> title, Optional<Integer> page) {

        if (title.isPresent()) {
            return Collections.singletonList(articleRepository.findAll().stream().filter(article ->
                    title.get().equals(article.getTitle())));
        } else {
            return Collections.singletonList(articleRepository.findAll(PageRequest.of(
                    page.orElse(0), 100, Sort.Direction.ASC, sort.orElse("id"))));
        }
    }

    public Article getArticle(Long id) {
        return articleRepository.findById(id).orElseThrow();
    }

    public Article updateArticle(Article newArticle, Long id) {

        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(newArticle.getTitle());
                    article.setContent(newArticle.getContent());
                    article.setStar(newArticle.isStar());
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return articleRepository.save(newArticle);
                });
    }
}
