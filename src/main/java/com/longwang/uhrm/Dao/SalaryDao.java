package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Entity.SalaryParameters;
import com.longwang.uhrm.mapper.SalaryParametersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryDao {
    SalaryParametersMapper salaryParametersMapper;
    @Autowired
    void setSalaryParametersMapper(SalaryParametersMapper salaryParametersMapper){
        this.salaryParametersMapper = salaryParametersMapper;
    }

    public List<EmployeeArchives> getAllSalaryParameters(){
        return salaryParametersMapper.getAllSalaryParameters();
    }

    public boolean changeLevel(String level, int id){
        System.out.println(level + "   " + id);
        return salaryParametersMapper.updateSalaryLevel(level, id) == 1;
    }

    public boolean createNewSalaryParameters(int id){
        return salaryParametersMapper.insertNewSalary(id) == 1;
    }
}
