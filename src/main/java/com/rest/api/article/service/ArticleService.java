package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ArticleService {

    void saveArticle(Article article);

    Article getArticle(String title);

    List<Article> getArticles();

    void removeArticle(Long id);


}
