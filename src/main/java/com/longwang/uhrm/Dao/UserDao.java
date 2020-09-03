package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.User;
import com.longwang.uhrm.Tool.convertdata;
import com.longwang.uhrm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //找回密码
    public String retrieve_password(convertdata convertdata){
        return userMapper.retrieve_password(convertdata);
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

    public String getName(String phone){
        return userMapper.getName(phone);
    }

    //根据CandidateInfo（面试用户）获取对应的user表中的所有用户信息的函数
    public List<User> getUserByCandiate(){
        return userMapper.getUsrByCandidate();
    }

    //获取通过的user信息
    public List<User> getUserPassed(){
        return userMapper.userPassed();
    }

    //根据CandidateInfoID为没有通过资料审查的用户更新状态
    public void update_unpassed(long id){ userMapper.unpassed_update(id); }

    //根据CandidateInfoID为通过资料审查的用户更新状态
    public void update_passed(long id){ userMapper.passed_update(id); }
}
