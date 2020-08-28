package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.EmployeeArchives;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.longwang.uhrm.mapper.EmployeeArchivesMapper;

@Service
public class EmployeeArchivesDao{
    @Autowired
    EmployeeArchivesMapper employeeArchivesMapper;

    public boolean authenticate(int id, String password){
        System.out.println(employeeArchivesMapper.getPassword(id));
        return password.equals(employeeArchivesMapper.getPassword(id));
    }

    public boolean register(EmployeeArchives employeeArchives){
         if(employeeArchivesMapper.getEmployeeById(employeeArchives.getEmployeeId()) == null){
             return employeeArchivesMapper.insertEmployee(employeeArchives) == 1;
         }
         return false;
    }

    public String getName(int id) {
        return employeeArchivesMapper.getName(id);
    }

    //3.1.1.2 信息浏览 人事助理输入查询条件，查询员工的基本信息
    public EmployeeArchives getEmployeeById(long id){
        return employeeArchivesMapper.getEmployeeById(id);
    }

//    //3.1.1.2 信息浏览 员工输入查询条件，查询自己的基本信息
//    public Object userSearchInfo(String target, int id){
//        EmployeeArchives employee = employeeArchivesMapper.getEmployeeById(id);
//        if(target.equals("电话")){
//            return employee.getEmployeePhoneNumber();
//        }
//        else if(target.equals("地址")){
//            return employee.getEmployeeAddress();
//        }
//        //
//    }

}
