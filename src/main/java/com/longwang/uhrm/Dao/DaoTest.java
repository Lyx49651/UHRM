package com.longwang.uhrm.Dao;

import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Entity.Position;
import com.longwang.uhrm.Entity.Post;
import com.longwang.uhrm.Entity.RecruitmentNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DaoTest {
    private EmployeeArchivesDao employeeArchivesDao;
    private UserDao userDao;
    private DepartmentDao departmentDao;
    private PositionDao positionDao;
    private RecruitmentNoticeDao recruitmentNoticeDao;

    @Autowired
    public void setRecruitmentNoticeDao(RecruitmentNoticeDao recruitmentNoticeDao){this.recruitmentNoticeDao = recruitmentNoticeDao;}


    @Autowired
    public void setPositionDao(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao) {
        this.employeeArchivesDao = employeeArchivesDao;
    }

    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @GetMapping("daoTest")
    @ResponseBody
    String test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException {
        //User user = new User("Yxq","test","male","test","test",14,"2","1234",1,"1234");
/*
        User user =  userDao.getUserById(2);

        System.out.println(user.getName());
        System.out.println(employeeArchivesDao.findAllEmployee());
        System.out.println(user.getName());*/

//        departmentDao.getId("人事部");
//        System.out.println(departmentDao.getId("人事部")+ departmentDao.getName(1));
//        return "success";
//
//        EmployeeArchives employeeArchives = new EmployeeArchives();
//        employeeArchives.setEmployeeId(2);
//        employeeArchives.setEmployeeName("f");
//        employeeArchives.setPassword("123");
//        employeeArchivesDao.register(employeeArchives);
//        return "success";
//        List<EmployeeArchives> em = departmentDao.getDepartmentEmployee(1);
//        System.out.println(em);
//        Post post = positionDao.getPost(1);
//
////        String  post = positionDao.getPostName(1);

       // System.out.println(employeeArchivesDao.getEmployeeByName("刘"));
        System.out.println(recruitmentNoticeDao.findAll());


//
//        String date = "2009-07-16T19:20"; // <input type="datetime-local"> 输入参数
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//            Date dt = sdf.parse(date);
//            Timestamp time = new Timestamp(dt.getTime());
//
//        RecruitmentNotice recruitmentNotice = new RecruitmentNotice("人事部","1",time,"1");
//        System.out.println(recruitmentNoticeDao.addRecruitmentNotice(recruitmentNotice));
//
        List<Position> list = positionDao.getPostByDepartment("人事部");
        System.out.println(list.get(1).toString());
        return "success";
    }
}
