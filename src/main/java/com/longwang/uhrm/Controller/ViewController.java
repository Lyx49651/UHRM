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

    //    用户登录
    @RequestMapping(method = RequestMethod.GET, value = "/user_login")
    public String user_login(Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        return "candidates_login";
    }

    //    员工登录
    @RequestMapping(method = RequestMethod.GET, value = "/personnel_login")
    public String personnel_login(Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        return "employee_login";
    }

    //用户登录信息确认
    @RequestMapping(method = RequestMethod.POST, value = "/user_login_check")
    //@ResponseBody
    public String user_login_check(@RequestParam("phone") String phone, @RequestParam("password") String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        System.out.println(phone + password);
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        Boolean res = employeeArchivesDao.authenticate(1, password);
        return "redirect:index";
    }

    //员工登录信息确认
    @RequestMapping(method = RequestMethod.POST, value = "/employee_login_check")
    @ResponseBody
    public JSONObject employee_login_check(@RequestBody HashMap<String, String> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        Boolean res = employeeArchivesDao.authenticate(Integer.parseInt(map.get("id")), map.get("password"));
        if (res) {
            HttpSession httpSession = httpServletRequest.getSession();//获取session
            String name = employeeArchivesDao.getName(Integer.parseInt(map.get("id")));
            httpSession.setAttribute("id", map.get("id"));
            httpSession.setAttribute("name", name);
            httpSession.setAttribute("type", "employee");
            httpSession.setMaxInactiveInterval(2 * 60);//设置session存活时间
            Cookie cookie = new Cookie("name", name);//新建cookie供客户端使用
            cookie.setMaxAge(2 * 60);// 设置存在时间为30分钟
            cookie.setPath("/");//设置作用域
            httpServletResponse.addCookie(cookie);
            boolean typeBoolean = true;
            model.addAttribute("typeBoolean", typeBoolean);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "pass");
            return jsonObject;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "undefined");
            return jsonObject;
        }
        //请注意，因为session和cookie会给测试工作带来很大的复杂性，因此在整个开发过程，没有直接用到的，均暂时注解掉
