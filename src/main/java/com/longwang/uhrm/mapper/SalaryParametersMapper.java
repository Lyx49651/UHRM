package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Entity.SalaryLog;
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

    @Insert("INSERT into SalaryLog(EmployeeArchives_employeeId, time, amount) values (${id}, #{time}, #{amount})")
    int insertSalaryLog(@Param("id") int id, @Param("time") String time, @Param("amount") String amount);

    @Select("SELECT * from SalaryLog")
    List<SalaryLog> findAllLogs();
}
