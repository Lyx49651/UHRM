package com.longwang.uhrm.Dao;
import com.longwang.uhrm.Entity.Department;
import com.longwang.uhrm.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //获得所有的department
    public List<Department> getAll(){return  departmentMapper.getAllDepartment();}
}
