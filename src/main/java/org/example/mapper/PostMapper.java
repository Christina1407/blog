package org.example.mapper;

import org.example.model.Post;
import org.example.model.dto.CommentReadDto;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class PostMapper {

    public PostReadDto map(Post post, List<CommentReadDto> comments, int reactionCount) {
        return getBuild(post, comments, reactionCount);
    }
    public PostReadDto map(Post post) {
        return getBuild(post, null); // TODO переделать
    }

    public PostReadDto map(Post post, List<String> tags) {
        return getBuild(post, tags);
    }

    public List<PostReadDto> map(List<Post> posts) {
        return posts.stream()
                .map(this::map)
                .toList();
    }

    public Post map(PostCreateDto postCreateDto) {
        return getBuild(postCreateDto);
    }

    private PostReadDto getBuild(Post post, List<CommentReadDto> comments, int reactionCount) {
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdDate(post.getCreatedDate())
                .authorId(post.getAuthorId())
                .comments(comments)
                .reactionCount(reactionCount)
                .build();
    }

    private PostReadDto getBuild(Post post, List<String> tags) {
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdDate(post.getCreatedDate())
                .authorId(post.getAuthorId())
                .commentCount(Optional.ofNullable(post.getComments())
                        .map(Set::size)
                        .orElse(0))
                .reactionCount(Optional.ofNullable(post.getReactions())
                        .map(Set::size)
                        .orElse(0))
                .tags(tags)
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
