package org.example.mapper;

import org.example.model.Comment;
import org.example.model.dto.CommentCreateDto;
import org.example.model.dto.CommentReadDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public List<CommentReadDto> map(Set<Comment> comments) {
        return comments.stream()
                .map(this::getBuild)
                .collect(Collectors.toList());
    }

    public CommentReadDto map(Comment comment) {
        return getBuild(comment);
    }

    public Comment map(CommentCreateDto commentCreateDto) {
        return getBuild(commentCreateDto);
    }
    private CommentReadDto getBuild(Comment comment) {
        return CommentReadDto.builder()
                .id(comment.getId())
                .authorId(comment.getAuthorId())
                .postId(comment.getPostId())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText())
                .build();
    }

    private Comment getBuild(CommentCreateDto commentCreateDto) {
        return Comment.builder()
                .text(commentCreateDto.text())
                .authorId(commentCreateDto.authorId())
                .postId(commentCreateDto.postId())
                .build();
    }
}
