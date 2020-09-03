package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.CollectTable;
import com.longwang.uhrm.Entity.RecruitmentNotice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectTableMapper {
    @Select("select * from CollectTable")
    List<CollectTable> findAll();

    @Select("select * from CollectTable where status = 'passed'")
    List<CollectTable> findAllPassed();

    @Select("select * from CollectTable where status = 'saved'")
    List<CollectTable> findAllSaved();

    @Insert("insert into CollectTable values(#{id}, #{departmentIdDepartment}, #{departmentName}, #{recutimentNumber}, #{idPost}" +
            ", #{namePost}, #{status})")
    public int insertCollectTable (CollectTable collectTable);

    //改变状态
    @Update("update CollectTable set status = #{status} where id = #{id}")
    int changeStatusById(String status, int id);

    @Delete("delete from CollectTable where id = #{id}")
    public boolean deleteByID(int id);

    @Update("update CollectTable set status = \"passed\" , recutimentNumber = #{recutimentNumber} where id = #{id}")
    public boolean updateStatus(@Param("id") int id,@Param("recutimentNumber") String recutimentNumber);

}
