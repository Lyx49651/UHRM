package com.longwang.uhrm.Entity.mapper;

import com.longwang.uhrm.Entity.EmployeeArchives;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeArchivesMapper {
    @Select("select * from uhrm.employeearchives")
    List<EmployeeArchives> findAll();
}
