package org.example.service.interfaces;

import java.util.List;

public interface TagService {
    void insertNotExistedTags(List<String> tags);
    void addTagsToPost(List<String> tagsName, Long postId);

    List<String> getTagNames(List<Long> ids);
}
