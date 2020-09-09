package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.CandidateInfo;
import com.longwang.uhrm.Entity.User;
import com.longwang.uhrm.Tool.convertdata;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select *from User where idUser = #{idUser}")
    public User getUserById(int idUser);

    @Select("select *from User where telephone = #{telephone}")
    public User getUserByTelephone(String telephone);

    @Select("select *from User where name = #{name}")
    public User getUserByName(String name);

    @Select("select password from User where telephone = #{telephone}")
    public String getPassword(String telephone);

    @Select("select name from User where telephone = #{telephone}")
    public String getName(String telephone);

    @Insert("insert into User(name, sex, IDCard, photo, address, age, mailAddress, telephone, Post_idPost, password) " +
            "values(#{name}, #{sex}, #{IDCard}, #{photo}, #{address}, #{age}, #{mailAddress}, #{telephone}, #{Post_idPost}, #{password})")
    public int insertUser(User user);

    @Delete("delete from user where idUser = #{idUser}")
    public boolean deleteUserById(int idUser);

    @Delete("delete from user where name = #{name}")
    public boolean deleteUserByName(String name);

    @Select("SELECT * FROM User,CandidateInfo where idUser = idCandidateInfo and status = 'unverified'")
    public List<User> getUsrByCandidate();

    @Select("SELECT * FROM User,CandidateInfo where CandidateInfo.status = \"passed\" and User.idUser = CandidateInfo.idCandidateInfo")
    public List<User> userPassed();

    @Select("SELECT * from User,CandidateInfo where CandidateInfo.status = \"tested\" and User.idUser = CandidateInfo.idCandidateInfo")
    public  List<User> testedUsers();

    @Update("UPDATE User SET sex = #{sex}, IDCard = #{IDCard}, address = #{address}," +
            "age = #{age},mailAddress = #{mailAddress},education=#{education} WHERE telephone = #{telephone}")
    public int update_user_Info(User user);

    @Update("UPDATE CandidateInfo set status = 'verified' where idCandidateInfo = #{id}")
    public void unpassed_update(long id);

    @Update("UPDATE CandidateInfo set status = 'passed' where idCandidateInfo = #{id}")
    public void passed_update(long id);

    @Select("SELECT * from CandidateInfo where CandidateInfo.status = \"passed\"")
    public List<CandidateInfo> userTested();

    @Update("UPDATE CandidateInfo set status = 'tested' where idCandidateInfo = #{id}")
    public void tested_update(long id);
    @Update("UPDATE CandidateInfo set status = 'verified' where idCandidateInfo = #{id}")
    public void untested_delete(long id);

    @Delete("Delete from CandidateInfo where status = \"tested\"")
    public void delete_tested();

    @Insert("INSERT into CandidateInfo(idCandidateInfo, status, departmentPost) values(#{idCandidateInfo}, #{status}, #{departmentPost})")
    public void insert_candidate(CandidateInfo candidateInfo);

    @Delete("DELETE from User where telephone = #{phone}")
    public void delete_user_phone(String phone);

    @Select("SELECT departmentPost from CandidateInfo where idCandidateInfo = #{id}")
    public String get_post(long id);

    //找回密码
    @Select("SELECT password FROM User where telephone = #{telephone} and name = #{name}")
    public String user_retrieve_password(@Param("telephone") String telephone,@Param("name") String name);
}

