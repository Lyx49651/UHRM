package com.longwang.uhrm.Controller;

import com.longwang.uhrm.Dao.SalaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SalaryMgt {
    SalaryDao salaryDao;
    @Autowired
    void setSalaryDao(SalaryDao salaryDao){this.salaryDao = salaryDao;}

    @GetMapping("salaryMgt")
    public String mgtPage(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String id = (String) httpServletRequest.getSession().getAttribute("id");
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        String type = (String) httpServletRequest.getSession().getAttribute("type");
        if(id == null || !type.equals("employee")){
            model.addAttribute("logged", false);
        }else {
            model.addAttribute("salaryMeters", salaryDao.getAllSalaryParameters());
            model.addAttribute("salaryLogs", salaryDao.findAllLogs());
            model.addAttribute("logged", true);
            model.addAttribute("id", id);
            model.addAttribute("name", name);
        }
        return "salary_management";
    }

    @PostMapping("salaryMgt")
    @ResponseBody
    public String changeSalaryLevel(HttpServletRequest httpServletRequest){
        boolean res = salaryDao.changeLevel(httpServletRequest.getParameter("salaryLevel"), Integer.parseInt(httpServletRequest.getParameter("employeeId")));
        if (res){
            return "{\"changeResult\": true}";
        }else return "{\"changeResult\": false}";
    }

    @GetMapping("distribute")
    @ResponseBody
    public String distribute(HttpServletRequest httpServletRequest){
        boolean res = salaryDao.salaryLog(Integer.parseInt(httpServletRequest.getParameter("id")), httpServletRequest.getParameter("level"));
        if (res){
            return "{\"res\": true}";
        }else return "{\"res\": false}";
    }

    @GetMapping("mySalary")
    public String personalSalaryLog(Model model, HttpServletRequest httpServletRequest){
        String id = (String) httpServletRequest.getSession().getAttribute("id");
        if(id == null){
            model.addAttribute("logged", false);
        }else {
            model.addAttribute("logged", true);
            model.addAttribute("salaryLogs", salaryDao.findMyLogs(Integer.parseInt(id)));
        }
        return "MySalary";
    }
}
