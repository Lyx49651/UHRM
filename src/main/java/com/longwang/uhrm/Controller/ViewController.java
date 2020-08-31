package com.longwang.uhrm.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Dao.*;
import com.longwang.uhrm.Entity.*;
import com.longwang.uhrm.Tool.ToolMy;

import com.alibaba.fastjson.JSON;
import com.longwang.uhrm.Entity.InformationChange;
import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Entity.Post;
import com.longwang.uhrm.Tool.convertdata;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.sun.tools.jconsole.JConsoleContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;

@Controller
public class ViewController {
    //注入
    EmployeeArchivesDao employeeArchivesDao;
    UserDao userDao;
    DepartmentDao departmentDao;
    RecruitmentNoticeDao recruitmentNoticeDao;
    PositionDao positionDao;

    @Autowired
    public void setPositionDao(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Autowired
    public void setRecruitmentNoticeDao(RecruitmentNoticeDao recruitmentNoticeDao) {
        this.recruitmentNoticeDao = recruitmentNoticeDao;
    }

    @Autowired
    void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao){
        this.employeeArchivesDao = employeeArchivesDao;
    }
    @Autowired
    @Qualifier("userDao")
    void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
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
    public SpringTemplateEngine templateEngine(){
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
    //主页面
    @RequestMapping(method = RequestMethod.GET,value = "/index")
    public String test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        return "home";
    }
    //同样主页面
    @RequestMapping(method = RequestMethod.GET,value = "/")
    public String start(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return "home";
    }
    //登出
    @RequestMapping(method = RequestMethod.GET,value = "/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("name",null );
        httpSession.setMaxInactiveInterval(0);//设置session存活时间
        httpSession.invalidate();
        Cookie[] cookies = httpServletRequest.getCookies();
        for(Cookie cookie:cookies){
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
        }
        return "home";
    }
//    用户登录
    @RequestMapping(method = RequestMethod.GET,value = "/user_login")
    public String user_login(Model model){
        return "candidates_login";
    }
//    员工登录
    @RequestMapping(method = RequestMethod.GET,value = "/personnel_login")
    public String personnel_login(Model model){

        return "employee_login";
    }
    //用户登录信息确认
    @RequestMapping(method = RequestMethod.POST,value = "/user_login_check")
        //@ResponseBody
        public String user_login_check(@RequestParam("phone") String phone, @RequestParam("password") String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println(phone + password);
        Boolean res =  employeeArchivesDao.authenticate(1, password);
        return "redirect:index";
    }
    //员工登录信息确认
    @RequestMapping(method = RequestMethod.POST,value = "/employee_login_check")
    @ResponseBody
    public JSONObject employee_login_check(@RequestBody HashMap<String, String> map , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Boolean res =  employeeArchivesDao.authenticate(Integer.parseInt(map.get("id")), map.get("password"));
        if(res){
            HttpSession httpSession = httpServletRequest.getSession();//获取session
            String name = employeeArchivesDao.getName(Integer.parseInt(map.get("id")));
            httpSession.setAttribute("id",map.get("id"));
            httpSession.setAttribute("name",name);
            httpSession.setMaxInactiveInterval(2*60);//设置session存活时间
            Cookie cookie = new Cookie("name",name);//新建cookie供客户端使用
            cookie.setMaxAge(2*60);// 设置存在时间为30分钟
            cookie.setPath("/");//设置作用域
            httpServletResponse.addCookie(cookie);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result","pass");
            return jsonObject;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result","undefined");
            return jsonObject;
        }
        //请注意，因为session和cookie会给测试工作带来很大的复杂性，因此在整个开发过程，没有直接用到的，均暂时注解掉
//        return "redirect:index";
    }
    //非员工登录信息确认
    @RequestMapping(method = RequestMethod.POST,value = "/personnel_login_check")
    @ResponseBody
    public JSONObject personnel_login_check(@RequestBody HashMap<String, String> map , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Boolean res =  userDao.login(map.get("phone"), map.get("password"));
        if(res){
            HttpSession httpSession = httpServletRequest.getSession();//获取session
            String name = userDao.getName(map.get("phone"));
            System.out.println(name);
            httpSession.setAttribute("name",name);
            httpSession.setMaxInactiveInterval(2*60);//设置session存活时间
            Cookie cookie = new Cookie("name",name);//新建cookie供客户端使用
            cookie.setMaxAge(2*60);// 设置存在时间为30分钟
            cookie.setPath("/");//设置作用域
            httpServletResponse.addCookie(cookie);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("result","pass");
            return jsonObject1;
        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("result","undefined");
            return jsonObject1;
        }
        //请注意，因为session和cookie会给测试工作带来很大的复杂性，因此在整个开发过程，没有直接用到的，均暂时注解掉

//        return "redirect:index";

    }
    // 跳转到人员管理系统
    @RequestMapping(method = RequestMethod.GET,value = "/employee_management_system")
    public String employee_management_system(){
        return "employee_management_system_functionlist";
    }
    // 跳转到人员信息录入
    @RequestMapping(method = RequestMethod.GET,value = "/employee_import")
    public String employee_import(Model model){
        model.addAttribute("list",departmentDao.getAll());
        return "employee_import";
    }
    //信息导入
    @RequestMapping(method = RequestMethod.POST,value = "/employee_info_import")
    @ResponseBody
    public JSONObject employee_info_import(@RequestBody com.longwang.uhrm.Entity.EmployeeArchives employeeArchives) {
        System.out.println(employeeArchives.getEmployeeName());
        System.out.println(employeeArchives.getSalaryParametersIdSalaryParameters());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        return jsonObject;
    }
    //员工信息分析页面跳转
    @RequestMapping(method = RequestMethod.GET,value = "/employee_management/info_analysis")
    public String info_analysis(Model model) {
        model.addAttribute("DepartmentList",departmentDao.getAll());
        return "EmployeeInfo_analysis";
    }
    //向前端发送部门详细数据
    @RequestMapping(method = RequestMethod.POST,value = "/get_info_employee")
    @ResponseBody
    public JSONObject employee_info_import(@RequestBody HashMap<String, String> map) {
        ToolMy demo = new ToolMy();
        return demo.analysis_json(departmentDao.getDepartmentEmployeeByName(map.get("name")));
    }
    //跳转到查询页面
    @RequestMapping(method = RequestMethod.GET,value = "/employee_search")
    public String employee_search(Model model){
        List<com.longwang.uhrm.Entity.EmployeeArchives> list = employeeArchivesDao.findAllEmployee();
        model.addAttribute("list",list);
        return "employee_search";
    }
    //跳转到招聘系统的功能页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_system")
    public String recruitment_system(Model model){
        List<com.longwang.uhrm.Entity.RecruitmentNotice> test = recruitmentNoticeDao.findAll();
        for(com.longwang.uhrm.Entity.RecruitmentNotice recruitmentNotice:test){
            recruitmentNotice.setStringTime(recruitmentNotice.getTime().toString());
        }
        List<CollectTable> test1 = new ArrayList<>();
        CollectTable a = new CollectTable();
        a.setId(1);
        a.setMemberNumber("5");
        a.setAuthorizedStrengthNumber("10");
        a.setRecutimentNumber("4");
        a.setIdPost(1);
        a.setDepartmentIdDepartment(1);
        a.setNamePost("二级人事助理");
        a.setDepartmentName("人事部");
        CollectTable b = new CollectTable();
        b.setId(2);
        b.setMemberNumber("25");
        b.setAuthorizedStrengthNumber("40");
        b.setRecutimentNumber("14");
        b.setIdPost(2);
        b.setDepartmentIdDepartment(2);
        b.setNamePost("科研组长");
        b.setDepartmentName("科研部");
        test1.add(a);
        test1.add(b);
        model.addAttribute("plan",test1);
        model.addAttribute("list",test);

        List<User> users = new ArrayList<>();
        User h = new User();
        h.setIdUser(1);
        h.setName("边小博");
        h.setIdCard("61012219990331xxxx");
        h.setAddress("陕西榆林");
        h.setAge(21);
        h.setSex("男");
        h.setMailAddress("123@qq.com");
        h.setTelephone("1531581010");
        users.add(h);
        model.addAttribute("list1",users);
        return "Recruitment_system_functions";
    }
    //接收前端的招聘通知，存入数据库
    @RequestMapping(method = RequestMethod.POST,value = "/recruitment_notice")
    @ResponseBody
    public JSONObject recruitment_notice(@RequestBody HashMap<String, String> map) {
        System.out.println(map.get("title") + map.get("content"));

        com.longwang.uhrm.Entity.RecruitmentNotice recruitmentNotice = new com.longwang.uhrm.Entity.RecruitmentNotice();
        recruitmentNotice.setTitle(map.get("title"));
        recruitmentNotice.setContent(map.get("content"));
        Timestamp time = new Timestamp(System.currentTimeMillis());
        recruitmentNotice.setTime(time);
        recruitmentNoticeDao.addRecruitmentNotice(recruitmentNotice);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        return jsonObject;
    }

