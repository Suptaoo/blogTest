package com.example.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.example.mapper.PostMapper;
import com.example.domain.Post;
import com.example.service.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;

    @Override
    public int deleteByPrimaryKey(Integer postId) {
        return postMapper.deleteByPrimaryKey(postId);
    }

    @Override
    public int insert(Post record) {
        return postMapper.insert(record);
    }

    @Override
    public int insertSelective(Post record) {
        return postMapper.insertSelective(record);
    }

    @Override
    public Post selectByPrimaryKey(Integer postId) {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    public int updateByPrimaryKeySelective(Post record) {
        return postMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Post record) {
        return postMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Post> selectPostByUserId(Integer userId) {
        return postMapper.selectPostByUserId(userId);
    }

}


