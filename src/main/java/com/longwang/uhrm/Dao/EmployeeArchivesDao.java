package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.Contract;
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
    //添加记录
    public boolean Archive(EmployeeArchives employeeArchives){
            return employeeArchivesMapper.insertEmployee(employeeArchives) == 1;
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
        return employeeArchivesMapper.informationChange(convertdata) == 1;
    }

    public boolean updateEmployeeBaseInfo(convertdata convertdata){
        return employeeArchivesMapper.updateEmployeeBaseInfo(convertdata) == 1;
    }

    public boolean updateEmployeeSpecialInfo(convertdata convertdata){
        return employeeArchivesMapper.updateEmployeeSpecialInfo(convertdata) == 1;
    }

    public List<Contract> findAllContract(){
        return employeeArchivesMapper.findAllContract();
    }

    public Contract getContractById(int id){
        return employeeArchivesMapper.getContractById(id);
    }

    public List<Contract> getContractByName(String employeeName){
        return employeeArchivesMapper.getContractByName(employeeName);
    }

    //修改合同
    public boolean contract_change(Contract contract){
        return employeeArchivesMapper.contract_change(contract) == 1;
    }

    //删除合同
    public void delete_contract(int id){
        employeeArchivesMapper.delete_contract(id);
    }

    //新增合同
    public boolean add_contract(Contract contract){
        return employeeArchivesMapper.add_contract(contract) == 1;
    }

    //获取最大的ID
    public int max_id(){ return employeeArchivesMapper.max_id(); }

    //删除员工档案
    public void delete_employee(int id){
        employeeArchivesMapper.delete_employee(id);
    }
}
