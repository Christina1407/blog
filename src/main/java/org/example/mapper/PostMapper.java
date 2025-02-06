package org.example.mapper;

import org.example.model.Post;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private PostReadDto getBuild(Post post) {
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdDate(post.getCreatedDate())
                .authorId(post.getAuthorId())
                .build();
    }

    private Post getBuild(PostCreateDto postCreateDto) {
        return Post.builder()
                .title(postCreateDto.title())
                .text(postCreateDto.text())
                .authorId(postCreateDto.authorId())
                .build();
    }

    public PostReadDto map(Post post) {
        return getBuild(post);
    }

    public Post map(PostCreateDto postCreateDto) {
        return getBuild(postCreateDto);
    }
}
