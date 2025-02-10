package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.PostTag;
import org.example.model.Tag;
import org.example.repo.BatchTagRepository;
import org.example.repo.TagRepository;
import org.example.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final BatchTagRepository batchTagRepository;

    @Override
    public void insertNotExistedTags(List<String> tags) {
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
//        if (tagList.size() != tags.size()) {
//            throw
//        }
        List<PostTag> postTags = tagList.stream()
                .map(tag -> PostTag.builder()
                        .tagId(tag.getId())
                        .postId(postId)
                        .build())
                .toList();
        batchTagRepository.insertPostTags(postTags);
    }


}
