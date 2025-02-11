package org.example.service.interfaces;

import org.example.model.dto.CommentCreateDto;
import org.example.model.dto.CommentEditDto;
import org.example.model.dto.CommentReadDto;

public interface CommentService {
    CommentReadDto saveComment(CommentCreateDto commentCreateDto);

    void deleteComment(Long commentId);

    CommentReadDto editComment(CommentEditDto commentEditDto, Long commentId);
}
