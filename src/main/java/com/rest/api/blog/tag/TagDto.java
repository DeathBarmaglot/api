package com.rest.api.blog.tag;

import com.rest.api.blog.aricle.Article;
import com.rest.api.blog.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagDto extends DtoMapper {

    private final TagService tagService;

    public List<String> getAllTag() {
        List<Tag> tags = tagService.getAllTags();
        List<String> hashes = new ArrayList<>();
        tags.forEach(tag -> hashes.add(tag.getTag()));
        return hashes;
    }

    public Set<String> getAllTagsById(Long id) {
        Set<String> tagSet = new HashSet<>();
        Set<Article> allTagsById = tagService.getAllTagsById(id);
        allTagsById.forEach(article ->
                article.getTags().forEach(tag ->
                        tagSet.add(tag.getTag())));
        return tagSet;
    }
}
