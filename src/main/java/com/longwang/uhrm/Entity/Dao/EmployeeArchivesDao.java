package com.longwang.uhrm.Entity.Dao;

import com.longwang.uhrm.Entity.EmployeeArchives;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.longwang.uhrm.Entity.mapper.EmployeeArchivesMapper;

@Service("employeeDao")
public class EmployeeArchivesDao {
    EmployeeArchivesMapper mapper;

    @Autowired
    void setMapper(){};

    public boolean authenticate(int id, String password){
        mapper.getPassword(id);
        return password.equals(mapper.getPassword(id));
    }

    public boolean register(EmployeeArchives employeeArchives){
         if(mapper.getEmployeeById(employeeArchives.getEmployeeId()) == null){
             return mapper.insertEmployee(employeeArchives) == 1;
         }
         return false;
    }
}
