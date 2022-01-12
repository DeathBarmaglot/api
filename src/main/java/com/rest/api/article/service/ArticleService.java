package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService{

    private final ArticleRepository articleRepository;

    public void saveArticle(Article article) {articleRepository.save(article);}

    public Optional<Article> getArticle(Long id) {return articleRepository.findById(id);}

    public void removeArticle(Long id) { articleRepository.deleteById(id);}

}
