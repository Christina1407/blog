package org.example.mapper;

import org.example.model.Post;
import org.example.model.dto.CommentReadDto;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {

    public PostReadDto map(Post post, List<CommentReadDto> comments ) {
        return getBuild(post, comments );
    }
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

    private PostReadDto getBuild(Post post, List<CommentReadDto> comments) {
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdDate(post.getCreatedDate())
                .authorId(post.getAuthorId())
                .comments(comments)
                .build();
    }

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
                .image(postCreateDto.image())
                .build();
    }

}
