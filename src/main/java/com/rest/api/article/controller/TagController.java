package com.rest.api.article.controller;

import com.rest.api.article.entity.Article;
import com.rest.api.article.entity.Tag;
import com.rest.api.article.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class TagController {

    private final TagService tagService;

    @GetMapping("/{articleId}/tags")
    public Set<String> getAllTagsByArticleId(
            @PathVariable(value = "articleId") Article article) {
        return tagService.getAllTagsById(article);
    }

    @PostMapping("/{articleId}/tags")
    public String addNewTag(
            @PathVariable(value = "articleId") Article article,
            @RequestBody Tag tag) {
        return tagService.addNewTag(article, tag);
    }

//    @DeleteMapping("/{articleId}/tags/{tagId}")
//    public Tag deleteTag(
//            @PathVariable(value = "articleId") Long articleId,
//            @PathVariable(value = "tagId") Long tagId) {
//        return tagService.removeTag(articleId, tagId);
//    }

    @GetMapping("/tags")
    @ResponseBody
    public List<Tag> getAll() {
        return tagService.getAll();
    }

//    @GetMapping("/{articleId}/tags/{tagId}")
//    public Tag getTagById(
//            @PathVariable(value = "articleId") Long articleId,
//            @PathVariable(value = "tagId") Tag tagId) {
//        return tagService.getTag(articleId, tagId);
//    }
}
