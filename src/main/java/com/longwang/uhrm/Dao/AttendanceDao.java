package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.Attendance;
import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.mapper.AttendanceMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceDao {
    @Autowired
    AttendanceMapper attendanceMapper;
    @Autowired
    DepartmentDao departmentDao;
    @Autowired
    EmployeeArchivesDao employeeArchivesDao;

    //根据年月获取员工自己的考勤信息
    public List<Attendance> getEmployeeByTime(String attendanceTime, int employeeId){
        return attendanceMapper.getEmployeeAttendance(attendanceTime, employeeId);
    }

    //根据人员类别信息（department），显示人员表
    public List<EmployeeArchives> getList(String department){
        return departmentDao.getDepartmentEmployeeByName(department);
    }

    //当二级考勤人员选择类别后，遍历employeeId调用该函数，
    public boolean putList(String nameDepartment,int AssitantId){
        List<EmployeeArchives> employeeArchives = departmentDao.getDepartmentEmployeeByName(nameDepartment);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String today = ft.format(dNow);
        String AssitantName = employeeArchivesDao.getName(AssitantId);
        for(EmployeeArchives employeeArchives1 : employeeArchives){
            long employeeId = employeeArchives1.getEmployeeId();
            String employeeName = employeeArchives1.getEmployeeName();
            Attendance attendance = new Attendance(today,employeeId,AssitantName,AssitantId,employeeName,"intime");
            attendanceMapper.putList(attendance);
        }
        return true;
    }

    public Attendance checkAttendance(String time, int id){
        return attendanceMapper.checkAttendance(time,id);
    }

    public boolean putListE(Attendance attendance){
        return attendanceMapper.putList(attendance);
    }
}
