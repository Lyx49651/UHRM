package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.Position;
import com.longwang.uhrm.Entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PositionMapper {
    @Select("select * from Position where idPosition = #{idPosition}")
    public Position getPosById(int idPosition);

    @Select("Select idPosition from Position where typePosition = #{typePosition} and departmentId = #{departmentId}")
    public int getId(String typePosition, int departmentId);

    @Select("SELECT postName FROM uhrm.Post, Position where Position.idPosition = idPost and idPosition = #{idPosition}")
    public String getName(int idPosition);
    //sql

    @Select("SELECT idPost,postName FROM Post, Position where Position.idPosition = idPost and idPosition = #{idPosition}")
    public Post getPost(int idPosition);

    @Select("Select * from Position where departmentId = #{departmentId}")
    public List<Position> getPositionListByDepartment(int departmentId);

    @Select("Select employeeId from EmployeeArchives where employeeDepartment = #{employeeDepartment} and employeePost = #{employeePost}")
    public List<Integer> getPositionStuff(String employeeDepartment, String employeePost);

    @Select("Select employeeId from EmployeeArchives where Position_idPosition = #{Position_idPosition} and Department_idDepartment = #{Department_idDepartment}")
    public List<Integer> getPositionStuffAdvanced(int Position_idPosition, int Department_idDepartment);


    @Select("Select totalStaff from Position where typePosition = #{typePositon} and departmentId = #{departmentId}")
    public long getRecruitment(String typePosition,int departmentId);

    @Select("Select idPost from Post where postName = #{postName}")
    public int getPostId(String postName);
}
