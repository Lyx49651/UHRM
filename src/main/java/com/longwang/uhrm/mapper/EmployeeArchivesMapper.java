package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.Contract;
import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Tool.convertdata;
import org.apache.ibatis.annotations.*;
import com.longwang.uhrm.Entity.InformationChange;

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
            "employeeIdentity, SalaryParameters_idSalaryParameters," +
            "Position_idPosition, Department_idDepartment, password) values(#{employeeId}, " +
            " #{employeeName}, #{employeeSex}, #{employeeBirthday}," +
            "#{employeeAddress}, #{employeePhoneNumber}, #{employeeDepartment}, #{employeeEducation}," +
            " #{employeePost}, #{employeeTitle}, #{employeeTechnicalGrade}, " +
            "#{employeeIdentity}, #{SalaryParameters_idSalaryParameters}," +
            "#{Position_idPosition}, #{Department_idDepartment}, #{password})")
    public int insertEmployee(EmployeeArchives employeeArchives);

    //3.1.1.2 信息浏览
    @Select("Select * from EmployeeArchives where employeeName like concat('%',#{employeeName},'%')")
    public List<EmployeeArchives> getEmployeeByName(String employeeName);



    //信息修改
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

    //查询所有合同
    @Select("select * from Contract")
    public List<Contract> findAllContract();

    @Select("select * from Contract where idContract = #{idContract}")
    public Contract getContractById(long employeeId);

    @Select("Select * from Contract where employeeName like concat('%',#{employeeName},'%')")
    public List<Contract> getContractByName(String employeeName);
    //合同修改
    @Update("update Contract set employeeName = #{employeeName},employeeSex = #{employeeSex},contractPeriod = #{contractPeriod}," +
            "salary = #{salary},position = #{position} where idContract = #{idContract}")
    public int contract_change(Contract contract);

    //合同删除
    @Delete("delete from Contract where idContract = #{idContract}")
    public void delete_contract(int id);

    //添加合同
    @Insert("insert into Contract(idContract,employeeId,employeeName,employeeSex,contractPeriod,salary,position) values(#{idContract},#{employeeId},#{employeeName},#{employeeSex}," +
            "#{contractPeriod},#{salary},#{position})")
    public int add_contract(Contract contract);

}
