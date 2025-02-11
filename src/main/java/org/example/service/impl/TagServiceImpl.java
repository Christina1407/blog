package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.PostTag;
import org.example.model.Tag;
import org.example.repo.interfaces.BatchTagRepository;
import org.example.repo.interfaces.TagRepository;
import org.example.service.interfaces.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final BatchTagRepository batchTagRepository;

    @Override
    public void insertNotExistedTags(List<String> tags) {
        if (tags.isEmpty()) {
            return;
        }
        List<String> existingTags = tagRepository.findExistingTags(tags);
        List<String> newTags = tags.stream()
                .filter(tag -> !existingTags.contains(tag))
                .toList();

        if (!newTags.isEmpty()) {
            batchTagRepository.insertTags(newTags.stream()
                    .map(tagName -> Tag.builder()
                            .name(tagName)
                            .build())
                    .toList());
        }
    }

    @Override
    public void addTagsToPost(List<String> tags, Long postId) {
        List<Tag> tagList = tagRepository.findByNameIn(tags);
        List<PostTag> postTags = tagList.stream()
                .map(tag -> PostTag.builder()
                        .tagId(tag.getId())
                        .postId(postId)
                        .build())
                .toList();
        batchTagRepository.insertPostTags(postTags);
    }
    @Override
    @Transactional(readOnly = true)
    public List<String> getTagNames(List<Long> ids) {
        if (!ids.isEmpty()) {
            List<Tag> tags = tagRepository.findByIdIn(ids);
            return tags.stream()
                    .map(Tag::getName)
                    .toList();
        }
        return new ArrayList<>();
    }



}
