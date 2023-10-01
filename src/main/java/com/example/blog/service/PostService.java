package com.example.blog.service;

import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto dto, long id);

    void deletePostById(long id);
}
