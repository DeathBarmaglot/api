package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class TagController {
    private final TagService tagService;

    @GetMapping("/{id}/tags")
    public Set<Tag> getAllTagsByArticleId(
            @PathVariable(value = "id") Article articleDb) {
        return tagService.getAll(articleDb);
    }

    @PostMapping("/{id}/tags")
    public Tag addNewTag(
            @PathVariable(value = "id") Article articleDb,
            @RequestBody Tag tag) {
        return tagService.addNewTag(articleDb, tag);
    }

    @DeleteMapping("/{id}/tags/{tagId}")
    public Tag deleteTag(
            @PathVariable(value = "id") Article article,
            @PathVariable(value = "tagId") Tag tag) {
        return tagService.removeTag(article, tag);
    }

    @GetMapping("/tags")
    public Map<String, Article> getAllTaggedArticles() {
        return tagService.ArticlesByTags();
    }
}
