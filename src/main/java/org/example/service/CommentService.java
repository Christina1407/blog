package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.example.model.dto.CommentCreateDto;
import org.example.model.dto.CommentEditDto;
import org.example.model.dto.CommentReadDto;

public interface CommentService {
    CommentReadDto saveComment(CommentCreateDto commentCreateDto);

    void deleteComment(Long commentId);

    CommentReadDto editComment(CommentEditDto commentEditDto, Long commentId);
}
