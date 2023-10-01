package com.example.blog.service.impl;

import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert DTO to Entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        // convert Entity to DTO
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();
        List<PostDto> content = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(postResponse.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto dto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());

        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);
    }

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto dto) {
        Post post = mapper.map(dto, Post.class);
        return post;
    }
}
