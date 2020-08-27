package com.longwang.uhrm.Entity.mapper;

import com.longwang.uhrm.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select password from User where telephone = #{telephone}")
    public String getPassword(String telephone);

    @Insert("insert into User(name, sex, IDCard, photo, address, age, mailAddress, telephone, Post_idPost, password) " +
            "values(#{name}, #{sex}, #{IDCard}, #{photo}, #{address}, #{age}, #{mailAddress}, #{telephone}, #{postIdPost}, #{password})")
    public int insertUser(User user);
}
