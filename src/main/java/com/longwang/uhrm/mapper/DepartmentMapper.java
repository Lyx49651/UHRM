package com.longwang.uhrm.mapper;
import com.longwang.uhrm.Entity.Department;
import com.longwang.uhrm.Entity.EmployeeArchives;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select * from Department where idDepartment = #{idDepartment}")
    public Department getDepartmentById(int idDepartment);

    @Select("select nameDepartment from Department where idDepartment = #{idDepartment}")
    public String getName(int idDepartment);

    @Select("select idDepartment from Department where nameDepartment = #{nameDepartment}")
    public int getID(String nameDepartment);
}
