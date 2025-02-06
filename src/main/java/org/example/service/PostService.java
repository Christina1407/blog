package org.example.service;

import org.example.model.Post;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;



public interface PostService {
    PostReadDto savePost(PostCreateDto postCreateDto);
}
