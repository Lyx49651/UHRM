package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Tool.convertdata;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.longwang.uhrm.Entity.InformationChange;
import org.apache.ibatis.annotations.Update;

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
            "employeePost, employeeTitle, employeeTechnicalGrade, " +
            "employeeIdentity, salaryParametersIdSalaryParameters," +
            "positionIdPosition, departmentIdDepartment, password) values(#{employeeId}, " +
            " #{employeeName}, #{employeeSex}, #{employeeBirthday}," +
            "#{employeeAddress}, #{employeePhoneNumber}, #{employeeDepartment}, #{employeeEducation}," +
            " #{employeePost}, #{employeeTitle}, #{employeeTechnicalGrade}, " +
            "#{employeeIdentity}, #{salaryParametersIdSalaryParameters}," +
            "#{positionIdPosition}, #{departmentIdDepartment}, #{password} )")
    public int insertEmployee(EmployeeArchives employeeArchives);

    //3.1.1.2 信息浏览
    @Select("Select * from EmployeeArchives where employeeName like concat('%',#{employeeName},'%')")
    public List<EmployeeArchives> getEmployeeByName(String employeeName);


    //信息修改一条
    @Insert("insert into InformationChange(idInformationChange, employeeId, employeeName, change_time, " +
            "change_type, change_original, change_now) values(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{changeType}, #{changeInfoOriginal}, #{changeInfoNow})")
    public int informationChange(convertdata convertdata);


    //信息更新
    @Update("UPDATE EmployeeArchives SET employeeName = #{employeeName},employeeSex = #{employeeSex},employeeBirthday = #{employeeBirthday}," +
            "employeeAddress = #{employeeAddress},employeePhoneNumber = #{employeePhone} WHERE employeeId = #{employeeId}")
    public int updateEmployeeBaseInfo(convertdata convertdata);

    @Update("UPDATE EmployeeArchives SET ${changeType} = #{changeInfoNow} WHERE employeeId = #{employeeId}")
    public int updateEmployeeSpecialInfo(convertdata convertdata);

}
