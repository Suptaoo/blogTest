package com.example.domain;

import lombok.Data;

@Data
public class Post {
    private Integer postId;

    private String title;

    private String content;

    private Integer userId;

    private String created;

    private String lastModified;
}