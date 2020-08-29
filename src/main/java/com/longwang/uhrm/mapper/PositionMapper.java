package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.Position;
import com.longwang.uhrm.Entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PositionMapper {
    @Select("select * from Position where idPosition = #{idPosition}")
    public Position getPosById(int idPosition);

    @Select("Select idPosition from Position where typePosition = #{typePosition}")
    public int getId(String typePosition);

    @Select("SELECT postName FROM uhrm.Post, Position where Position.idPosition = idPost and idPosition = #{idPosition}")
    public String getName(int idPosition);
    //sql

    @Select("SELECT idPost,postName FROM Post, Position where Position.idPosition = idPost and idPosition = #{idPosition}")
    public Post getPost(int idPosition);
}
