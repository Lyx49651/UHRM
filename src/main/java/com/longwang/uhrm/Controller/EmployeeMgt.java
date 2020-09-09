package com.longwang.uhrm.controller;

import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Dao.DepartmentDao;
import com.longwang.uhrm.Dao.EmployeeArchivesDao;
import com.longwang.uhrm.Dao.SalaryDao;
import com.longwang.uhrm.Entity.EmployeeArchives;
import com.longwang.uhrm.Proxy.LogInterface;
import com.longwang.uhrm.Tool.ToolMy;
import com.longwang.uhrm.Tool.convertdata;
import com.longwang.uhrm.Tool.getSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class EmployeeMgt {
    //注入
    EmployeeArchivesDao employeeArchivesDao;
    DepartmentDao departmentDao;
    SalaryDao salaryDao;

    @Autowired
    void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao) {
        this.employeeArchivesDao = employeeArchivesDao;
    }
    @Autowired
    void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }
    @Autowired
    void setSalaryDao(SalaryDao salaryDao) {
        this.salaryDao = salaryDao;
    }

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    private void log(String fun){
        LogInterface logInterface = (LogInterface)applicationContext.getBean("logDaoProxy");
        logInterface.time_function(fun);
    }

    //    员工登录
    @RequestMapping(method = RequestMethod.GET, value = "/personnel_login")
    public String personnel_login(Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        return "employee_login";
    }

    //员工登录信息确认
    @RequestMapping(method = RequestMethod.POST, value = "/employee_login_check")
    @ResponseBody
    public JSONObject employee_login_check(@RequestBody HashMap<String, String> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        Boolean res = employeeArchivesDao.authenticate(Integer.parseInt(map.get("id")), map.get("password"));
        if (res) {
            HttpSession httpSession = httpServletRequest.getSession();//获取session
            EmployeeArchives employeeArchives = employeeArchivesDao.getEmployeeById(Integer.parseInt(map.get("id")));
            httpSession.setAttribute("id", map.get("id"));
            httpSession.setAttribute("name", employeeArchives.getEmployeeName());
            httpSession.setAttribute("type", "employee");
            httpSession.setAttribute("post",employeeArchives.getEmployeePost());
            httpSession.setMaxInactiveInterval(2 * 60);//设置session存活时间
            Cookie cookie = new Cookie("name", employeeArchives.getEmployeeName());//新建cookie供客户端使用
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
    }
    // 跳转到人员管理系统
    @RequestMapping(method = RequestMethod.GET, value = "/employee_management_system")
    public String employee_management_system(HttpServletRequest httpServletRequest,Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        String id = (String) httpServletRequest.getSession().getAttribute("id");
        if(id!=null){
            EmployeeArchives emp = employeeArchivesDao.getEmployeeById(Integer.parseInt(id));
            String post = emp.getEmployeePost();
            String name = (String) httpServletRequest.getSession().getAttribute("name");
            String type = (String) httpServletRequest.getSession().getAttribute("type");
            if(type.equals("employee")&&post.equals("二级人事助理")){
                model.addAttribute("id", id);
                model.addAttribute("name", name);
                model.addAttribute("logged",true);
            }else {
                model.addAttribute("logged",false);
            }
        }else{
            model.addAttribute("logged",false);
        }
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
        List<EmployeeArchives> list = employeeArchivesDao.findAllEmployee();
        model.addAttribute("list", list);
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "employee_search";
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

    //跳转到信息修改页面
    @RequestMapping(method = RequestMethod.GET, value = "/employee_info_change")
    public String employee_info_change(HttpServletRequest httpServletRequest, Model model) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        model.addAttribute("employee", employeeArchivesDao.getEmployeeById(Integer.parseInt(httpServletRequest.getParameter("id"))));
        getSessionInfo.getsessionInfo(httpServletRequest,model);
        return "information_change";
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

    //员工找回密码
    @RequestMapping(method = RequestMethod.POST, value = "/retrieve_password")
    @ResponseBody
    public JSONObject retrieve_password(@RequestBody HashMap<String, String> hashMap) {
        log(Thread.currentThread().getStackTrace()[1].getMethodName());//日志
        convertdata convertdata = new convertdata();
        convertdata.setEmployeePhone(hashMap.get("employeePhoneNumber"));
        convertdata.setEmployeeId(Integer.parseInt(hashMap.get("employeeId")));
        String res = employeeArchivesDao.retrieve_password(convertdata);
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
