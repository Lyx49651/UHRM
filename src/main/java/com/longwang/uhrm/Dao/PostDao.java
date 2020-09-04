package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.Post;
import com.longwang.uhrm.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostDao {
    @Autowired
    PostMapper postMapper;

    Post getPostById(int postId){
        return postMapper.getPostById(postId);
    }

    List<Post> findAll(){
        return postMapper.findAll();
    }

    boolean insertPost(Post post){
        return postMapper.insertPost(post) == 1;
    }
}
