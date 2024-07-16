package com.example.controller;

import cn.hutool.core.util.ObjUtil;
import com.example.config.AuthAccess;
import com.example.config.Result;
import com.example.domain.Post;
import com.example.domain.User;
import com.example.exception.ServiceException;
import com.example.mapper.PostMapper;
import com.example.service.PostService;
import com.example.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("api/posts")
public class PostController {
    @Resource
    PostService postService;
    @PostMapping()
    public Result  posts(Post post){
         if (ObjUtil.isEmpty(post)) {
            throw  new ServiceException("文章不能为空");
        }
         try {
             User currentUser = TokenUtils.getCurrentUser();
             post.setUserId(currentUser.getUserId());
             postService.insert(post);
         }catch (Exception e ){
             throw new ServiceException("新增数据出错");
         }
         return  new Result("200","发布文章成功","");
    }
    @AuthAccess
    @GetMapping
    public List<Post> getPostByUserId(Integer userId){
        return  postService .selectPostByUserId(userId);
    }
    @AuthAccess
    @GetMapping("/{id}")
    public  Post getPostByPostId(@RequestParam Integer id){
        return postService.selectByPrimaryKey(id);
    }

    @PutMapping("/{id}")
    public  Result   UpdatePost(@RequestParam  Integer id,@RequestBody Post post){
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getUserId().equals(post.getUserId())){
            postService.updateByPrimaryKeySelective(post);
            return  new Result("200","更新成功","");
        }
        return  new Result("201","无权修改","");
    }

    @DeleteMapping("{id}")
    public  Result   DeletePost(@RequestParam  Integer id,@RequestBody Post post){
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getUserId().equals(id)){
            postService.updateByPrimaryKeySelective(post);
            return  new Result("200","更新成功","");
        }
        return  new Result("201","无权修改","");
    }
}
