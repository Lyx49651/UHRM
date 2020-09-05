package com.longwang.uhrm.Controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Dao.*;
import com.longwang.uhrm.Entity.*;
import com.longwang.uhrm.Tool.Solution;
import com.longwang.uhrm.Tool.ToolMy;

import com.alibaba.fastjson.JSON;
import com.longwang.uhrm.Entity.InformationChange;
import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Entity.Post;
import com.longwang.uhrm.Tool.convertdata;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.sun.tools.jconsole.JConsoleContext;

import org.apache.ibatis.annotations.Param;
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
import java.text.SimpleDateFormat;
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
    CollectTableDao collectTableDao;
    Solution solution;
    AttendanceDao attendanceDao;


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

    @Autowired
    public void setAttendanceDao(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }

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
    @RequestMapping(method = RequestMethod.GET,value = {"/index","/"})
    public String test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Model model){
        HttpSession httpSession = httpServletRequest.getSession();//获取session
        String type = (String) httpSession.getAttribute("type");
        if(type != null){
            model.addAttribute("islogged",true);
            boolean typeBoolean;
            if(type.equals("employee")){
                typeBoolean = true;
            }else{
                typeBoolean = false;
            }
            model.addAttribute("typeBoolean",typeBoolean);
            model.addAttribute("name",httpSession.getAttribute("name"));
        }else{
            model.addAttribute("islogged",false);
        }
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
        return "redirect:index";
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
    public JSONObject employee_login_check(@RequestBody HashMap<String, String> map , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Model model){
        Boolean res =  employeeArchivesDao.authenticate(Integer.parseInt(map.get("id")), map.get("password"));
        if(res){
            HttpSession httpSession = httpServletRequest.getSession();//获取session
            String name = employeeArchivesDao.getName(Integer.parseInt(map.get("id")));
            httpSession.setAttribute("id",map.get("id"));
            httpSession.setAttribute("name",name);
            httpSession.setAttribute("type","employee");
            httpSession.setMaxInactiveInterval(2*60);//设置session存活时间
            Cookie cookie = new Cookie("name",name);//新建cookie供客户端使用
            cookie.setMaxAge(2*60);// 设置存在时间为30分钟
            cookie.setPath("/");//设置作用域
            httpServletResponse.addCookie(cookie);
            boolean typeBoolean = true;
            model.addAttribute("typeBoolean", typeBoolean);
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
    public JSONObject personnel_login_check(@RequestBody HashMap<String, String> map , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Model model){
        Boolean res =  userDao.login(map.get("phone"), map.get("password"));
        if(res){
            HttpSession httpSession = httpServletRequest.getSession();//获取session
            String name = userDao.getName(map.get("phone"));
            System.out.println(name);
            httpSession.setAttribute("name",name);
            httpSession.setAttribute("type","user");
            httpSession.setMaxInactiveInterval(5*60);//设置session存活时间
            Cookie cookie = new Cookie("name",name);//新建cookie供客户端使用
            cookie.setMaxAge(5*60);// 设置存在时间为30分钟
            cookie.setPath("/");//设置作用域
            httpServletResponse.addCookie(cookie);
            model.addAttribute("typeBoolean", Boolean.FALSE);
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
        List<CollectTable> test1 =  collectTableDao.findAllPassed();
        for(CollectTable collectTable: test1){
            //System.out.println(collectTable.getDepartmentIdDepartment());
            collectTable.setDepartmentName(departmentDao.getName(collectTable.getDepartment_idDepartment()));
            collectTable.setNamePost(positionDao.getPostName(collectTable.getIdPost()));
        }
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
        JSONObject jsonObject = new JSONObject();
        JSONArray post_name = new JSONArray();
        JSONArray position_id = new JSONArray();
        JSONArray member_number = new JSONArray();
        JSONArray authorize_strength = new JSONArray();
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
    //审核招聘计划
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_plan_check")
    public String recruitment_plan_check(Model model) {
        List<CollectTable> test = collectTableDao.findAllSaved();
        for(int i=0;i<test.size();i++){
            test.get(i).setDepartmentName(departmentDao.getDepartmentById(test.get(i).getDepartment_idDepartment()).getNameDepartment());
            test.get(i).setNamePost(positionDao.getPost(test.get(i).getIdPost()).getPostName());
        }
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
        List<String> member = solution.translate(map.get("member").toString());//现有人数
        List<String> max = solution.translate(map.get("max").toString());//编制人数
        List<String> form = solution.translate(map.get("form_member").toString());//原计划招聘人数
        List<String> rec = solution.translate(map.get("recruitment").toString());//审核后人数
        List<String> state = solution.translate(map.get("state").toString());//审核后状态
        List<String> depart = solution.translate(map.get("depart").toString());//部门和岗位名
        for(int i=0;i<member.size();i++){
            if(state.get(i).contains("#")){
                System.out.println(state.get(i).split("#")[0]);
            }else {
                System.out.println("通过");
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }
    //跳转到信息修改页面
    @RequestMapping(method = RequestMethod.GET,value = "/employee_info_change")
    public String employee_info_change(HttpServletRequest httpServletRequest,Model model){
        model.addAttribute("employee",employeeArchivesDao.getEmployeeById(Integer.parseInt(httpServletRequest.getParameter("id"))));
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
    //跳转到信息审核页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_namelist_check")
    public String recruitment_namelist_check(Model model){
        List<User> users = userDao.getUserByCandiate();
        model.addAttribute("list",users);
        return "recruitment_name_list_check";
    }
    //接收审核后的报名应聘者
    @RequestMapping(method = RequestMethod.POST, value = "/modify_users")
    @ResponseBody
    public JSONObject modify_users(@RequestBody HashMap<String,Object> map){
        System.out.println(map.get("out_list"));
        List<String> out = solution.translate(map.get("out_list").toString());
        for(int i=0;i<out.size();i++){
            if(out.get(i).contains("#")){
                userDao.update_unpassed(Integer.parseInt(out.get(i).split("#")[0]));
            }else {
                userDao.update_passed(Integer.parseInt(out.get(i)));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","pass");
        return jsonObject;
    }
    //跳转面试与笔试成绩审核页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_info_save")
    public String recruitment_info_save(Model model){
        List<CandidateInfo> candidateInfos = new ArrayList<>();
        CandidateInfo a = new CandidateInfo();
        a.setIdCandidateInfo(1);
        a.setInterviewResult("98");
        a.setWrittenResult("90");
        candidateInfos.add(a);
        model.addAttribute("list", a);
        return "select_recruitment_grade";
    }
    //接收经过笔试和面试成绩审核的信息
    @RequestMapping(method = RequestMethod.POST, value = "/modify_users_selected")
    @ResponseBody
    public JSONObject modify_users_selected(@RequestBody HashMap<String,Object> map){
        System.out.println(map.get("out_list"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","pass");
        return jsonObject;
    }
    //跳转到招聘人归档页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_to_employee")
    public String recruitment_to_employee(Model model){
        List<User> users = new ArrayList<>();
        User a = new User();
        a.setIdUser(1);
        a.setName("边小博");
        a.setIdCard("61012219990331xxxx");
        a.setAddress("陕西榆林");
        a.setAge(21);
        a.setMailAddress("123@qq.com");
        a.setTelephone("1531581010");
        a.setEducation("本科");
        a.setSex("男");
        users.add(a);
        model.addAttribute("list", users);
        return "Archive";
    }
    //归档
    @RequestMapping(method = RequestMethod.POST, value = "/archive_data")
    @ResponseBody
    public JSONObject archive_data(@RequestBody HashMap<String,Object> map) {
        System.out.println(map.get("title"));
        System.out.println(map.get("name").toString().split("]")[0]);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
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


    //跳转到找回密码
    @RequestMapping(method = RequestMethod.GET,value = "/to_retrieve_password")
    public String to_retrieve_password(){
        return "Retrieve_password";
    }


    //用户找回密码
    @RequestMapping(method = RequestMethod.POST,value = "/retrieve_password")
    @ResponseBody
    public JSONObject retrieve_password(@RequestBody HashMap<String,String> hashMap){
        convertdata convertdata = new convertdata();
        convertdata.setEmployeePhone(hashMap.get("employeePhoneNumber"));
        convertdata.setEmployeeId(Integer.parseInt(hashMap.get("employeeId")));
        String res = userDao.retrieve_password(convertdata);
        System.out.println(res);
        JSONObject jsonObject=new JSONObject();
        if(res!=null){
            jsonObject.put("password",res);
            jsonObject.put("result","success");
        }else{
            jsonObject.put("result","default");
        }
        return jsonObject;
    }

    //跳转到合同查询
    @RequestMapping(method = RequestMethod.GET,value = "/to_contract_query")
    public String to_contract_query(Model model){
        List<Contract> list = employeeArchivesDao.findAllContract();
        model.addAttribute("list",list);
        return "contract_search";
    }

    //查询指定的合同
    @RequestMapping(method = RequestMethod.GET,value = "/contract_info_search")
    public String contract_info_search(HttpServletRequest request, Model model){
        try{
            int id = Integer.parseInt( request.getParameter("idOrName"));
            Contract emp = employeeArchivesDao.getContractById(id);
            model.addAttribute("list",emp);
        }catch(Exception e){
            String name = request.getParameter("idOrName");
            List<Contract> list = employeeArchivesDao.getContractByName(name);
            model.addAttribute("list",list);
        }
        return "contract_search";
    }

    //to合同修改
    @RequestMapping(method = RequestMethod.GET,value = "/contract_info_change")
    public String contract_info_change(HttpServletRequest httpServletRequest,Model model){
        int ContractId = Integer.parseInt(httpServletRequest.getParameter("id"));
        System.out.println(ContractId);
        Contract contract = employeeArchivesDao.getContractById(ContractId);
        model.addAttribute("list",contract);
        return "contract_update";
    }

    //合同修改提交
    @RequestMapping(method = RequestMethod.POST,value = "/contract_change")
    @ResponseBody
    public JSONObject contract_change(@RequestBody HashMap<String,String> hashMap){
        Contract contract = new Contract();
        JSONObject jsonObject = new JSONObject();
        contract.setIdContract(Integer.parseInt(hashMap.get("contractId")));
        contract.setEmployeeId(Integer.parseInt(hashMap.get("employeeId")));
        contract.setContractPeriod(hashMap.get("contractPeriod"));
        contract.setEmployeeName(hashMap.get("employeeName"));
        contract.setEmployeeSex(hashMap.get("employeeSex"));
        contract.setSalary(hashMap.get("salary"));
        contract.setPosition(hashMap.get("position"));

        boolean res = employeeArchivesDao.contract_change(contract);

        if(res){
            jsonObject.put("result","success");
        }else{
            jsonObject.put("result","default");
        }

        return jsonObject;
    }

    //删除合同
    @RequestMapping(method = RequestMethod.GET,value = "/contract_delete")
    public String contract_delete(HttpServletRequest httpServletRequest,Model model){
        int idContract = Integer.parseInt(httpServletRequest.getParameter("id"));
        employeeArchivesDao.delete_contract(idContract);

        List<Contract> list = employeeArchivesDao.findAllContract();
        model.addAttribute("list",list);


        return "contract_search";
    }

    //to新增合同
    @RequestMapping(method = RequestMethod.GET,value = "/to_add_contract")
    public String to_add_contract(){
        return "contract_import";
    }


    @RequestMapping(method = RequestMethod.POST,value = "/add_contract")
    @ResponseBody
    public JSONObject add_contract(@RequestBody HashMap<String,String> hashMap){
        Contract contract = new Contract();
        JSONObject jsonObject = new JSONObject();
        contract.setEmployeeId(Integer.parseInt(hashMap.get("employeeId")));
        contract.setEmployeeName(hashMap.get("employeeName"));
        contract.setEmployeeSex(hashMap.get("employeeSex"));
        contract.setContractPeriod(hashMap.get("contractPeriod"));
        contract.setSalary(hashMap.get("salary"));
        contract.setPosition(hashMap.get("position"));

        boolean res = employeeArchivesDao.add_contract(contract);
        if(res){
            jsonObject.put("result","success");
        }else{
            jsonObject.put("result","default");
        }

        return jsonObject;
    }



    //跳转到考勤管理系统
    @RequestMapping(method = RequestMethod.GET, value = "/Attendance")
    public String toAttendance(Model m){
        return "Attendance";
    }

    //跳转到我的考勤信息查看
    @RequestMapping(method = RequestMethod.GET, value = "/myAttendance")
    public String toMyAttendance(Model m){
        return "MyAttendance";
    }
    //查找我的考勤信息
    @RequestMapping(method = RequestMethod.GET, value = "/searchMyAttendance")
    public String searchMyAttendance(Model m,HttpServletRequest request){
        String date = request.getParameter("AttendanceDate");
        List<Attendance> attendances = attendanceDao.getEmployeeByTime(date,2);
        m.addAttribute("list",attendances);
        return "MyAttendance";
    }

    String department_for_Attendance;


    @RequestMapping(method = RequestMethod.GET, value = "/departmentChange")
    public String departmentChange(Model m,HttpServletRequest request){
        department_for_Attendance = request.getParameter("inlineRadioOptions");
        List<EmployeeArchives> employeeArchivesList = departmentDao.getDepartmentEmployeeByName(department_for_Attendance);
        List<Attendance> attendances = new ArrayList<>();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String today = ft.format(dNow);
        if(attendanceDao.checkAttendance(today,(int)employeeArchivesList.get(0).getEmployeeId()) == null ){
            for(int i = 0; i < employeeArchivesList.size(); i++) {
                Attendance attendance = new Attendance(today,employeeArchivesList.get(i).getEmployeeId(),employeeArchivesList.get(i).getEmployeeName(),"intime");
                attendances.add(attendance);
            }
            m.addAttribute("list",attendances);
            m.addAttribute("department",department_for_Attendance);
        }
        else{

        }
        return "Attendance";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkIn")
    @ResponseBody
    public JSONObject checkIN(Model m, HttpServletRequest request, @RequestBody List<String> list){
        JSONObject jsonObject = new JSONObject();
        List<EmployeeArchives> employeeArchivesList = departmentDao.getDepartmentEmployeeByName(department_for_Attendance);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String today = ft.format(dNow);
        System.out.println(department_for_Attendance);
        if(attendanceDao.checkAttendance(today,(int)employeeArchivesList.get(0).getEmployeeId()) == null) {
            for(int i = 0; i < employeeArchivesList.size(); i++) {
                Attendance attendance = new Attendance(today,employeeArchivesList.get(i).getEmployeeId(),employeeArchivesList.get(i).getEmployeeName(),list.get(i));
                attendanceDao.putListE(attendance);
            }
            jsonObject.put("result","success");
        }else{
            jsonObject.put("result","error");
        }
        return jsonObject;
    }
}
