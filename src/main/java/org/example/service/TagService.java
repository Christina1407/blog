package org.example.service;

import java.util.List;

public interface TagService {
    void insertNotExistedTags(List<String> tags);
    void addTagsToPost(List<String> tagsName, Long postId);
}
