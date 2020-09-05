package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.CandidateInfo;
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

    //按telephone查找返回用户
    public User getUserByTelephone(String telephone){
        return userMapper.getUserByTelephone(telephone);
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

    //修改用户信息
    public boolean update_user_Info(User user){
        return userMapper.update_user_Info(user) == 1;
    }

    //根据CandidateInfoID为没有通过资料审查的用户更新状态
    public void update_unpassed(long id){ userMapper.unpassed_update(id); }

    //根据CandidateInfoID为通过资料审查的用户更新状态
    public void update_passed(long id){ userMapper.passed_update(id); }

    //查询通过审核的候选人
    public List<CandidateInfo> userpassed_get(){ return userMapper.userTested();}

    //更新经过笔试和面试筛选的人
    public void usertested(long id){ userMapper.tested_update(id);}

    //更新未通过的人
    public void user_untested(long id){ userMapper.untested_delete(id);}

    //遍历即将归入员工的人
    public List<User> archive(){ return userMapper.testedUsers();}

    //归档结束删除所有tested的对象
    public  void delete_tested(){ userMapper.delete_tested();}

    //插入新候选人
    public void insert_candidate(CandidateInfo candidateInfo){userMapper.insert_candidate(candidateInfo);}

    //删除电话对应的用户，并返回他的密码
    public  String delete_phone(String phone){
        String password = userMapper.getUserByTelephone(phone).getPassword();
        userMapper.delete_user_phone(phone);
        return  password;
    }
}
