package com.longwang.uhrm.Entity.mapper;

import com.longwang.uhrm.Entity.EmployeeArchives;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface EmployeeArchivesMapper {
    @Select("select * from uhrm.EmployeeArchives")
    public List<EmployeeArchives> findAll();

    @Select("select password from EmployeeArchives where employeeId = #{id}")
    public String getPassword(int id);

    @Select("select employeeName from EmployeeArchives where employeeId = #{id}")
    public String getName(int id);

    @Select("select * from EmployeeArchives where employeeId = #{employeeId}")
    public EmployeeArchives getEmployeeById(long employeeId);

    @Insert("insert into EmployeeArchives(employeeId, employeeName, employeeSex, employeeBirthday," +
            "employeeAddress, employeePhoneNumber, employeeDepartment, employeeEducation," +
            "employeeEducation, employeePost, employeeTitle, employeeTechnicalGrade, " +
            "employeeIdentity, employeeProfessionalTitle, salaryParametersIdSalaryParameters," +
            "positionIdPosition, departmentIdDepartment, password) values(#{employeeId}, " +
            " #{employeeName}, #{employeeSex}, #{employeeBirthday}," +
            "#{employeeAddress}, #{employeePhoneNumber}, #{employeeDepartment}, #{employeeEducation}," +
            "#{employeeEducation}, #{employeePost}, #{employeeTitle}, #{employeeTechnicalGrade}, " +
            "#{employeeIdentity}, #{employeeProfessionalTitle}, #{salaryParametersIdSalaryParameters}," +
            "#{positionIdPosition}, #{departmentIdDepartment}, #{password} )")
    public int insertEmployee(EmployeeArchives employeeArchives);
}
