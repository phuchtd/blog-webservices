package com.example.blog.payload;

import lombok.Data;

import java.util.List;

@Data
public class PostDtoV2 extends PostDto {
    private List<String> tags;
}
