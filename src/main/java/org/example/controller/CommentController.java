package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.dto.CommentCreateDto;
import org.example.model.dto.CommentReadDto;
import org.example.service.CommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentReadDto saveComment(@RequestBody @Valid CommentCreateDto commentCreateDto) {
        log.info("Попытка создания нового комментария {} to Post id = {} by User id = {}", commentCreateDto, commentCreateDto.postId(), commentCreateDto.authorId());
        return commentService.saveComment(commentCreateDto);
    }
}
