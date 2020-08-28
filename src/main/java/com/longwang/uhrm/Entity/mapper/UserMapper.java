package com.longwang.uhrm.Entity.mapper;

import com.longwang.uhrm.Entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select *from User where idUser = #{idUser}")
    public User getUserById(int idUser);

    @Select("select *from User where name = #{name}")
    public User getUserByName(String name);

    @Select("select password from User where telephone = #{telephone}")
    public String getPassword(String telephone);

    @Select("select name from User where telephone = #{telephone}")
    public String getName(String telephone);


    @Insert("insert into User(name, sex, IDCard, photo, address, age, mailAddress, telephone, Post_idPost, password) " +
            "values(#{name}, #{sex}, #{IDCard}, #{photo}, #{address}, #{age}, #{mailAddress}, #{telephone}, #{postIdPost}, #{password})")
    public int insertUser(User user);

    @Delete("delete from user where idUser = #{idUser}")
    public boolean deleteUserById(int idUser);

    @Delete("delete from user where name = #{name}")
    public boolean deleteUserByName(String name);
}