//        return "redirect:index";
    }

    //非员工登录信息确认
    @RequestMapping(method = RequestMethod.POST, value = "/personnel_login_check")
    @ResponseBody
    public JSONObject personnel_login_check(@RequestBody HashMap<String, String> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        Boolean res = userDao.login(map.get("phone"), map.get("password"));
        if (res) {
            HttpSession httpSession = httpServletRequest.getSession();//获取session
            String name = userDao.getName(map.get("phone"));
            httpSession.setAttribute("phone", map.get("phone"));
            httpSession.setAttribute("name", name);
            httpSession.setAttribute("type", "user");
            httpSession.setMaxInactiveInterval(5 * 60);//设置session存活时间
            Cookie cookie = new Cookie("name", name);//新建cookie供客户端使用
            cookie.setMaxAge(5 * 60);// 设置存在时间为30分钟
            cookie.setPath("/");//设置作用域68563656
            httpServletResponse.addCookie(cookie);
            model.addAttribute("typeBoolean", Boolean.FALSE);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("result", "pass");
            return jsonObject1;
        } else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("result", "undefined");
            return jsonObject1;
        }
    }

    // 跳转到人员管理系统
    @RequestMapping(method = RequestMethod.GET, value = "/employee_management_system")
    public String employee_management_system(HttpServletRequest httpServletRequest,Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        boolean res = getSessionInfo.getsessionInfo(httpServletRequest,model);
        if(!res) return "index";
        return "employee_management_system_functionlist";
    }

    // 跳转到人员信息录入
    @RequestMapping(method = RequestMethod.GET, value = "/employee_import")
    public String employee_import(HttpServletRequest httpServletRequest,Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        boolean res = getSessionInfo.getsessionInfo(httpServletRequest,model);
        if(!res) return "index";
        model.addAttribute("list", departmentDao.getAll());
        return "employee_import";
    }

    //信息导入
    @RequestMapping(method = RequestMethod.POST, value = "/employee_info_import")
    @ResponseBody
    public JSONObject employee_info_import(@RequestBody EmployeeArchives employeeArchives) {
        int id = employeeArchivesDao.max_id();
        employeeArchives.setEmployeeId(id+1);
        boolean res1 = employeeArchivesDao.Archive(employeeArchives);
        boolean res = salaryDao.createNewSalaryParameters(id+1);
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        JSONObject jsonObject = new JSONObject();
        if(res && res1){
            jsonObject.put("result", "success");
        }else{
            jsonObject.put("result", "default");
        }
        return jsonObject;
    }

    //员工信息分析页面跳转
    @RequestMapping(method = RequestMethod.GET, value = "/employee_management/info_analysis")
    public String info_analysis(HttpServletRequest httpServletRequest,Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        boolean res = getSessionInfo.getsessionInfo(httpServletRequest,model);
        if(!res) return "index";
        model.addAttribute("DepartmentList", departmentDao.getAll());
        return "EmployeeInfo_analysis";
    }

    //向前端发送部门详细数据
    @RequestMapping(method = RequestMethod.POST, value = "/get_info_employee")
    @ResponseBody
    public JSONObject employee_info_import(@RequestBody HashMap<String, String> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        ToolMy demo = new ToolMy();
        return demo.analysis_json(departmentDao.getDepartmentEmployeeByName(map.get("name")));
    }

    //跳转到查询页面
    @RequestMapping(method = RequestMethod.GET, value = "/employee_search")
    public String employee_search(HttpServletRequest httpServletRequest,Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<com.longwang.uhrm.Entity.EmployeeArchives> list = employeeArchivesDao.findAllEmployee();
        model.addAttribute("list", list);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "employee_search";
    }

    //跳转到招聘系统的功能页面
    @RequestMapping(method = RequestMethod.GET, value = "/recruitment_system")
    public String recruitment_system(HttpServletRequest httpServletRequest,Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<com.longwang.uhrm.Entity.RecruitmentNotice> test = recruitmentNoticeDao.findAll();
        for (com.longwang.uhrm.Entity.RecruitmentNotice recruitmentNotice : test) {
            recruitmentNotice.setStringTime(recruitmentNotice.getTime().toString());
        }
        List<CollectTable> test1 =  collectTableDao.findAllPassed();
        for(CollectTable collectTable: test1){
            //System.out.println(collectTable.getDepartmentIdDepartment());
            collectTable.setDepartmentName(departmentDao.getName(collectTable.getDepartment_idDepartment()));
            collectTable.setNamePost(positionDao.getPostName(collectTable.getIdPost()));
        }
        model.addAttribute("plan", test1);
        model.addAttribute("list", test);

        List<User> users = userDao.getUserPassed();//new ArrayList<>();
        model.addAttribute("list1", users);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "Recruitment_system_functions";
    }

    //接收前端的招聘通知，存入数据库
    @RequestMapping(method = RequestMethod.POST, value = "/recruitment_notice")
    @ResponseBody
    public JSONObject recruitment_notice(@RequestBody HashMap<String, String> map) {
//        System.out.println(map.get("title") + map.get("content"));
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
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
    @RequestMapping(method = RequestMethod.GET, value = "/employee_id_search")
    public String employee_search_id(HttpServletRequest request, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        try {
            long id = Long.parseLong(request.getParameter("idOrName"));
            com.longwang.uhrm.Entity.EmployeeArchives emp = employeeArchivesDao.getEmployeeById(id);
            model.addAttribute("list", emp);
        } catch (Exception e) {
            String name = request.getParameter("idOrName");
            List<com.longwang.uhrm.Entity.EmployeeArchives> list = employeeArchivesDao.getEmployeeByName(name);
            model.addAttribute("list", list);
        }
        getSessionInfo.getsessionInfo(request,model);
        return "employee_search";
    }


    //查看个人信息
    @RequestMapping(method = RequestMethod.GET, value = "/personalInfo")
    public String personal_info(HttpServletRequest httpServletRequest, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        long employeeId = Long.parseLong((String) httpServletRequest.getSession().getAttribute("id"));
        model.addAttribute("Employee", employeeArchivesDao.getEmployeeById(employeeId));
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "personal_info";
    }

    //跳转到上报招聘计划页面
    @RequestMapping(method = RequestMethod.GET, value = "/recruitment_plan_make")

    public String recruitment_plan_make(HttpServletRequest httpServletRequest,Model model) {

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        model.addAttribute("DepartmentList", departmentDao.getAll());
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "recruitment_plan_make";
    }

    //根据部门名获取编制信息
    @RequestMapping(method = RequestMethod.POST, value = "/get_info_by_departmentName")
    @ResponseBody
    public JSONObject get_info_by_departmentName(@RequestBody HashMap<String, String> map) {
        //执行的为查询操作
//        System.out.println(map.get("department"));
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

    //审核招聘计划
    @RequestMapping(method = RequestMethod.GET, value = "/recruitment_plan_check")
    public String recruitment_plan_check(HttpServletRequest httpServletRequest,Model model) {

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志

        List<CollectTable> test = collectTableDao.findAllSaved();
        for(int i=0;i<test.size();i++){
            test.get(i).setDepartmentName(departmentDao.getDepartmentById(test.get(i).getDepartment_idDepartment()).getNameDepartment());
            test.get(i).setNamePost(positionDao.getPost(test.get(i).getIdPost()).getPostName());
        }
        model.addAttribute("plan",test);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "recruitment_plan_check";
    }

    //招聘计划审核结果
    @RequestMapping(method = RequestMethod.POST, value = "/recruitment_plan_check_result")
    @ResponseBody
    public JSONObject recruitment_plan_check_result(@RequestBody HashMap<String, Object> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<String> member = solution.translate(map.get("member").toString());//现有人数
        List<String> max = solution.translate(map.get("max").toString());//编制人数
        List<String> form = solution.translate(map.get("form_member").toString());//原计划招聘人数
        List<String> rec = solution.translate(map.get("recruitment").toString());//审核后人数
        List<String> state = solution.translate(map.get("state").toString());//审核后状态
        List<String> depart = solution.translate(map.get("depart").toString());//部门和岗位名
        for(int i=0;i<member.size();i++){
            if(state.get(i).contains("#")){
                //System.out.println(state.get(i).split("#")[0]);
                collectTableDao.deleteById(Integer.parseInt(state.get(i).split("#")[0]));
            }else {
                collectTableDao.updatePassed(Integer.parseInt(state.get(i)), rec.get(i));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }

    //跳转到信息修改页面
    @RequestMapping(method = RequestMethod.GET, value = "/employee_info_change")
    public String employee_info_change(HttpServletRequest httpServletRequest, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        model.addAttribute("employee", employeeArchivesDao.getEmployeeById(Integer.parseInt(httpServletRequest.getParameter("id"))));
        getSessionInfo.getsessionInfo(httpServletRequest,model);
//        model.addAttribute("employee",employeeArchivesDao.getEmployeeById(1));
        return "information_change";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/info_change")
    @ResponseBody
    public JSONObject info_change(@RequestBody HashMap<String, String> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        int originalLen = Integer.parseInt(map.get("originallen"));
        int nowLen = Integer.parseInt(map.get("nowlen"));
        int len1 = Integer.parseInt(map.get("len1"));
        convertdata convertdata = new convertdata();
        String[] changeInfoOriginal = new String[originalLen];
        String[] changeInfoNow = new String[nowLen];
        String[] selectedInfo = new String[len1];
        for (int i = 0; i < changeInfoOriginal.length; i++) {
            changeInfoOriginal[i] = map.get("changeinfooriginal[" + i + "]");
        }
        for (int i = 0; i < changeInfoNow.length; i++) {
            changeInfoNow[i] = map.get("changeinfonow[" + i + "]");
        }
        for (int i = 0; i < selectedInfo.length; i++) {
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

        for (int i = 0; i < len1; i++) {
            convertdata.setChangeInfoOriginal(changeInfoOriginal[i]);
            convertdata.setChangeInfoNow(changeInfoNow[i]);
            convertdata.setChangeType(selectedInfo[i]);
            res = employeeArchivesDao.informationChange(convertdata);
            if (res) continue;
            else break;
        }


        for (int i = 0; i < len1; i++) {
            convertdata.setChangeType(selectedInfo[i]);
            convertdata.setChangeInfoNow(changeInfoNow[i]);
            res1 = employeeArchivesDao.updateEmployeeSpecialInfo(convertdata);
            if (res) continue;
            else break;
        }

        if (res && res1 && res2) jsonObject.put("result", "success");
        else jsonObject.put("result", "default");
        return jsonObject;
    }

    //获取需修改信息的原信息
    @RequestMapping(method = RequestMethod.POST, value = "/get_info")
    @ResponseBody
    public JSONObject get_info(@RequestBody HashMap<String, String> hashMap) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        JSONObject jsonObject = new JSONObject();
        String res = "";
        com.longwang.uhrm.Entity.EmployeeArchives employeeArchives = employeeArchivesDao.getEmployeeById(Integer.parseInt(hashMap.get("employeeId")));
        switch (hashMap.get("changeType")) {
            case "employeeDepartment":
                res = employeeArchives.getEmployeeDepartment();
                break;
            case "employeePost":
                res = employeeArchives.getEmployeePost();
                break;
            case "employeeTitle":
                res = employeeArchives.getEmployeeTitle();
                break;
            case "employeeTechnicalGrade":
                res = employeeArchives.getEmployeeTechnicalGrade();
                break;
            case "employeeIdentity":
                res = employeeArchives.getEmployeeIdentity();
                break;
            case "employeeEducation":
                res = employeeArchives.getEmployeeEducation();
                break;
        }
        jsonObject.put("res", res);
        jsonObject.put("result", "success");
        return jsonObject;
    }

    //跳转到信息审核页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_namelist_check")
    public String recruitment_namelist_check(HttpServletRequest httpServletRequest,Model model){

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志

        List<User> users = userDao.getUserByCandiate();
        model.addAttribute("list",users);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "recruitment_name_list_check";
    }

    //接收审核后的报名应聘者
    @RequestMapping(method = RequestMethod.POST, value = "/modify_users")
    @ResponseBody
    public JSONObject modify_users(@RequestBody HashMap<String, Object> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
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
        jsonObject.put("result", "pass");
        return jsonObject;
    }

    //跳转面试与笔试成绩审核页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_info_save")
    public String recruitment_info_save(HttpServletRequest httpServletRequest,Model model){

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志

        List<CandidateInfo> candidateInfos = userDao.userpassed_get();
        model.addAttribute("list", candidateInfos);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "select_recruitment_grade";
    }

    //接收经过笔试和面试成绩审核的信息
    @RequestMapping(method = RequestMethod.POST, value = "/modify_users_selected")
    @ResponseBody
    public JSONObject modify_users_selected(@RequestBody HashMap<String, Object> map) {
//        System.out.println(map.get("out_list"));
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<String> re = solution.translate(map.get("out_list").toString());
        for(int i=0;i<re.size();i++){
            if(re.get(i).contains("#")){
                userDao.user_untested(Integer.parseInt(re.get(i).split("#")[0]));
            }else {
                userDao.usertested(Integer.parseInt(re.get(i)));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }

    //跳转到招聘人归档页面
    @RequestMapping(method = RequestMethod.GET,value = "/recruitment_to_employee")
    public String recruitment_to_employee(HttpServletRequest httpServletRequest,Model model){

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<User> users = userDao.archive();
        List<String> depart = userDao.get_post(users);
        for (int i=0;i<users.size();i++){
            users.get(i).setDepart_name_sign_up(depart.get(i).split("--")[0]);
            users.get(i).setPost_name_sign_up(depart.get(i).split("--")[1]);
        }
        model.addAttribute("list", users);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "Archive";
    }

    //归档
    @RequestMapping(method = RequestMethod.POST, value = "/archive_data")
    @ResponseBody
    public JSONObject archive_data(@RequestBody HashMap<String, Object> map) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
//        System.out.println(map.get("title"));
//        System.out.println(map.get("name"));
        List<String> name = solution.translate(map.get("name").toString());
        List<String> sex = solution.translate(map.get("sex").toString());
        List<String> age = solution.translate(map.get("age").toString());
        List<String> address = solution.translate(map.get("address").toString());
        List<String> telephone = solution.translate(map.get("telephone").toString());
        List<String> education = solution.translate(map.get("education").toString());
        List<String> IdCard = solution.translate(map.get("IdCard").toString());
        List<String> title = solution.translate(map.get("title").toString());
        List<String> technicalGrade = solution.translate(map.get("technicalGrade").toString());
        List<String> depart = solution.translate(map.get("department").toString());
        List<String> post = solution.translate(map.get("post").toString());
        System.out.println(sex);
        System.out.println(age);
        System.out.println(address);
        System.out.println(telephone);
        System.out.println(education);
        for(int i=0;i<name.size();i++){
            EmployeeArchives temp = new EmployeeArchives();
            temp.setEmployeeName(name.get(i));
            temp.setEmployeeSex(sex.get(i));
            Timestamp time = new Timestamp(System.currentTimeMillis());
            String[] a = time.toString().split(" ")[0].split("-");
            a[0] = String.valueOf(Integer.parseInt(a[0]) - Integer.parseInt(age.get(i)));
            temp.setEmployeeBirthday(a[0] + "-" + a[1] + "-" + a[2]);
            temp.setEmployeeAddress(address.get(i));
            temp.setEmployeePhoneNumber(Integer.parseInt(telephone.get(i)));
            temp.setEmployeeEducation(education.get(i));
            temp.setEmployeeIdentity(IdCard.get(i));
            temp.setEmployeeTitle(title.get(i));
            temp.setEmployeeTechnicalGrade(technicalGrade.get(i));
            temp.setEmployeeDepartment(depart.get(i));
            temp.setEmployeePost(post.get(i));
            temp.setEmployeeId(employeeArchivesDao.max_id()+1);
            userDao.delete_tested();
            employeeArchivesDao.Archive(temp);
            temp.setPassword(userDao.delete_phone(telephone.get(i)));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "pass");
        return jsonObject;
    }

    //用户注册
    @RequestMapping(method = RequestMethod.POST, value = "/userRegister")
    @ResponseBody
    public JSONObject user_register(@RequestBody User user) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        boolean flag = userDao.register(user);
        JSONObject jsonObject = new JSONObject();
        if (flag) {
            jsonObject.put("result", "success");
        }
        return jsonObject;
    }


    //跳转到找回密码
    @RequestMapping(method = RequestMethod.GET, value = "/to_retrieve_password")
    public String to_retrieve_password(HttpServletRequest httpServletRequest,Model model) {
        getSessionInfo.getsessionInfo(httpServletRequest,model);

        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        return "Retrieve_password";
    }


    //用户找回密码
    @RequestMapping(method = RequestMethod.POST, value = "/retrieve_password")
    @ResponseBody
    public JSONObject retrieve_password(@RequestBody HashMap<String, String> hashMap) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        convertdata convertdata = new convertdata();
        convertdata.setEmployeePhone(hashMap.get("employeePhoneNumber"));
        convertdata.setEmployeeId(Integer.parseInt(hashMap.get("employeeId")));
        String res = userDao.retrieve_password(convertdata);
        System.out.println(res);
        JSONObject jsonObject = new JSONObject();
        if (res != null) {
            jsonObject.put("password", res);
            jsonObject.put("result", "success");
        } else {
            jsonObject.put("result", "default");
        }
        return jsonObject;
    }

    //查看修改用户个人信息
    @RequestMapping(method = RequestMethod.GET, value = "/userInfo")
    public String user_info(HttpServletRequest httpServletRequest, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        String phone = (String) httpServletRequest.getSession().getAttribute("phone");
        model.addAttribute("User", userDao.getUserByTelephone(phone));
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "user_info";
    }
    //完善,修改用户个人信息
    @RequestMapping(method = RequestMethod.POST,value = "/user_info_change")
    @ResponseBody
    public  JSONObject user_info_change(@RequestBody HashMap<String,String> hashMap,Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        User user = new User();
        user.setSex(hashMap.get("sex"));
        user.setAge(Long.parseLong(hashMap.get("age")));
        user.setAddress(hashMap.get("address"));
        user.setMailAddress(hashMap.get("mailAddress"));
        user.setIDCard(hashMap.get("IDCard"));
        user.setTelephone(hashMap.get("telephone"));
        user.setEducation(hashMap.get("education"));
        boolean flag=userDao.update_user_Info(user);
        JSONObject jsonObject=new JSONObject();
        if(flag){
            jsonObject.put("result","success");
        }else {
            jsonObject.put("result","failure");
        }
        return jsonObject;
    }

    //跳转到合同查询
    @RequestMapping(method = RequestMethod.GET,value = "/to_contract_query")
    public String to_contract_query(HttpServletRequest request,Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        List<Contract> list = employeeArchivesDao.findAllContract();
//        getSessionInfo.getsessionInfo(request,model);
        getSessionInfo.getsessionInfo(request,model);
        model.addAttribute("list",list);
        return "contract_search";
    }

    //查询指定的合同
    @RequestMapping(method = RequestMethod.GET,value = "/contract_info_search")
    public String contract_info_search(HttpServletRequest request, Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
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
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        int ContractId = Integer.parseInt(httpServletRequest.getParameter("id"));
        Contract contract = employeeArchivesDao.getContractById(ContractId);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        model.addAttribute("list",contract);
        return "contract_update";
    }

    //合同修改提交
    @RequestMapping(method = RequestMethod.POST,value = "/contract_change")
    @ResponseBody
    public JSONObject contract_change(@RequestBody HashMap<String,String> hashMap){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
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
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        int idContract = Integer.parseInt(httpServletRequest.getParameter("id"));
        employeeArchivesDao.delete_contract(idContract);

        List<Contract> list = employeeArchivesDao.findAllContract();
        model.addAttribute("list",list);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "contract_search";
    }

    //to新增合同
    @RequestMapping(method = RequestMethod.GET,value = "/to_add_contract")
    public String to_add_contract(HttpServletRequest httpServletRequest,Model model){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "contract_import";
    }


    @RequestMapping(method = RequestMethod.POST,value = "/add_contract")
    @ResponseBody
    public JSONObject add_contract(@RequestBody HashMap<String,String> hashMap){
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
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

    //应聘者报名,获取到岗位信息
    @RequestMapping(method = RequestMethod.POST,value = "/sign_up")
    @ResponseBody
    public JSONObject sign_up(@RequestBody HashMap<String, String> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
//        System.out.println(map.get("post"));
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        String phone = (String) httpServletRequest.getSession().getAttribute("phone");
        User user=userDao.getUserByTelephone(phone);
        CandidateInfo candidateInfo=new CandidateInfo();
        candidateInfo.setIdCandidateInfo(user.getIdUser());
        candidateInfo.setStatus("unverified");
        candidateInfo.setDepartmentPost(map.get("post"));
        userDao.insert_candidate(candidateInfo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","success");
        return jsonObject;
    }

    //跳转到我的考勤信息查看
    @RequestMapping(method = RequestMethod.GET, value = "/myAttendance")
    public String toMyAttendance(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String id = (String) httpServletRequest.getSession().getAttribute("id");
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        String type = (String) httpServletRequest.getSession().getAttribute("type");
        if(id == null || !type.equals("employee")){
            return "redirect:index";
        }else {
            model.addAttribute("id", id);
            model.addAttribute("name", name);
        }
        return "myAttendance";
    }

    //查找我的考勤信息
    @RequestMapping(method = RequestMethod.GET, value = "/searchMyAttendance")
    public String searchMyAttendance(Model m, HttpServletRequest request, Model model) {
        String date = request.getParameter("AttendanceDate");
        int id;
        if(request.getSession().getAttribute("id")!=null){
            id =  Integer.parseInt((String)request.getSession().getAttribute("id"));
            model.addAttribute("name",request.getSession().getAttribute("name"));
        }else {
            return "redirect:index";
        }

        List<Attendance> attendances = attendanceDao.getEmployeeByTime(date, id);
        m.addAttribute("list", attendances);
        return "myAttendance";
    }

    //跳转到考勤管理系统
    @RequestMapping(method = RequestMethod.GET, value = "/Attendance")
    public String toAttendance(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        int assitant_id = Integer.parseInt((String)httpServletRequest.getSession().getAttribute("id"));
        EmployeeArchives emp = employeeArchivesDao.getEmployeeById(assitant_id);
        String post = emp.getEmployeePost();

        String id = (String) httpServletRequest.getSession().getAttribute("id");
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        String type = (String) httpServletRequest.getSession().getAttribute("type");

        //只有人事助理能访问考勤管理系统
        if(id == null || !type.equals("employee") || !post.equals("二级人事助理")){
            return "redirect:index";
        }else {
            model.addAttribute("id", id);
            model.addAttribute("name", name);
        }
        return "Attendance";
    }

    String department_for_Attendance;

    //查看部门对应的考勤信息，只有post为二级人事助理的雇员才能进行
    @RequestMapping(method = RequestMethod.GET, value = "/departmentChange")
    public String departmentChange(Model m, HttpServletRequest request) {
        department_for_Attendance = request.getParameter("inlineRadioOptions");
        List<EmployeeArchives> employeeArchivesList = departmentDao.getDepartmentEmployeeByName(department_for_Attendance);
        List<Attendance> attendances = new ArrayList<>();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String today = ft.format(dNow);
        if (attendanceDao.checkAttendance(today, (int) employeeArchivesList.get(0).getEmployeeId()) == null) {
            for (int i = 0; i < employeeArchivesList.size(); i++) {
                Attendance attendance = new Attendance(today, employeeArchivesList.get(i).getEmployeeId(), employeeArchivesList.get(i).getEmployeeName(), "intime");
                attendances.add(attendance);
            }
            m.addAttribute("list", attendances);
            m.addAttribute("department", department_for_Attendance);
            m.addAttribute("name",request.getSession().getAttribute("name"));
        } else {

        }
        return "Attendance";
    }
    //进行对应部门的考勤,只有post为二级人事助理的雇员才能进行
    @RequestMapping(method = RequestMethod.POST, value = "/checkIn")
    @ResponseBody
    public JSONObject checkIN(Model m, HttpServletRequest request, @RequestBody List<String> list) {
        JSONObject jsonObject = new JSONObject();
        List<EmployeeArchives> employeeArchivesList = departmentDao.getDepartmentEmployeeByName(department_for_Attendance);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String today = ft.format(dNow);
        int assistantId = Integer.parseInt((String)request.getSession().getAttribute("id"));
        String assistantName = employeeArchivesDao.getName(assistantId);
        if (attendanceDao.checkAttendance(today, (int) employeeArchivesList.get(0).getEmployeeId()) == null) {
            for (int i = 0; i < employeeArchivesList.size(); i++) {
                Attendance attendance = new Attendance(today, employeeArchivesList.get(i).getEmployeeId(),assistantName,assistantId, employeeArchivesList.get(i).getEmployeeName(), list.get(i));
                attendanceDao.putListE(attendance);
            }
            jsonObject.put("result", "success");
        } else {
            jsonObject.put("result", "error");
        }
        return jsonObject;
    }

    //删除员工档案信息
    @RequestMapping(method = RequestMethod.GET,value = "/employee_info_delete")
    public String employee_info_delete(HttpServletRequest httpServletRequest,Model model){
        boolean res = getSessionInfo.getsessionInfo(httpServletRequest,model);
        if(!res) return "index";
        int id = Integer.parseInt(httpServletRequest.getParameter("id"));
        employeeArchivesDao.delete_employee(id);
        return "employee_search";
    }
}
