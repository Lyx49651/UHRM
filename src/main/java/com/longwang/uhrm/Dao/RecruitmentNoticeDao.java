package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.RecruitmentNotice;
import com.longwang.uhrm.mapper.RecruitmentNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentNoticeDao {
    @Autowired
    RecruitmentNoticeMapper recruitmentNoticeMapper;

    //得到所有的对象
    public List<RecruitmentNotice> findAll(){
        return recruitmentNoticeMapper.findAll();
    }

    //按title得到相关部门的所有招聘信息
    public List<RecruitmentNotice> findAllByTitle(String title){
        return recruitmentNoticeMapper.findAllByTitle(title);
    }

}
