package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.PostManager;
import org.example.manager.UserManager;
import org.example.mapper.CommentMapper;
import org.example.model.Comment;
import org.example.model.dto.CommentCreateDto;
import org.example.model.dto.CommentReadDto;
import org.example.repo.CommentRepository;
import org.example.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
}