    //高级查询，按id查询，或者按姓名模糊查询
    @RequestMapping(method = RequestMethod.GET,value = "/employee_id_search")
    public String employee_search_id(HttpServletRequest request, Model model){
        try{
            long id = Long.parseLong( request.getParameter("idOrName"));
            com.longwang.uhrm.Entity.EmployeeArchives emp = employeeArchivesDao.getEmployeeById(id);
            model.addAttribute("list",emp);
        }catch(Exception e){
            String name = request.getParameter("idOrName");
            List<com.longwang.uhrm.Entity.EmployeeArchives> list = employeeArchivesDao.getEmployeeByName(name);
            model.addAttribute("list",list);
        }
        return "employee_search";
    }


    //查看个人信息
    @RequestMapping(method = RequestMethod.GET,value = "/personalInfo")
    public String personal_info(HttpServletRequest httpServletRequest,Model model) {
        long employeeId = Long.parseLong((String) httpServletRequest.getSession().getAttribute("id"));
        model.addAttribute("Employee", employeeArchivesDao.getEmployeeById(employeeId));
        return "personal_info";
    }

    //跳转到上报招聘计划页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_plan_make")
    public String recruitment_plan_make(Model model){
        model.addAttribute("DepartmentList",departmentDao.getAll());
        return "recruitment_plan_make";
    }
    //根据部门名获取编制信息
    @RequestMapping(method = RequestMethod.POST,value = "/get_info_by_departmentName")
    @ResponseBody
    public JSONObject get_info_by_departmentName(@RequestBody HashMap<String, String> map) {
        //执行的为查询操作
        System.out.println(map.get("department"));
        List<com.longwang.uhrm.Entity.Position> positions = positionDao.getPostByDepartment(map.get("department"));
//            System.out.println(positions);
        JSONObject jsonObject = new JSONObject();
        JSONArray post_name = new JSONArray();
        JSONArray position_id = new JSONArray();
        JSONArray member_number = new JSONArray();
        JSONArray authorize_strength = new JSONArray();
//            post_name.add("二级人事助理");
//            post_name.add("部门主管");
//            position_id.add(1);
//            position_id.add(2);
//            member_number.add(3);
//            member_number.add(1);
//            authorize_strength.add(10);
//            authorize_strength.add(1);
//        for (Position position1:positions){
//            System.out.println(position1.getTypePostion());
//        }
        for(com.longwang.uhrm.Entity.Position position:positions){
            post_name.add(position.getTypePosition());
            position_id.add(position.getIdPosition());
            member_number.add(positionDao.getStuffNumByPosition_and_Department(map.get("department"),position.getTypePosition()));
            System.out.println(position.getTypePosition());
            authorize_strength.add(positionDao.getRecruitment(position.getTypePosition(),map.get("department")));
        }
        jsonObject.put("post_name",post_name);
        jsonObject.put("position_id",position_id);
        jsonObject.put("member_number",member_number);
        jsonObject.put("authorize_strength",authorize_strength);
        return jsonObject;

    }
    //根据部门名获取编制信息
    @RequestMapping(method = RequestMethod.POST,value = "/get_info_by_departmentName_store")
    @ResponseBody
    public JSONObject get_info_by_departmentName_store(@RequestBody HashMap<String, Object> map) {
            System.out.println(map.get("id"));
            System.out.println(map.get("member"));
            System.out.println(map.get("authoried"));
            System.out.println(map.get("recruitment"));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "pass");
            return jsonObject;
    }
    //审核招聘计划
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_plan_check")
    public String recruitment_plan_check(Model model) {
        List<com.longwang.uhrm.Entity.CollectTable> test = new ArrayList<>();
        com.longwang.uhrm.Entity.CollectTable a = new com.longwang.uhrm.Entity.CollectTable();
        a.setId(1);
        a.setMemberNumber("5");
        a.setAuthorizedStrengthNumber("10");
        a.setRecutimentNumber("4");
        a.setIdPost(1);
        a.setDepartmentIdDepartment(1);
        a.setNamePost("二级人事助理");
        a.setDepartmentName("人事部");
        com.longwang.uhrm.Entity.CollectTable b = new com.longwang.uhrm.Entity.CollectTable();
        b.setId(2);
        b.setMemberNumber("25");
        b.setAuthorizedStrengthNumber("40");
        b.setRecutimentNumber("14");
        b.setIdPost(2);
        b.setDepartmentIdDepartment(2);
        b.setNamePost("科研组长");
        b.setDepartmentName("科研部");
        test.add(a);
        test.add(b);
        model.addAttribute("plan",test);
        return "recruitment_plan_check";
    }
    //招聘计划审核结果
    @RequestMapping(method = RequestMethod.POST,value = "/recruitment_plan_check_result")
    @ResponseBody
    public JSONObject recruitment_plan_check_result(@RequestBody HashMap<String, Object> map) {
        System.out.println(map.get("member"));
        System.out.println(map.get("max"));
        System.out.println(map.get("form_member"));
        System.out.println(map.get("recruitment"));
        System.out.println(map.get("state"));
        System.out.println(map.get("depart"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }
    //跳转到信息修改页面
    @RequestMapping(method = RequestMethod.GET,value = "/employee_info_change")
    public String employee_info_change(HttpServletRequest httpServletRequest,Model model){
        model.addAttribute("employee",employeeArchivesDao.getEmployeeById(Integer.parseInt(httpServletRequest.getParameter("id"))));
//        model.addAttribute("employee",employeeArchivesDao.getEmployeeById(1));
        return "information_change";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/info_change")
    @ResponseBody
    public JSONObject info_change(@RequestBody HashMap<String, String> map){
        int originalLen = Integer.parseInt(map.get("originallen"));
        int nowLen = Integer.parseInt(map.get("nowlen"));
        int len1 = Integer.parseInt(map.get("len1"));
        convertdata convertdata = new convertdata();
        String[] changeInfoOriginal = new String[originalLen];
        String[] changeInfoNow = new String[nowLen];
        String[] selectedInfo = new String[len1];
        for(int i=0;i<changeInfoOriginal.length;i++){
            changeInfoOriginal[i] = map.get("changeinfooriginal["+i+"]");
        }
        for(int i=0;i<changeInfoNow.length;i++){
            changeInfoNow[i] = map.get("changeinfonow["+i+"]");
        }
        for(int i=0;i<selectedInfo.length;i++) {
            selectedInfo[i] = map.get("selectInfo[" + i + "]");
        }
        JSONObject jsonObject = new JSONObject();
        convertdata.setEmployeeAddress(map.get("employeeAddress"));
        convertdata.setEmployeeBirthday(map.get("employeeBirthday"));
        convertdata.setEmployeeId(Integer.parseInt(map.get("employeeId")));
        convertdata.setEmployeeName(map.get("employeeName"));
        convertdata.setEmployeeSex(map.get("employeeSex"));
        convertdata.setEmployeePhone(map.get("employeePhone"));

        Boolean res = false;
        Boolean res1 = false;


        Boolean res2 = employeeArchivesDao.updateEmployeeBaseInfo(convertdata);

        for(int i=0;i<len1;i++){
            convertdata.setChangeInfoOriginal(changeInfoOriginal[i]);
            convertdata.setChangeInfoNow(changeInfoNow[i]);
            convertdata.setChangeType(selectedInfo[i]);
            res = employeeArchivesDao.informationChange(convertdata);
            if(res) continue;
            else break;
        }


        for(int i=0;i<len1;i++){
            convertdata.setChangeType(selectedInfo[i]);
            convertdata.setChangeInfoNow(changeInfoNow[i]);
            res1 = employeeArchivesDao.updateEmployeeSpecialInfo(convertdata);
            if(res) continue;
            else break;
        }

        if(res && res1 && res2) jsonObject.put("result","success");
        else jsonObject.put("result","default");
        return jsonObject;
    }

    //获取需修改信息的原信息
    @RequestMapping(method = RequestMethod.POST, value = "/get_info")
    @ResponseBody
    public JSONObject get_info(@RequestBody HashMap<String,String> hashMap){
        JSONObject jsonObject = new JSONObject();
        String res="";
        com.longwang.uhrm.Entity.EmployeeArchives employeeArchives = employeeArchivesDao.getEmployeeById(Integer.parseInt(hashMap.get("employeeId")));
        switch (hashMap.get("changeType")){
            case "employeeDepartment": res = employeeArchives.getEmployeeDepartment();break;
            case "employeePost": res = employeeArchives.getEmployeePost();break;
            case "employeeTitle": res = employeeArchives.getEmployeeTitle();break;
            case "employeeTechnicalGrade": res = employeeArchives.getEmployeeTechnicalGrade();break;
            case "employeeIdentity": res = employeeArchives.getEmployeeIdentity();break;
            case "employeeEducation": res = employeeArchives.getEmployeeEducation();break;
        }
        jsonObject.put("res",res);
        jsonObject.put("result","success");
        return jsonObject;
    }
    //跳转到信息修改页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_namelist_check")
    public String recruitment_namelist_check(Model model){
        List<User> users = new ArrayList<>();
        User a = new User();
        a.setIdUser(1);
        a.setName("边小博");
        a.setIdCard("61012219990331xxxx");
        a.setAddress("陕西榆林");
        a.setAge(21);
        a.setMailAddress("123@qq.com");
        a.setTelephone("1531581010");
        users.add(a);
        model.addAttribute("list",users);
        return "recruitment_name_list_check";
    }
    //接收审核后的报名应聘者
    @RequestMapping(method = RequestMethod.POST, value = "/modify_users")
    @ResponseBody
    public JSONObject modify_users(@RequestBody HashMap<String,Object> map){
        System.out.println(map.get("out_list"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","pass");
        return jsonObject;
    }
    //用户注册
    @RequestMapping(method = RequestMethod.POST,value = "/userRegister")
    @ResponseBody
    public JSONObject user_register(@RequestBody User user){
        boolean flag=userDao.register(user);
        JSONObject jsonObject=new JSONObject();
        if(flag){
            jsonObject.put("result","success");
        }
        return jsonObject;
    }


    //用户注册
    @RequestMapping(method = RequestMethod.POST,value = "/retrieve_password")
    @ResponseBody
    public JSONObject retrieve_password(@RequestBody HashMap<String,String> hashMap){
        String phoneNumber = hashMap.get("employeePhoneNumber");
        String employeeName = hashMap.get("employeeName");
        String res = userDao.retrieve_password(phoneNumber,employeeName);
        JSONObject jsonObject=new JSONObject();
        if(res!=null){
            jsonObject.put("password",res);
            jsonObject.put("result","success");
        }else{
            jsonObject.put("result","default");
        }
        return jsonObject;
    }
}
