package com.longwang.uhrm.Entity.Dao;

import com.longwang.uhrm.Entity.EmployeeArchives;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.longwang.uhrm.Entity.mapper.EmployeeArchivesMapper;

import java.util.List;


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

    public EmployeeArchives getEmployeeById(long id){
        return employeeArchivesMapper.getEmployeeById(id);
    }
}
