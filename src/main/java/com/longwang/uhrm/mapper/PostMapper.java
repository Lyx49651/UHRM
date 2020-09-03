package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select * from post where idPost = #{idPost}")
    Post getPostById(int postId);

    @Select("select * from post")
    List<Post> findAll();

    @Insert("insert into post values(#{idPost}, #{postName})")
    int insertPost(Post post);
}
