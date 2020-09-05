package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.Attendance;
import com.longwang.uhrm.Entity.Contract;
import com.longwang.uhrm.Entity.EmployeeArchives;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    @Select("Select * from Attendance where attendanceTime like concat(#{attendanceTime},'%') and employeeId = #{employeeId}")
    public List<Attendance> getEmployeeAttendance (@Param("attendanceTime") String attendanceTime, @Param("employeeId") int employeeId);
//
//    @Insert("insert into Attendance(attendanceTime,employeeId,attendanceAssistantName,attendanceAssistantId) values(#{attendanceTime},#{employeeId})")
//    public boolean putList(@Param("employeeId") int employeeId, @Param("attendanceTime") String attendanceTime);

    @Insert("insert into Attendance values(#{attendanceTime},#{employeeId},#{attendanceAssistantName},#{attendanceAssistantId},#{employeeName},#{attendanceResults})")
    public boolean putList(Attendance attendance);

    @Select("Select * from Attendance where attendanceTime = #{attendanceTime}")
    public List<Attendance> getAttendance(String attendanceTime);

    @Select("Select * from Attendance where attendanceTime = #{attendanceTime} and employeeId = #{employeeId}")
    public Attendance checkAttendance(@Param("attendanceTime") String attendanceTime,@Param("employeeId") int employeeId);


}
