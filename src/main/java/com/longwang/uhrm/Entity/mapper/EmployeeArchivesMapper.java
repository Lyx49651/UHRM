package com.longwang.uhrm.Entity.mapper;

import com.longwang.uhrm.Entity.EmployeeArchives;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface EmployeeArchivesMapper {
    @Select("select * from uhrm.employeeArchives")
    public List<EmployeeArchives> findAll();

    @Select("select password from uhrm.employeeArchives where id = #{id}")
    public String getPassword(int id);

    @Select("select id from uhrm.employeeArchives where id = #{employeeId}")
    public EmployeeArchives getEmployeeById(long employeeId);

    @Insert("insert into uhrm.employeeArchives(employeeId, employeeName, password) values(#{employeeId}, " +
            " #{employeeName}, #{password} )")
    public int insertEmployee(EmployeeArchives employeeArchives);
}
