package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Tool.convertdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.longwang.uhrm.Entity.InformationChange;
import com.longwang.uhrm.mapper.EmployeeArchivesMapper;

import java.util.List;

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

    public List<EmployeeArchives> getEmployeeByName(String employeeName){
        return employeeArchivesMapper.getEmployeeByName(employeeName);
    }

    public List<EmployeeArchives> findAllEmployee(){
        return employeeArchivesMapper.findAll();
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
    public boolean informationChange(convertdata convertdata){
        if(convertdata.getSelectedInfo().length == 1){
            return employeeArchivesMapper.informationChangeOne(convertdata) == 1;
        }else if(convertdata.getSelectedInfo().length == 2){
            return employeeArchivesMapper.informationChangeTwo(convertdata) == 1;
        }else if(convertdata.getSelectedInfo().length == 3){
            return employeeArchivesMapper.informationChangeThree(convertdata) == 1;
        }else if(convertdata.getSelectedInfo().length == 4){
            return employeeArchivesMapper.informationChangeFour(convertdata) == 1;
        }else if(convertdata.getSelectedInfo().length == 5){
            return employeeArchivesMapper.informationChangeFive(convertdata) == 1;
        }else{
            return employeeArchivesMapper.informationChangeSix(convertdata) == 1;
        }
    }

    public boolean updateEmployeeInfo(convertdata convertdata){
        if(convertdata.getSelectedInfo().length == 1){
            return employeeArchivesMapper.updateEmployeeInfoOne(convertdata) == 1;
        }else if(convertdata.getSelectedInfo().length == 2){
            return employeeArchivesMapper.updateEmployeeInfoTwo(convertdata) == 1;
        }else if(convertdata.getSelectedInfo().length == 3){
            return employeeArchivesMapper.updateEmployeeInfoThree(convertdata) == 1;
        }else if(convertdata.getSelectedInfo().length == 4){
            return employeeArchivesMapper.updateEmployeeInfoFour(convertdata) == 1;
        }else if(convertdata.getSelectedInfo().length == 5){
            return employeeArchivesMapper.updateEmployeeInfoFive(convertdata) == 1;
        }else{
            return employeeArchivesMapper.updateEmployeeInfoSix(convertdata) == 1;
        }
    }

}
