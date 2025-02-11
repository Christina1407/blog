package org.example.repo.interfaces;

import org.example.model.PostTag;
import org.example.model.Tag;

import java.util.List;

public interface BatchTagRepository {
    void insertTags(List<Tag> tags);
    void insertPostTags(List<PostTag> postTags);
}
