package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.Position;
import com.longwang.uhrm.Entity.Post;
import com.longwang.uhrm.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionDao {
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    DepartmentDao departmentDao;

    //根据id获得position
    public Position getPosByID(int idPosition){
        return positionMapper.getPosById(idPosition);
    }



    //根据idposition获得post表中的name
    public String getPostName(int idPosition){
        return positionMapper.getName(idPosition);
    }

    //根据idposition查询得到对应的post
    public Post getPost(int idPosition){
        return positionMapper.getPost(idPosition);
    }

    //根据部门名返回部门下的岗位列表
    public List<Position> getPostByDepartment(String nameDepartment){
        return positionMapper.getPositionListByDepartment(departmentDao.getId(nameDepartment));
    }


    //根据岗位名和部门名查询该部门该岗位现有人数，例：人事部--助理
    public int getStuffNumByPosition_and_Department(String employeeDepartment,String employeePosition){
        List<Integer> nums = positionMapper.getPositionStuff(employeeDepartment, employeePosition);
        return nums.size();
    }

    //根据positionName和departmenName获取相应部门岗位的编制人数，例：人事部--助理--编制人数
    public long getRecruitment(String posName, String departName){
        System.out.println(departmentDao.getId(departName));
        return positionMapper.getRecruitment(posName, departmentDao.getId(departName));
    }

    //根据typename(postname)和department获得position表中的id
    public int getId(String typeName,String departmentName){
        return positionMapper.getId(typeName,departmentDao.getId(departmentName));
    }

    //根据Post表中的postName获得Post表的id
    public int getPostId(String postName){
        return positionMapper.getPostId(postName);
    }

    //根据Post表中的idPost获得Post表的postName
    public String getPostName(long postId){
        return positionMapper.getPostName(postId);
    }
}
