package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.NotFoundException;
import org.example.manager.PostManager;
import org.example.manager.UserManager;
import org.example.mapper.CommentMapper;
import org.example.model.Comment;
import org.example.model.dto.CommentCreateDto;
import org.example.model.dto.CommentEditDto;
import org.example.model.dto.CommentReadDto;
import org.example.repo.interfaces.CommentRepository;
import org.example.service.interfaces.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final UserManager userManager;
    private final PostManager postManager;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;


    @Override
    public CommentReadDto saveComment(CommentCreateDto commentCreateDto) {
        userManager.findUserById(commentCreateDto.authorId());
        postManager.findPostById(commentCreateDto.postId());
        Comment comment = commentMapper.map(commentCreateDto);
        return commentMapper.map(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = getComment(commentId);
        commentRepository.delete(comment);

    }

    @Override
    public CommentReadDto editComment(CommentEditDto commentEditDto, Long commentId) {
        Comment comment = getComment(commentId);
        comment.setText(commentEditDto.text());
        return commentMapper.map(commentRepository.save(comment));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id = %d was not found", commentId)));
    }

}
