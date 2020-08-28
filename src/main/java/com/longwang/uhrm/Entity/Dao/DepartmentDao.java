package com.longwang.uhrm.Entity.Dao;
import com.longwang.uhrm.Entity.Department;
import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Entity.mapper.DepartmentMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.longwang.uhrm.Entity.mapper.EmployeeArchivesMapper;

import java.util.List;

@Service
public class DepartmentDao {

    @Autowired
    DepartmentMapper departmentMapper;
//按id获取部门信息
    public Department getDepartmentById(int idDepartment){
        return departmentMapper.getDepartmentById(idDepartment);
    }

    //按name获取id
    public int getId(String nameDepartment){
        return departmentMapper.getID(nameDepartment);
    }

    //按id获取name
    public String getName(int idDepartment){
        return departmentMapper.getName(idDepartment);
    }

}
