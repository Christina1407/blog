package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.model.Post;
import org.example.model.PostTag;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;
import org.example.service.interfaces.TagService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentMapper commentMapper;
    private final TagService tagService;

    public PostReadDto map(Post post) {
        return getBuild(post);
    }

    public List<PostReadDto> map(List<Post> posts) {
        return posts.stream()
                .map(this::map)
                .toList();
    }

    public Post map(PostCreateDto postCreateDto) {
        return getBuild(postCreateDto);
    }


    private PostReadDto getBuild(Post post) {
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdDate(post.getCreatedDate())
                .authorId(post.getAuthorId())
                .tags(tagService.getTagNames(post.getPostTags().stream()
                        .map(PostTag::getTagId)
                        .toList()))
                .comments(commentMapper.map(post.getComments()))
                .commentCount(Optional.ofNullable(post.getComments())
                        .map(Set::size)
                        .orElse(0))
                .reactionCount(Optional.ofNullable(post.getReactions())
                        .map(Set::size)
                        .orElse(0))
                .build();
    }

    private Post getBuild(PostCreateDto postCreateDto) {
        try {
            return Post.builder()
                    .title(postCreateDto.title())
                    .text(postCreateDto.text())
                    .authorId(postCreateDto.authorId())
                    .image(postCreateDto.image().getBytes())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
