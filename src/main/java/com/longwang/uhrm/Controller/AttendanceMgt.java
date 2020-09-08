package com.longwang.uhrm.Controller;

import com.alibaba.fastjson.JSONObject;
import com.longwang.uhrm.Dao.AttendanceDao;
import com.longwang.uhrm.Dao.DepartmentDao;
import com.longwang.uhrm.Dao.EmployeeArchivesDao;
import com.longwang.uhrm.Entity.Attendance;
import com.longwang.uhrm.Entity.EmployeeArchives;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AttendanceMgt {

    AttendanceDao attendanceDao;

    @Autowired
    void setAttendanceDao(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }

    @Autowired
    void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Autowired
    void setEmployeeArchivesDao(EmployeeArchivesDao employeeArchivesDao) {
        this.employeeArchivesDao = employeeArchivesDao;
    }

    DepartmentDao departmentDao;
    EmployeeArchivesDao employeeArchivesDao;

    //跳转到我的考勤信息查看
    @RequestMapping(method = RequestMethod.GET, value = "/myAttendance")
    public String toMyAttendance(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String id = (String) httpServletRequest.getSession().getAttribute("id");
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        String type = (String) httpServletRequest.getSession().getAttribute("type");
        if(id == null || !type.equals("employee")){
            return "employee_login";
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
        int id =  Integer.parseInt((String)request.getSession().getAttribute("id"));

        model.addAttribute("name",request.getSession().getAttribute("name"));
        List<Attendance> attendances = attendanceDao.getEmployeeByTime(date, id);
        m.addAttribute("list", attendances);
        return "myAttendance";
    }

    //跳转到考勤管理系统
    @RequestMapping(method = RequestMethod.GET, value = "/Attendance")
    public String toAttendance(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String id = (String) httpServletRequest.getSession().getAttribute("id");
        if(id!=null){
            EmployeeArchives emp = employeeArchivesDao.getEmployeeById(Integer.parseInt(id));
            String post = emp.getEmployeePost();
            String name = (String) httpServletRequest.getSession().getAttribute("name");
            String type = (String) httpServletRequest.getSession().getAttribute("type");
            if(type.equals("employee")&&post.equals("二级考勤助理")){
                model.addAttribute("departmentNames",departmentDao.getAllName());
                model.addAttribute("id", id);
                model.addAttribute("name", name);
                model.addAttribute("logged",true);
            }else{
                model.addAttribute("logged",false);
            }
        }else{
            model.addAttribute("logged",false);
        }
        return "Attendance";
    }

    String department_for_Attendance;

    //查看部门对应的考勤信息，只有post为二级人事助理的雇员才能进行
    @RequestMapping(method = RequestMethod.GET, value = "/departmentChange")
    public String departmentChange(Model m, HttpServletRequest request) {
        m.addAttribute("departmentNames",departmentDao.getAllName());
        department_for_Attendance = request.getParameter("inlineRadioOptions");
        if(department_for_Attendance==null){
            return "Attendance";
        }
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
//            m.addAttribute("dis","disable");
        } else {
            department_for_Attendance = null;
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
}
