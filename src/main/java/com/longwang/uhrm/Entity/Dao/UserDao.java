package com.longwang.uhrm.Entity.Dao;

import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Entity.User;
import com.longwang.uhrm.Entity.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.longwang.uhrm.Entity.mapper.EmployeeArchivesMapper;

import java.util.List;
@Service("userDao")
public class UserDao {
    @Autowired
    UserMapper userMapper;

    //登录
    public boolean login(String telephone,String password){
        String pass = userMapper.getPassword(telephone);
        boolean test = pass.equals(password);
        System.out.println(test);
        return test;
    }

    //注册
    public boolean register(User user){
        if(userMapper.insertUser(user) == 1){
            return true;
        }else{
            return false;
        }
    }
    //按id查找返回用户
    public User getUserById(int userId){
        return userMapper.getUserById(userId);
    }

    //按name查找返回用户
    public User getUserByName(String name){
        return userMapper.getUserByName(name);
    }

    //按id删除用户
    public boolean deleteUserById(int userId){
        return userMapper.deleteUserById(userId);
    }

    //按name删除用户
    public boolean deleteUserById(String name){
        return userMapper.deleteUserByName(name);
    }

}
