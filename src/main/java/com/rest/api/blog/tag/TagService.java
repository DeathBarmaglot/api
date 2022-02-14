package com.rest.api.blog.tag;

import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.aricle.ArticleRepository;
import com.rest.api.blog.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagService extends DtoMapper {

    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;

    public List<Tag> getAllTags() {
        log.info("Searching All tags");
        return tagRepository.findAll();
    }

    public String addNewTag(Article articleDb, Tag tag) {
        log.info("Adding New tag");
        articleDb.getTags().add(tag);
        tagRepository.save(tag);
        return tag.getTag();
    }

    public Set<Tag> removeTag(Long articleId, Tag tag) {
        log.info("Removing tag {}", tag.getTag());
        Article article = articleRepository.getById(articleId);
        Set<Tag> tags = article.getTags();
        tags.remove(tag);
        article.setTags(tags);
        articleRepository.save(article);
        return tags;
    }

    public Set<Article> getAllTagsById(Long id) {
        log.info("getAllTagsById {}", id);
        return tagRepository.findTagsById(id);
    }
}
