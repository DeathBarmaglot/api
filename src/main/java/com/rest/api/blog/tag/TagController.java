package com.rest.api.blog.tag;

import com.rest.api.blog.aricle.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class TagController {

    private final TagService tagService;
    private final TagDto tagDto;

    @GetMapping("/{articleId}/tags")
    public Set<String> getAllTagsByArticleId(
            @PathVariable(value = "articleId") Long id) {
        return tagDto.getAllTagsById(id);
    }

    @PostMapping("/{articleId}/tags")
    public String addNewTag(
            @PathVariable(value = "articleId") Article article,
            @RequestBody Tag tag) {
        return tagService.addNewTag(article, tag);
    }

    @DeleteMapping("/{articleId}/tags/{tagId}")
    public Set<Tag> deleteTag(
            @PathVariable(value = "articleId") Long articleId,
            @PathVariable(value = "tagId") Tag tag) {
        return tagService.removeTag(articleId, tag);
    }

    @GetMapping("/tags")
    @ResponseBody
    public List<String> getAll() {
        return tagDto.getAllTag();
    }
}
