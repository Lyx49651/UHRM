package com.longwang.uhrm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SalaryMgt {
    @GetMapping("salaryMgt")
    public String mgtPage(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String id = (String) httpServletRequest.getSession().getAttribute("id");
        String name = (String) httpServletRequest.getSession().getAttribute("name");
        String type = (String) httpServletRequest.getSession().getAttribute("type");
        if(id == null || !type.equals("employee")){
            model.addAttribute("logged", false);
        }else {
            model.addAttribute("logged", true);
            model.addAttribute("id", id);
            model.addAttribute("name", name);
        }
        return "salary_management";
    }
}
