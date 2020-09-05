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
}
