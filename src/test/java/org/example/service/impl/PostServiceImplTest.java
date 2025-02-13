package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.manager.PostManager;
import org.example.manager.UserManager;
import org.example.mapper.PostMapper;
import org.example.mapper.PostReactionMapper;
import org.example.model.Post;
import org.example.model.PostReaction;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReactionDto;
import org.example.model.dto.PostReadDto;
import org.example.model.enums.ReactionType;
import org.example.repo.interfaces.PostReactionRepository;
import org.example.repo.interfaces.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
    @Mock
    private  PostRepository postRepository;
    @Mock
    private  PostMapper postMapper;
    @Mock
    private  PostReactionMapper reactionMapper;
    @Mock
    private  UserManager userManager;
    @Mock
    private  PostManager postManager;
    @Mock
    private  PostReactionRepository postReactionRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void savePost() {
        //before
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .title("Test title")
                .text("Test text")
                .authorId(1L)
                .build();

        PostReadDto postReadDto = PostReadDto.builder().build();
        Post post = new Post();
        when(postMapper.map(eq(postCreateDto))).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.map(any(Post.class))).thenReturn(postReadDto);
        when(postManager.findPostById(any())).thenReturn(post);

        //when
        PostReadDto result = postService.savePost(postCreateDto);

        //then
        assertThat(result).isEqualTo(postReadDto);
        verify(postMapper, times(1)).map(postCreateDto);
        verify(userManager, times(1)).findUserById(eq(1L));
    }

    @Test
    void addReaction() {
        //before
        PostReactionDto postReactionDto = new PostReactionDto(1L, ReactionType.LIKE);
        PostReaction postReaction = new PostReaction();
        when(reactionMapper.map(eq(postReactionDto))).thenReturn(postReaction);
        when(postReactionRepository.save(any(PostReaction.class))).thenReturn(postReaction);
        //when
        postService.addReaction(postReactionDto);

        //then
        verify(postManager, times(1)).findPostById(1L);
        verify(postReactionRepository, times(1)).save(postReaction);
    }
    @Test
    void addReactionToNotFoundPost() {
        //before
        PostReactionDto postReactionDto = new PostReactionDto(100500L, ReactionType.LIKE);
        PostReaction postReaction = new PostReaction();
        when(postManager.findPostById(postReactionDto.postId())).thenThrow(new NotFoundException(""));

        //when
        assertThatThrownBy(() -> postService.addReaction(postReactionDto))
                .isInstanceOf(NotFoundException.class);

        //then
        verify(postManager, times(1)).findPostById(postReactionDto.postId());

    }
}
