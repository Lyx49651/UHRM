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

    public boolean login(String telephone,String password){
        String pass = userMapper.getPassword(telephone);
        boolean test = pass.equals(password);
        System.out.println(test);
        return test;
    }

    public boolean register(User user){
        if(userMapper.insertUser(user) == 1){
            return true;
        }else{
            return false;
        }
    }

}
