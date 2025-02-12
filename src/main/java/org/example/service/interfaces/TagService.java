package org.example.service.interfaces;

import org.example.model.Tag;

import java.util.List;

public interface TagService {
    void insertNotExistedTags(List<String> tags);
    void addTagsToPost(List<String> tagsName, Long postId);

    List<String> getTagNames(List<Long> ids);

    Long getTagId (String tagName);

    List<Tag> getTagsByNames(List<String> tagNames);
}
