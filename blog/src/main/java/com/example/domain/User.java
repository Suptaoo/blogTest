package com.example.domain;

import lombok.Data;

@Data
public class User {
    private Integer userId;

    private String username;

    private String password;

    private String email;

    private String created;

    private String lastModified;

    private  String token;
}