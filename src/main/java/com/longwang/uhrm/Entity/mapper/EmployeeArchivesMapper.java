package com.longwang.uhrm.Entity.mapper;

import com.longwang.uhrm.Entity.EmployeeArchives;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeArchivesMapper {
    @Select("select * from uhrm.employeeArchives")
    List<EmployeeArchives> findAll();

    @Select("select password from uhrm.employeeArchives where id = #{id}")
    String getPassword(int id);

    @Select("select id from uhrm.employeeArchives where id = #{employeeId}")
    EmployeeArchives getEmployeeById(long employeeId);

    @Insert("insert into uhrm.employeeArchives(employeeId, employeeName, password) values(#{employeeId}, " +
            " #{employeeName}, #{password} )")
    int insertEmployee(EmployeeArchives employeeArchives);
}
