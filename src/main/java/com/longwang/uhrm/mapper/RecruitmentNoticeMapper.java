package com.longwang.uhrm.mapper;

import com.longwang.uhrm.Entity.RecruitmentNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecruitmentNoticeMapper {
    @Select("select * from RecruitmentNotice")
    public List<RecruitmentNotice> findAll();

    @Select("select * from RecruitmentNotice where title = #{title}")
    public List<RecruitmentNotice> findAllByTitle(String title);
}
