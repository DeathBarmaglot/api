package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    public List<Article> getByStar() {
        log.info("Searching top articles");
        return articleRepository.findByStarTrue();
    }

    public List<Article> getAll() {
        log.info("Searching All articles");
        return articleRepository.findAll();
    }

    public void removeArticle(Article article) {
        log.info("Removing post {} to the database", article.getTitle());
        articleRepository.deleteAllById(Collections.singleton(article.getId()));
    }

    public Article toggle(Article articleDb, boolean star) {
        log.info("Setting top article {} as {}", articleDb.getTitle(), star);
        articleDb.setStar(star);
        return articleRepository.save(articleDb);
    }

    public Article addNewArticle(Article article) {
        log.info("Saving article {} to the database", article.getTitle());
        return articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        log.info("Searching article by Id {} to the database", id);
        return articleRepository.findById(id).orElseThrow();
    }

    public Article updateArticle(Article articleDb, Article article) {
        log.info("Updating article {} to the database", article.getTitle());
        BeanUtils.copyProperties(article, articleDb, "id");
        return articleRepository.save(articleDb);
    }

    public List<Article> filteredBy(Optional<String> sort, Optional<String> title, Optional<Integer> page) {

        List<Article> result;

        if (title.isPresent()) {
            result = articleRepository.findByTitle(title.get());
            log.info("Founded article by title {} to the database", title.get());

        } else if (sort.isPresent()) {
            result = articleRepository.findAll(PageRequest.of(
                    page.orElse(0), 100, Sort.Direction.ASC, sort.orElse("id"))).getContent();
            log.info("Sorted article by {} to the database", sort.get());

        } else {
            result = articleRepository.findAll();
        }
        return result;
    }
}
