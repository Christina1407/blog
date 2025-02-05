package org.example.service;

import org.example.model.Post;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public interface PostService {
    Post savePost(Long userId, Post post);
}
