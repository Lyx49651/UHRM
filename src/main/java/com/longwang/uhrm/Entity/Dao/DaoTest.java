package com.longwang.uhrm.Entity.Dao;

import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DaoTest {
    private EmployeeArchivesDao employeeArchivesDao;
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao) {
        this.employeeArchivesDao = employeeArchivesDao;
    }

    @GetMapping("daoTest")
    @ResponseBody
    String test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        User user = new User("Yxq","test","male","test","test",14,"2","1234",1,"1234");

        boolean test =  userDao.register(user);
        System.out.println(test);
        return "success";
//
//        EmployeeArchives employeeArchives = new EmployeeArchives();
//        employeeArchives.setEmployeeId(2);
//        employeeArchives.setEmployeeName("f");
//        employeeArchives.setPassword("123");
//        employeeArchivesDao.register(employeeArchives);
//        return "success";
    }
}
