package com.rest.api.article.service;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.repository.ArticleRepository;
import com.rest.api.article.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;

    public Set<Tag> getAll(Article article) {
        log.info("Searching All tags");
        return article.getHashtags();
    }

    public Tag addNewTag(Article articleDb, Tag tag) {
        Set<Tag> tags = articleDb.getHashtags();
        tags.add(tag);
        articleDb.setHashtags(tags);
        log.info("Adding New tag");
        tagRepository.save(tag);
        return tag;
    }

    public Tag removeTag(Article article, Tag tag) {
        Set<Tag> tags = article.getHashtags();
        tags.remove(tag);
        article.setHashtags(tags);
        articleRepository.save(article);
        log.info("Removing tag {}", tag.getHashtag());
        return tag;
    }

    public Map<String, Article> getArticlesByTags(List<String> responseTags) {
        if (!responseTags.isEmpty()){
            Map<String, Article> tags = new HashMap<>();
            responseTags.forEach(tag -> tags.put(tag, findBy(tag)));
            return tags;
        } else {
            return tagRepository.findAllArticlesByHashtag();
        }
    }

    private Article findBy(String tag) {
        return articleRepository.findByHashtags(tag);
    }

    public Tag getTags(Article articleDb, Tag tag) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDb, article, "hashtags");
        tag.setArticles(Collections.singleton(article));
        return tag;
    }
}