package com.example.demo.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostsDTO {
    private String content;

    private long author_id;

    private Date timestamp;
    private List<PostImagesDTO> images;
}
