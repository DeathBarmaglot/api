package com.rest.api.blog.aricle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getByStar() {
        log.info("Searching top articles");
        return articleRepository.findByStarTrue();
    }

    public List<Article> getAll() {
        log.info("Searching All articles withOut Comments");
        return articleRepository.findAll();
    }

    public void removeArticle(Long id) {
        log.info("Removing post {} to the database", id);
        articleRepository.deleteById(id);
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

    public Article updateArticle(Article articleDb, Article article) {
        log.info("Updating article {} to the database", article.getTitle());
        BeanUtils.copyProperties(article, articleDb, "id");
        return articleRepository.save(articleDb);
    }

    public Article getArticleById(Long id) {
        log.info("Searching article by Id {} to the database", id);
        return articleRepository.getById(id);
    }
}