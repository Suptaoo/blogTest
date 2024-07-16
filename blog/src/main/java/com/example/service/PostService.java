package com.example.service;

import com.example.domain.Post;

import java.util.List;

public interface PostService {


    int deleteByPrimaryKey(Integer postId);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer postId);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

    List<Post> selectPostByUserId(Integer userId);
}


