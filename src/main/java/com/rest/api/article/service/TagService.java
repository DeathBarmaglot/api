package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;

    public Set<Tag> getAll(Article article) {
        log.info("Searching All tags");
        return article.getTags();
    }

    public Tag addNewTag(Article articleDb, Tag tag) {
        Set<Tag> tags = articleDb.getTags();
        tags.add(tag);
        articleDb.setTags(tags);
        log.info("Adding New tag");
        return tagRepository.save(tag);
    }

    public Tag removeTag(Article article, Tag tag) {
        Set<Tag> tags = article.getTags();
        tags.remove(tag);
        article.setTags(tags);
        articleRepository.save(article);
        log.info("Removing tag {}", tag.getHash());
        return tag;
    }

    public Map<String, Article> ArticlesByTags() {
        Set<Article> taggedArticle = new HashSet<>();
        List<Tag> tags = tagRepository.findAll();
        Map<String, Article> articleMap =  tagRepository.findAllArticlesByAndHash();
        return articleMap;
    }
}