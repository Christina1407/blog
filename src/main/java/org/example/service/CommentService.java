package org.example.service;

import org.example.model.dto.CommentCreateDto;
import org.example.model.dto.CommentReadDto;

public interface CommentService {
    CommentReadDto saveComment(CommentCreateDto commentCreateDto);
}
