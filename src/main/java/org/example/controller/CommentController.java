package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.dto.CommentCreateDto;
import org.example.model.dto.CommentEditDto;
import org.example.model.dto.CommentReadDto;
import org.example.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public CommentReadDto saveComment(@RequestBody @Valid CommentCreateDto commentCreateDto) {
        log.info("Попытка создания нового комментария {} to Post id = {} by User id = {}", commentCreateDto, commentCreateDto.postId(), commentCreateDto.authorId());
        return commentService.saveComment(commentCreateDto);
    }
    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("commentId") @Min(1) Long commentId) {
        log.info("Попытка удаления комментария id = {}", commentId);
        commentService.deleteComment(commentId);
    }

    @PutMapping("{commentId}")
    public CommentReadDto editComment(@PathVariable("commentId") @Min(1) Long commentId,
                                      @RequestBody @Valid CommentEditDto commentEditDto) {
        log.info("Попытка редактирования комментария {} id = {}", commentEditDto, commentId);
        return commentService.editComment(commentEditDto, commentId);
    }
}
