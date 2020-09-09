package com.longwang.uhrm.Controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Dao.*;
import com.longwang.uhrm.Entity.*;
import com.longwang.uhrm.Proxy.LogInterface;
import com.longwang.uhrm.Tool.Solution;
import com.longwang.uhrm.Tool.ToolMy;

import com.longwang.uhrm.Tool.convertdata;

import com.longwang.uhrm.Tool.getSessionInfo;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.sun.tools.jconsole.JConsoleContext;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class ViewController{
    //注入
    EmployeeArchivesDao employeeArchivesDao;
    UserDao userDao;
    DepartmentDao departmentDao;
    RecruitmentNoticeDao recruitmentNoticeDao;
    PositionDao positionDao;
    CollectTableDao collectTableDao;
    Solution solution;
    AttendanceDao attendanceDao;
    SalaryDao salaryDao;

    @Autowired
    public void setSalaryDao(SalaryDao salaryDao) {
        this.salaryDao = salaryDao;
    }

    @Autowired
    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Autowired
    public void setCollectTableDao(CollectTableDao collectTableDao) {
        this.collectTableDao = collectTableDao;
    }

    @Autowired
    public void setPositionDao(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Autowired
    public void setRecruitmentNoticeDao(RecruitmentNoticeDao recruitmentNoticeDao) {
        this.recruitmentNoticeDao = recruitmentNoticeDao;
    }

    @Autowired
    void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao) {
        this.employeeArchivesDao = employeeArchivesDao;
    }

    @Autowired
    @Qualifier("userDao")
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    //ThymeLeaf视图解析设置
    @Autowired
    public void setAttendanceDao(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
        // speed up execution in most scenarios, but might be incompatible
        // with specific cases when expressions in one template are reused
        // across different data types, so this flag is "false" by default
        // for safer backwards compatibility.
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
    //日志函数
    private void log(String fun){
        LogInterface logInterface = (LogInterface)applicationContext.getBean("logDaoProxy");
        logInterface.time_function(fun);
    }
    //主页面
    @RequestMapping(method = RequestMethod.GET,value = {"/index","/"})
    public String test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        HttpSession httpSession = httpServletRequest.getSession();//获取session
        String type = (String) httpSession.getAttribute("type");
        if (type != null) {
            model.addAttribute("islogged", true);
            boolean typeBoolean;
            if (type.equals("employee")) {
                typeBoolean = true;
            } else {
                typeBoolean = false;
            }
            model.addAttribute("typeBoolean", typeBoolean);
            model.addAttribute("name", httpSession.getAttribute("name"));
        } else {
            model.addAttribute("islogged", false);
        }
        return "home";
    }

    //登出
    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("name", null);
        httpSession.setMaxInactiveInterval(0);//设置session存活时间
        httpSession.invalidate();
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
        }
        return "redirect:index";
    }

    //根据部门名获取编制信息
    @RequestMapping(method = RequestMethod.POST, value = "/get_info_by_departmentName")
    @ResponseBody
    public JSONObject get_info_by_departmentName(@RequestBody HashMap<String, String> map) {
        //执行的为查询操作
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<com.longwang.uhrm.Entity.Position> positions = positionDao.getPostByDepartment(map.get("department"));
        JSONObject jsonObject = new JSONObject();
        JSONArray post_name = new JSONArray();
        JSONArray position_id = new JSONArray();
        JSONArray member_number = new JSONArray();
        JSONArray authorize_strength = new JSONArray();

        for(com.longwang.uhrm.Entity.Position position:positions){
            post_name.add(position.getTypePosition());
            position_id.add(position.getIdPosition());
            member_number.add(positionDao.getStuffNumByPosition_and_Department(map.get("department"), position.getTypePosition()));
            System.out.println(position.getTypePosition());
            authorize_strength.add(positionDao.getRecruitment(position.getTypePosition(), map.get("department")));
        }
        jsonObject.put("post_name", post_name);
        jsonObject.put("position_id", position_id);
        jsonObject.put("member_number", member_number);
        jsonObject.put("authorize_strength", authorize_strength);
        return jsonObject;
    }

    //根据部门名获取编制信息
    @RequestMapping(method = RequestMethod.POST, value = "/get_info_by_departmentName_store")
    @ResponseBody
    public JSONObject get_info_by_departmentName_store(@RequestBody HashMap<String, Object> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<String> id = solution.translate(map.get("id").toString());
        List<String> member = solution.translate(map.get("member").toString());
        List<String> authoried = solution.translate(map.get("authoried").toString());
        List<String> recruitment = solution.translate(map.get("recruitment").toString());
        for(int i=0;i<solution.translate(map.get("id").toString()).size();i++){
            CollectTable temp = new CollectTable();
            temp.setRecutimentNumber(recruitment.get(i));
            temp.setMemberNumber(member.get(i));
            temp.setAuthorizedStrengthNumber(authoried.get(i));
            temp.setIdPost(Integer.parseInt(id.get(i)));
            temp.setStatus("saved");
            temp.setDepartment_idDepartment(positionDao.getPosByID(Integer.parseInt(id.get(i))).getDepartmentId());
            collectTableDao.insertCollectTable(temp);
        }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "pass");
            return jsonObject;
    }
}
