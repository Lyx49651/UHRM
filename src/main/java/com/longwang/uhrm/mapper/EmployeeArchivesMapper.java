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
            "#{employeeName}, now(), #{selectedInfo[0]}, #{changeInfoOriginal[0]}, #{changeInfoNow[0]})")
    public int informationChangeOne(convertdata convertdata);

    //信息修改两条
    @Insert("insert into InformationChange(idInformationChange, employeeId, employeeName, change_time, " +
            "change_type, change_original, change_now) values(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[0]}, #{changeInfoOriginal[0]}, #{changeInfoNow[0]})," +
            "(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[1]}, #{changeInfoOriginal[1]}, #{changeInfoNow[1]})")
    public int informationChangeTwo(convertdata convertdata);

    //信息修改三条
    @Insert("insert into InformationChange(idInformationChange, employeeId, employeeName, change_time, " +
            "change_type, change_original, change_now) values(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[0]}, #{changeInfoOriginal[0]}, #{changeInfoNow[0]})," +
            "(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[1]}, #{changeInfoOriginal[1]}, #{changeInfoNow[1]})," +
            "(#{idInformationChange}, #{employeeId},#{employeeName}, now(), #{selectedInfo[2]}, #{changeInfoOriginal[2]}," +
            "#{changeInfoNow[2]})")
    public int informationChangeThree(convertdata convertdata);

    //信息修改四条
    @Insert("insert into InformationChange(idInformationChange, employeeId, employeeName, change_time, " +
            "change_type, change_original, change_now) values(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[0]}, #{changeInfoOriginal[0]}, #{changeInfoNow[0]})," +
            "(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[1]}, #{changeInfoOriginal[1]}, #{changeInfoNow[1]})," +
            "(#{idInformationChange}, #{employeeId},#{employeeName}, now(), #{selectedInfo[2]}, #{changeInfoOriginal[2]}," +
            "#{changeInfoNow[2]}),(#{idInformationChange}, #{employeeId},#{employeeName}, now(), " +
            "#{selectedInfo[3]}, #{changeInfoOriginal[3]},#{changeInfoNow[3]})")
    public int informationChangeFour(convertdata convertdata);

    //信息修改五条
    @Insert("insert into InformationChange(idInformationChange, employeeId, employeeName, change_time, " +
            "change_type, change_original, change_now) values(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[0]}, #{changeInfoOriginal[0]}, #{changeInfoNow[0]})," +
            "(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[1]}, #{changeInfoOriginal[1]}, #{changeInfoNow[1]})," +
            "(#{idInformationChange}, #{employeeId},#{employeeName}, now(), #{selectedInfo[2]}, #{changeInfoOriginal[2]}," +
            "#{changeInfoNow[2]}),(#{idInformationChange}, #{employeeId},#{employeeName}, now(), " +
            "#{selectedInfo[3]}, #{changeInfoOriginal[3]},#{changeInfoNow[3]}),(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[4]}, #{changeInfoOriginal[4]}, #{changeInfoNow[4]})")
    public int informationChangeFive(convertdata convertdata);

    //信息修改六条
    @Insert("insert into InformationChange(idInformationChange, employeeId, employeeName, change_time, " +
            "change_type, change_original, change_now) values(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[0]}, #{changeInfoOriginal[0]}, #{changeInfoNow[0]})," +
            "(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[1]}, #{changeInfoOriginal[1]}, #{changeInfoNow[1]})," +
            "(#{idInformationChange}, #{employeeId},#{employeeName}, now(), #{selectedInfo[2]}, #{changeInfoOriginal[2]}," +
            "#{changeInfoNow[2]}),(#{idInformationChange}, #{employeeId},#{employeeName}, now(), " +
            "#{selectedInfo[3]}, #{changeInfoOriginal[3]},#{changeInfoNow[3]}),(#{idInformationChange}, #{employeeId}," +
            "#{employeeName}, now(), #{selectedInfo[4]}, #{changeInfoOriginal[4]}, #{changeInfoNow[4]})," +
            "(#{idInformationChange}, #{employeeId},#{employeeName}, now(), #{selectedInfo[5]}, #{changeInfoOriginal[5]}, #{changeInfoNow[5]})")
    public int informationChangeSix(convertdata convertdata);

    //信息更新，类型一
    @Update("UPDATE EmployeeArchives SET employeeName = #{employeeName},employeeSex = #{employeeSex},employeeBirthday = #{employeeBirthday}," +
            "employeeAddress = #{employeeAddress},employeePhoneNumber = #{employeePhone},#{selectedInfo[0]} = #{changeInfoNow[0]} WHERE employeeId = #{employeeId}")
    public int updateEmployeeInfoOne(convertdata convertdata);

    //信息更新，类型二
    @Update("UPDATE EmployeeArchives SET employeeName = #{employeeName},employeeSex = #{employeeSex},employeeBirthday = #{employeeBirthday}," +
            "employeeAddress = #{employeeAddress},employeePhoneNumber = #{employeePhone},#{selectedInfo[0]} = #{changeInfoNow[0]}," +
            "#{selectedInfo[1]} = #{changeInfoNow[1]} WHERE employeeId = #{employeeId}")
    public int updateEmployeeInfoTwo(convertdata convertdata);

    //信息更新，类型三
    @Update("UPDATE EmployeeArchives SET employeeName = #{employeeName},employeeSex = #{employeeSex},employeeBirthday = #{employeeBirthday}," +
            "employeeAddress = #{employeeAddress},employeePhoneNumber = #{employeePhone},#{selectedInfo[0]} = #{changeInfoNow[0]}," +
            "#{selectedInfo[1]} = #{changeInfoNow[1]},#{selectedInfo[2]} = #{changeInfoNow[2]} WHERE employeeId = #{employeeId}")
    public int updateEmployeeInfoThree(convertdata convertdata);

    //信息更新，类型四
    @Update("UPDATE EmployeeArchives SET employeeName = #{employeeName},employeeSex = #{employeeSex},employeeBirthday = #{employeeBirthday}," +
            "employeeAddress = #{employeeAddress},employeePhoneNumber = #{employeePhone},#{selectedInfo[0]} = #{changeInfoNow[0]}," +
            "#{selectedInfo[1]} = #{changeInfoNow[1]},#{selectedInfo[2]} = #{changeInfoNow[2]},#{selectedInfo[3]} = #{changeInfoNow[3]} WHERE employeeId = #{employeeId}")
    public int updateEmployeeInfoFour(convertdata convertdata);

    //信息更新，类型五
    @Update("UPDATE EmployeeArchives SET employeeName = #{employeeName},employeeSex = #{employeeSex},employeeBirthday = #{employeeBirthday}," +
            "employeeAddress = #{employeeAddress},employeePhoneNumber = #{employeePhone},#{selectedInfo[0]} = #{changeInfoNow[0]}," +
            "#{selectedInfo[1]} = #{changeInfoNow[1]},#{selectedInfo[2]} = #{changeInfoNow[2]},#{selectedInfo[3]} = #{changeInfoNow[3]}," +
            "#{selectedInfo[4]} = #{changeInfoNow[4]} WHERE employeeId = #{employeeId}")
    public int updateEmployeeInfoFive(convertdata convertdata);

    //信息更新，类型六
    @Update("UPDATE EmployeeArchives SET employeeName = #{employeeName},employeeSex = #{employeeSex},employeeBirthday = #{employeeBirthday}," +
            "employeeAddress = #{employeeAddress},employeePhoneNumber = #{employeePhone},#{selectedInfo[0]} = #{changeInfoNow[0]}," +
            "#{selectedInfo[1]} = #{changeInfoNow[1]},#{selectedInfo[2]} = #{changeInfoNow[2]},#{selectedInfo[3]} = #{changeInfoNow[3]}," +
            "#{selectedInfo[4]} = #{changeInfoNow[4]},#{selectedInfo[5]} = #{changeInfoNow[5]} WHERE employeeId = #{employeeId}")
    public int updateEmployeeInfoSix(convertdata convertdata);

}
