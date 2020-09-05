package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.EmployeeArchives;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SalaryParametersMapper {
    @Select("SELECT employeeId, employeeName, employeeDepartment, levelSalaryParameters FROM EmployeeArchives INNER JOIN SalaryParameters ON employeeId = idSalaryParameters")
    List<EmployeeArchives> getAllSalaryParameters();

    @Update("UPDATE SalaryParameters SET levelSalaryParameters = #{levelSalary} WHERE idSalaryParameters = ${employeeId} ")
    int updateSalaryLevel(@Param("levelSalary") String levelSalary, @Param("employeeId") int employeeId);

    @Insert("INSERT into SalaryParameters(idSalaryParameters) values (${idEmployee})")
    int insertNewSalary(@Param("idEmployee") int id);
}
